package org.kalnee.trivor.client.dto;

public class SubtitleRequestDTO {

  private String imdbId;

  public SubtitleRequestDTO() {}

  public SubtitleRequestDTO(String imdbId) {
    this.imdbId = imdbId;
  }

  public String getImdbId() {
    return imdbId;
  }

  public void setImdbId(String imdbId) {
    this.imdbId = imdbId;
  }
}
