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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.stream.Stream;

/**
 * File handler implementation of the <tt>SubtitleHandler</tt> interface. it accepts
 * both plain text files and jar files.
 *
 * @see SubtitleHandler
 * @see SubtitleHandlerFactory
 *
 * @since 0.0.1
 */
class FileSubtitleHandler implements SubtitleHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileSubtitleHandler.class);

    static final String FILE_SCHEME = "file";
    static final String JAR_SCHEME = "jar";

    /**
     * File uri
     */
    private final URI uri;

    /**
     * Constructs a file handler for an uri
     *
     * @param  uri  the file uri
     */
    public FileSubtitleHandler(URI uri) {
        this.uri = uri;
    }

    @Override
    public Stream<String> lines() {
        try {
            final InputStream stream = uri.toURL().openStream();
            return new BufferedReader(new InputStreamReader(stream)).lines();
        } catch (IOException e) {
            LOGGER.error("file not found", e);
            throw new IllegalStateException(e);
        }
    }
}
