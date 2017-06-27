package com.kalnee.trivor.sdk.models;

import java.util.List;

public class Subtitle {

    private String name;
    private Integer season;
    private Integer episode;
    private Integer duration;
    private TypeEnum type;
    private List<Sentence> sentences;

    public Subtitle() {
    }

    public Subtitle(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    public Subtitle(Integer duration, List<Sentence> sentences) {
        this.duration = duration;
        this.sentences = sentences;
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

    public void setSeason(Integer season) {
        this.season = season;
    }

    public Integer getEpisode() {
        return episode;
    }

    public void setEpisode(Integer episode) {
        this.episode = episode;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
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
}
