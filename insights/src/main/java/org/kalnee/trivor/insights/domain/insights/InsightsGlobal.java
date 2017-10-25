package org.kalnee.trivor.insights.domain.insights;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;

@Document(collection = "insights.global")
public class InsightsGlobal extends AbstractInsights {

    @Id
    private BigInteger id;

    public InsightsGlobal() {
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

}
