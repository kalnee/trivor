package org.kalnee.trivor.insights.domain.insights;

import org.kalnee.trivor.nlp.domain.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;

@Document(collection = "insights")
public class Insights extends AbstractInsights {

    @Id
    private BigInteger id;

    @Indexed
    private String imdbId;

    private String subtitleId;

    public Insights(String imdbId, String subtitleId, Result result) {
        super(result);
        this.imdbId = imdbId;
        this.subtitleId = subtitleId;
    }

    Insights() {
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

    public String getSubtitleId() {
        return subtitleId;
    }

    public void setSubtitleId(String subtitleId) {
        this.subtitleId = subtitleId;
    }
}
