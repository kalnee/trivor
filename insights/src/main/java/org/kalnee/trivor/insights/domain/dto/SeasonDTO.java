package org.kalnee.trivor.insights.domain.dto;

import java.util.ArrayList;
import java.util.List;

public class SeasonDTO {

    private Integer season;
    private List<Integer> episodes = new ArrayList<>();

    public SeasonDTO(Integer season, List<Integer> episodes) {
        this.season = season;
        this.episodes = episodes;
    }
    public SeasonDTO(Integer season, Integer episode) {
        this.season = season;
        this.episodes = episodes;
    }


    public SeasonDTO() {
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public List<Integer> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Integer> episodes) {
        this.episodes = episodes;
    }
}
