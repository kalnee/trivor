package com.kalnee.trivor.sdk.handlers;

import java.util.stream.Stream;

public interface SubtitleHandler {

    /**
     * Returns the lines of a subtitle file from different sources.
     *
     * @return Stream<String> Subtitle lines
     */
    Stream<String> lines();
}
