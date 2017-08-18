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

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;

public class SubtitleHandlerFactoryTest {

	@Test
	public void testFileScheme() throws URISyntaxException {
		URI file = URI.create("file://language/s1e1.srt");
		SubtitleHandler handler = SubtitleHandlerFactory.create(file).getHandler();

		assertEquals(FileSubtitleHandler.class, handler.getClass());
	}

	@Test
	public void testS3Scheme() throws URISyntaxException {
		URI file = URI.create("s3://bucket/s1e1.srt");
		SubtitleHandler handler = SubtitleHandlerFactory.create(file).getHandler();

		assertEquals(S3SubtitleHandler.class, handler.getClass());
	}

	@Test(expected = IllegalStateException.class)
	public void testInvalidScheme() {
		URI http = URI.create("http://google.com");
		SubtitleHandlerFactory.create(http).getHandler();
	}
}
