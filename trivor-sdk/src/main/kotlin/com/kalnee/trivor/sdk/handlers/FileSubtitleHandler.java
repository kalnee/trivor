package com.kalnee.trivor.sdk.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.stream.Stream;

class FileSubtitleHandler implements SubtitleHandler {

    private static final Logger LOGGER = LogManager.getLogger(FileSubtitleHandler.class);

    static final String FILE_SCHEME = "file";
    static final String JAR_SCHEME = "jar";

    private final URI uri;

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
