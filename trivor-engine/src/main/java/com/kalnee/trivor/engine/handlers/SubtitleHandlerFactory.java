package com.kalnee.trivor.engine.handlers;

import static com.kalnee.trivor.engine.handlers.FileSubtitleHandler.FILE_SCHEME;
import static com.kalnee.trivor.engine.handlers.FileSubtitleHandler.JAR_SCHEME;
import static com.kalnee.trivor.engine.handlers.S3SubtitleHandler.S3_SCHEME;

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
