package com.kalnee.trivor.engine.models;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "subtitles")
public class Subtitle {

  @Id
  private BigInteger id;

  private String imdbId;

  private String name;

  private Integer season;

  private Integer episode;

  private Integer year;

  private List<Sentence> sentences;

  public Subtitle(String imdbId, String name, Integer season, Integer episode, Integer year,
      List<Sentence> sentences) {
    this.imdbId = imdbId;
    this.name = name;
    this.season = season;
    this.episode = episode;
    this.year = year;
    this.sentences = sentences;
  }

  Subtitle() {}

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

  public List<Sentence> getSentences() {
    return sentences;
  }

  public void setSentences(List<Sentence> sentences) {
    this.sentences = sentences;
  }

  @Override
  public String toString() {
    return "SubtitleModel{" + "id=" + id + ", imdbId=" + imdbId + ", name='" + name + '\''
        + ", season=" + season + ", episode=" + episode + ", year=" + year + ", sentences=" + sentences
        + '}';
  }
}
