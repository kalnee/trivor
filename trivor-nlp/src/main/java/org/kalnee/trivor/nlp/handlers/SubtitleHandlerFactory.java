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

import java.net.URI;
import java.util.Objects;

public class SubtitleHandlerFactory {

	private final URI uri;

	private SubtitleHandlerFactory(URI uri) {
		this.uri = uri;
	}

	public static SubtitleHandlerFactory create(URI uri) {
		Objects.nonNull(uri);
		return new SubtitleHandlerFactory(uri);
	}

	public SubtitleHandler getHandler() {
		switch (uri.getScheme()) {
		case S3SubtitleHandler.S3_SCHEME:
			return new S3SubtitleHandler(uri);
		case FileSubtitleHandler.FILE_SCHEME:
		case FileSubtitleHandler.JAR_SCHEME:
			return new FileSubtitleHandler(uri);
		default:
			throw new IllegalStateException("no implementation found for scheme " + uri.getScheme());
		}
	}
}
