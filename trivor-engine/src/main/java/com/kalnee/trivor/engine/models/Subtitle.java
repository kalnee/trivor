package com.kalnee.trivor.engine.models;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.kalnee.trivor.engine.dto.SubtitleDTO;
import com.kalnee.trivor.engine.dto.TypeEnum;

@Document(collection = "subtitles")
public class Subtitle {

  @Id
  private BigInteger id;

  private String imdbId;

  private String name;

  private Integer season;

  private Integer episode;

  private Integer year;

  private Integer duration;

  private TypeEnum type;

  private List<Sentence> sentences;

  private List<Insight> insights;

  public Subtitle(BigInteger id, String imdbId, String name, Integer season, Integer episode,
      Integer year, Integer duration, List<Sentence> sentences) {
    this.id = id;
    this.imdbId = imdbId;
    this.name = name;
    this.season = season;
    this.episode = episode;
    this.year = year;
    this.duration = duration;
    this.sentences = sentences;
  }

  public Subtitle(String imdbId, String name, Integer season, Integer episode, Integer year,
      Integer duration, List<Sentence> sentences) {
    this.imdbId = imdbId;
    this.name = name;
    this.season = season;
    this.episode = episode;
    this.year = year;
    this.duration = duration;
    this.sentences = sentences;
  }

  public Subtitle(SubtitleDTO subtitleDTO, List<Sentence> sentences) {
    this.imdbId = subtitleDTO.getImdbId();
    this.name = subtitleDTO.getName();
    this.season = subtitleDTO.getSeason();
    this.episode = subtitleDTO.getEpisode();
    this.year = subtitleDTO.getYear();
    this.type = subtitleDTO.getType();
    this.duration = subtitleDTO.getDuration();
    this.sentences = sentences;
  }

  public Subtitle() {}

  public BigInteger getId() {
    return id;
  }

  public void setId(BigInteger id) {
    this.id = id;
  }

  public String getImdbId() {
    return imdbId;
  }

  public void setImdbId(String imdbId) {
    this.imdbId = imdbId;
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

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
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

  public List<Insight> getInsights() {
    return insights;
  }

  public void setInsights(List<Insight> insights) {
    this.insights = insights;
  }

  @Override
  public String toString() {
    return "Subtitle{" + "id=" + id + ", imdbId='" + imdbId + '\'' + ", name='" + name + '\''
        + ", season=" + season + ", episode=" + episode + ", year=" + year + ", duration="
        + duration + ", type=" + type + ", sentences=" + sentences + ", insights=" + insights + '}';
  }
}
