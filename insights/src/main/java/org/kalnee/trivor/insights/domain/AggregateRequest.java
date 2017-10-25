package org.kalnee.trivor.insights.domain;

import java.util.Set;

public class AggregateRequest {

    private Set<String> imdbIds;

    public Set<String> getImdbIds() {
        return imdbIds;
    }

    public void setImdbIds(Set<String> imdbIds) {
        this.imdbIds = imdbIds;
    }
}
