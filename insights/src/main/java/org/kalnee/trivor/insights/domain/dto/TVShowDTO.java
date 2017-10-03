package org.kalnee.trivor.insights.domain.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TVShowDTO {

    private Map<Integer, Set<Integer>> seasons = new HashMap<>();

    public TVShowDTO(Map<Integer, Set<Integer>> seasons) {
        this.seasons = seasons;
    }

    public TVShowDTO() {
    }

    public Map<Integer, Set<Integer>> getSeasons() {
        return seasons;
    }

    public void setSeasons(Map<Integer, Set<Integer>> seasons) {
        this.seasons = seasons;
    }
}
