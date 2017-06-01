package com.kalnee.trivor.sdk.models;

import java.util.List;

import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.SPACE;

public class Sentence {

    private String sentence;
    private List<Token> tokens;

    public Sentence() {
    }

    public Sentence(String sentence, List<Token> tokens) {
        this.sentence = sentence;
        this.tokens = tokens;
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

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public String getSentenceTags() {
        return tokens.stream().map(Token::getTag).collect(joining(SPACE));
    }

    @Override
    public String toString() {
        return "Sentence{" + "sentence='" + sentence + '\'' + ", tokens=" + tokens + '}';
    }
}
