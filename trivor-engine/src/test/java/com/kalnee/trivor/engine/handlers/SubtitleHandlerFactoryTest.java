package com.kalnee.trivor.engine.handlers;

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
