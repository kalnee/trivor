package com.kalnee.trivor.nlp.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.stream.Stream;

class FileSubtitleHandler implements SubtitleHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileSubtitleHandler.class);

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
