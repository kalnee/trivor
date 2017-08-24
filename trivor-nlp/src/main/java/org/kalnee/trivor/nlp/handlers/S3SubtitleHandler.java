/* Copyright (c) 2016 Kalnee
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.kalnee.trivor.nlp.handlers;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3URI;
import com.amazonaws.services.s3.model.S3Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.stream.Stream;

/**
 * AWS S3 bucket handler implementation of the <tt>SubtitleHandler</tt> interface.
 *
 * @see SubtitleHandler
 * @see SubtitleHandlerFactory
 *
 * @since 0.0.1
 */
class S3SubtitleHandler implements SubtitleHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileSubtitleHandler.class);

	static final String S3_SCHEME = "s3";

	private final URI uri;
	private final AmazonS3 s3Client;

	/**
	 * Constructs a S3 handler for a specified uri
	 *
	 * @param  uri  the file uri
	 */
	S3SubtitleHandler(URI uri) {
		this.uri = uri;
		this.s3Client = new AmazonS3Client();
	}

	@Override
	public Stream<String> lines() {
		LOGGER.debug("starting download from {}", uri);
		AmazonS3URI s3URI = new AmazonS3URI(uri);
		S3Object s3Object = s3Client.getObject(s3URI.getBucket(), s3URI.getKey());

		InputStream stream = s3Object.getObjectContent();
		return new BufferedReader(new InputStreamReader(stream)).lines();
	}
}
