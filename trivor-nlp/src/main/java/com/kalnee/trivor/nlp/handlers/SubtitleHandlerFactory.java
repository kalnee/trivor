package com.kalnee.trivor.nlp.handlers;

import java.net.URI;
import java.util.Objects;

import static com.kalnee.trivor.nlp.handlers.FileSubtitleHandler.FILE_SCHEME;
import static com.kalnee.trivor.nlp.handlers.FileSubtitleHandler.JAR_SCHEME;
import static com.kalnee.trivor.nlp.handlers.S3SubtitleHandler.S3_SCHEME;

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
		case S3_SCHEME:
			return new S3SubtitleHandler(uri);
		case FILE_SCHEME:
		case JAR_SCHEME:
			return new FileSubtitleHandler(uri);
		default:
			throw new IllegalStateException("no implementation found for scheme " + uri.getScheme());
		}
	}
}
