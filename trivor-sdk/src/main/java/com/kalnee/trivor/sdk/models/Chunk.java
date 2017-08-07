package com.kalnee.trivor.sdk.models;

import java.util.List;

public class Chunk {

    private final List<String> tokens;
    private final List<String> tags;

    public Chunk(List<String> tokens, List<String> tags) {
        this.tokens = tokens;
        this.tags = tags;
    }

    public List<String> getTokens() {
        return tokens;
    }

    public List<String> getTags() {
        return tags;
    }
}
