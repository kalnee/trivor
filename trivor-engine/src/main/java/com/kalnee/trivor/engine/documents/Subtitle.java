package com.kalnee.trivor.engine.documents;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "subtitles")
public class Subtitle {

  @Id
  private BigInteger id;

  private Long imdbId;

  private String name;

  private Integer season;

  private Integer episode;

  private Integer year;

  private List<String> phrases;

  public Subtitle(Long imdbId, String name, Integer season, Integer episode, Integer year,
      List<String> phrases) {
    this.imdbId = imdbId;
    this.name = name;
    this.season = season;
    this.episode = episode;
    this.year = year;
    this.phrases = phrases;
  }

  Subtitle() {}

  public BigInteger getId() {
    return id;
  }

  public void setId(BigInteger id) {
    this.id = id;
  }

  public Long getImdbId() {
    return imdbId;
  }

  public void setImdbId(Long imdbId) {
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

  public List<String> getPhrases() {
    return phrases;
  }

  public void setPhrases(List<String> phrases) {
    this.phrases = phrases;
  }

  @Override
  public String toString() {
    return "SubtitleModel{" + "id=" + id + ", imdbId=" + imdbId + ", name='" + name + '\''
        + ", season=" + season + ", episode=" + episode + ", year=" + year + ", phrases=" + phrases
        + '}';
  }
}
