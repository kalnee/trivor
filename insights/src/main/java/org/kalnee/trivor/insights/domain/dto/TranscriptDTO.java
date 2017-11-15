package org.kalnee.trivor.insights.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TranscriptDTO {

    @NotEmpty
    private String externalId;

    @NotEmpty
    private String name;

    private List<String> keywords;


    public TranscriptDTO(String externalId, String name) {
        this.externalId = externalId;
        this.name = name;
    }

    public TranscriptDTO() {
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public String toString() {
        return "TranscriptDTO{" +
            "externalId='" + externalId + '\'' +
            ", name='" + name + '\'' +
            '}';
    }
}
