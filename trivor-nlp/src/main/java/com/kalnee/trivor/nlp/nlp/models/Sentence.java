package com.kalnee.trivor.nlp.nlp.models;

import java.util.List;

import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.SPACE;

public class Sentence {

    private String sentence;
    private List<Token> tokens;
    private List<Chunk> chunks;

    public Sentence() {
    }

    public Sentence(String sentence, List<Token> tokens) {
        this.sentence = sentence;
        this.tokens = tokens;
    }

    public Sentence(String sentence, List<Token> tokens, List<Chunk> chunks) {
        this(sentence, tokens);
        this.chunks = chunks;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public String getSentenceTags() {
        return tokens.stream().map(Token::getTag).collect(joining(SPACE));
    }

    public List<Chunk> getChunks() {
        return chunks;
    }
}
