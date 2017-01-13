package com.kalnee.trivor.engine.dto;

public class SubtitleDTO {

	private String imdbId;
	private String name;
	private Integer season;
	private Integer episode;
	private Integer year;
	private TypeEnum type;

	public SubtitleDTO(String imdbId, String name, Integer season, Integer episode,
		Integer year, TypeEnum type) {
		this.imdbId = imdbId;
		this.name = name;
		this.season = season;
		this.episode = episode;
		this.year = year;
		this.type = type;
	}

	public SubtitleDTO(String imdbId, String name, Integer year, TypeEnum type) {
		this.imdbId = imdbId;
		this.name = name;
		this.year = year;
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

	public TypeEnum getType() {
		return type;
	}

	public void setType(TypeEnum type) {
		this.type = type;
	}
}
