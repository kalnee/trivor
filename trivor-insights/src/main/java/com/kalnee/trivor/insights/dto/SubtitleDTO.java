package com.kalnee.trivor.insights.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SubtitleDTO {

    @NotEmpty
    private String imdbId;

    @NotEmpty
    private String name;

    private Integer season;

    private Integer episode;

    @NotNull
    private Integer year;

    @NotNull
    private Integer duration;

    @NotNull
    private TypeEnum type;

    private String status;

    @NotNull
    private List<String> genres;

    public SubtitleDTO(String imdbId, String name, Integer season, Integer episode, Integer year, Integer duration,
                       TypeEnum type) {
        this.imdbId = imdbId;
        this.name = name;
        this.season = season;
        this.episode = episode;
        this.year = year;
        this.duration = duration;
        this.type = type;
    }

    public SubtitleDTO(String imdbId, String name, Integer year, Integer duration, TypeEnum type) {
        this.imdbId = imdbId;
        this.name = name;
        this.year = year;
        this.duration = duration;
        this.type = type;
    }

    public SubtitleDTO() {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    @JsonIgnore
    @Override
    public String toString() {
        return "SubtitleDTO{" +
                "imdbId='" + imdbId + '\'' +
                ", name='" + name + '\'' +
                ", season=" + season +
                ", episode=" + episode +
                ", year=" + year +
                ", duration=" + duration +
                ", type=" + type +
                ", status='" + status + '\'' +
                ", genres=" + genres +
                '}';
    }
}
