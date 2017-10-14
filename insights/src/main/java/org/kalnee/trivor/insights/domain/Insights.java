package org.kalnee.trivor.insights.domain;

import org.kalnee.trivor.nlp.domain.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Document(collection = "insights")
public class Insights {

    @Id
    private BigInteger id;

    @Indexed
    private String imdbId;

    private String subtitleId;

    private Integer numberOfSentences;
    private Map<SentimentEnum, BigDecimal> sentimentAnalysis;
    private RateOfSpeech rateOfSpeech;
    private List<SentenceFrequency> frequentSentences;
    private List<FrequencyRate> frequencyRate;
    private List<PhrasalVerbUsage> phrasalVerbs;

    private Vocabulary vocabulary;
    private VerbTenses verbTenses;

    public Insights(String imdbId, String subtitleId, Result result) {
        this.imdbId = imdbId;
        this.subtitleId = subtitleId;
        this.numberOfSentences = result.getNumberOfSentences();
        this.sentimentAnalysis = result.getSentimentAnalysis();
        this.rateOfSpeech = result.getRateOfSpeech();
        this.frequentSentences = result.getFrequentSentences();
        this.frequencyRate = result.getFrequencyRate();
        this.phrasalVerbs = result.getPhrasalVerbs();
        this.vocabulary = result.getVocabulary();
        this.verbTenses = result.getVerbTenses();
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

    public Integer getNumberOfSentences() {
        return numberOfSentences;
    }

    public void setNumberOfSentences(Integer numberOfSentences) {
        this.numberOfSentences = numberOfSentences;
    }

    public Map<SentimentEnum, BigDecimal> getSentimentAnalysis() {
        return sentimentAnalysis;
    }

    public void setSentimentAnalysis(Map<SentimentEnum, BigDecimal> sentimentAnalysis) {
        this.sentimentAnalysis = sentimentAnalysis;
    }

    public RateOfSpeech getRateOfSpeech() {
        return rateOfSpeech;
    }

    public void setRateOfSpeech(RateOfSpeech rateOfSpeech) {
        this.rateOfSpeech = rateOfSpeech;
    }

    public List<SentenceFrequency> getFrequentSentences() {
        return frequentSentences;
    }

    public void setFrequentSentences(List<SentenceFrequency> frequentSentences) {
        this.frequentSentences = frequentSentences;
    }

    public List<FrequencyRate> getFrequencyRate() {
        return frequencyRate;
    }

    public void setFrequencyRate(List<FrequencyRate> frequencyRate) {
        this.frequencyRate = frequencyRate;
    }

    public List<PhrasalVerbUsage> getPhrasalVerbs() {
        return phrasalVerbs;
    }

    public void setPhrasalVerbs(List<PhrasalVerbUsage> phrasalVerbs) {
        this.phrasalVerbs = phrasalVerbs;
    }

    public Vocabulary getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(Vocabulary vocabulary) {
        this.vocabulary = vocabulary;
    }

    public VerbTenses getVerbTenses() {
        return verbTenses;
    }

    public void setVerbTenses(VerbTenses verbTenses) {
        this.verbTenses = verbTenses;
    }

    @Override
    public String toString() {
        return "Insights{" +
            "id=" + id +
            ", imdbId='" + imdbId + '\'' +
            ", subtitleId='" + subtitleId + '\'' +
            '}';
    }
}
