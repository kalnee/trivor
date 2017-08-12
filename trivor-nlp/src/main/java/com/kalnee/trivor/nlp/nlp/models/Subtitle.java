package com.kalnee.trivor.nlp.nlp.models;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Subtitle {

    private String name;
    private Integer season;
    private Integer episode;
    private Integer duration;
    private TypeEnum type;
    private List<Sentence> sentences;
    private Map<SentimentEnum, BigDecimal> sentiment;

    public Subtitle() {
    }

    public Subtitle(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    public Subtitle(Integer duration, List<Sentence> sentences) {
        this(sentences);
        this.duration = duration;
    }

    public Subtitle(Integer duration, List<Sentence> sentences, Map<SentimentEnum, BigDecimal> sentiment) {
        this(duration, sentences);
        this.sentiment = sentiment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSeason() {
        return season;
    }

    public Integer getEpisode() {
        return episode;
    }

    public Integer getDuration() {
        return duration;
    }

    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    public Map<SentimentEnum, BigDecimal> getSentiment() {
        return sentiment;
    }
}
