package com.kalnee.trivor.sdk.models;

public class Token {

    private String token;
    private String tag;
    private String lemma;
    private Double prob;

    public Token() {
    }

    public Token(String token, String tag, String lemma, Double prob) {
        this.token = token;
        this.tag = tag;
        this.lemma = lemma;
        this.prob = prob;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public Double getProb() {
        return prob;
    }

    public void setProb(Double prob) {
        this.prob = prob;
    }

    @Override
    public String toString() {
        return "Token{" +
                "token='" + token + '\'' +
                ", tag='" + tag + '\'' +
                ", lemma='" + lemma + '\'' +
                ", prob=" + prob +
                '}';
    }
}
