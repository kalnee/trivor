package org.kalnee.trivor.insights.domain;

import org.kalnee.trivor.insights.domain.dto.TranscriptDTO;
import org.kalnee.trivor.nlp.domain.Sentence;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "transcripts")
public class Transcript {

    @Id
    private String id;

    @Indexed
    private String externalId;

    private String name;

    private List<String> keywords;

    private List<Sentence> sentences;

    public Transcript() {
    }

    public Transcript(TranscriptDTO transcriptDTO, List<Sentence> sentences) {
        this.externalId = transcriptDTO.getExternalId();
        this.name = transcriptDTO.getName();
        this.keywords = transcriptDTO.getKeywords();
        this.sentences = sentences;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    @Override
    public String toString() {
        return "Transcript{" +
            "id='" + id + '\'' +
            ", externalId='" + externalId + '\'' +
            ", name='" + name + '\'' +
            '}';
    }
}
