package org.kalnee.trivor.insights.domain;

import org.kalnee.trivor.insights.domain.dto.SubtitleDTO;
import org.kalnee.trivor.insights.domain.dto.TypeEnum;
import org.kalnee.trivor.nlp.nlp.models.Sentence;
import org.kalnee.trivor.nlp.nlp.models.SentimentEnum;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Document(collection = "subtitles")
public class Subtitle {

    @Id
    private String id;

    private String imdbId;

    private String name;

    private Integer season;

    private Integer episode;

    private Integer year;

    private Integer duration;

    private TypeEnum type;

    private List<Sentence> sentences;

    private List<String> genres;

    private List<String> keywords;

    private Map<SentimentEnum, BigDecimal> sentiment;

    public Subtitle(String id, String imdbId, String name, Integer season, Integer episode,
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

    public Subtitle(SubtitleDTO subtitleDTO, List<Sentence> sentences, Map<SentimentEnum, BigDecimal> sentiment) {
        this.imdbId = subtitleDTO.getImdbId();
        this.name = subtitleDTO.getName();
        this.season = subtitleDTO.getSeason();
        this.episode = subtitleDTO.getEpisode();
        this.year = subtitleDTO.getYear();
        this.type = subtitleDTO.getType();
        this.duration = subtitleDTO.getDuration();
        this.sentences = sentences;
        this.genres = subtitleDTO.getGenres();
        this.keywords = subtitleDTO.getKeywords();
        this.sentiment = sentiment;
    }

    public Subtitle() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public Map<SentimentEnum, BigDecimal> getSentiment() {
        return sentiment;
    }

    public void setSentiment(Map<SentimentEnum, BigDecimal> sentiment) {
        this.sentiment = sentiment;
    }

    @Override
    public String toString() {
        return "Subtitle{" +
                "id=" + id +
                ", imdbId='" + imdbId + '\'' +
                ", name='" + name + '\'' +
                ", season=" + season +
                ", episode=" + episode +
                ", year=" + year +
                ", duration=" + duration +
                ", type=" + type +
                ", sentences=" + sentences +
                ", genres=" + genres +
                ", keywords=" + keywords +
                '}';
    }
}
