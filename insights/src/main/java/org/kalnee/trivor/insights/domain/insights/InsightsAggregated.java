package org.kalnee.trivor.insights.domain.insights;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;

@Document(collection = "insights.aggregated")
public class InsightsAggregated extends AbstractInsights {

    @Id
    private BigInteger id;

    @Indexed
    private String imdbId;

    public InsightsAggregated() {
    }

    public InsightsAggregated(String imdbId) {
        this.imdbId = imdbId;
    }

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
}
