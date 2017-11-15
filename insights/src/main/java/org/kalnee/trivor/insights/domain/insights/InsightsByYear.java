package org.kalnee.trivor.insights.domain.insights;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;

@Document(collection = "insights.year")
public class InsightsByYear extends GenericInsights {

    @Id
    private BigInteger id;

    @Indexed
    private Integer year;

    public InsightsByYear() {
    }

    public InsightsByYear(Integer year) {
        this.year = year;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
