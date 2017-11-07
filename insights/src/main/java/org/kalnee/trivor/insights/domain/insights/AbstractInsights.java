package org.kalnee.trivor.insights.domain.insights;

import org.kalnee.trivor.nlp.domain.*;

import java.math.BigDecimal;
import java.util.*;

public class AbstractInsights {

    private Integer numberOfSentences = 0;
    private Map<SentimentEnum, BigDecimal> sentimentAnalysis = new HashMap<>();
    private RateOfSpeech rateOfSpeech = new RateOfSpeech();
    private Set<SentenceFrequency> frequentSentences = new HashSet<>();
    private Set<ChunkFrequency> frequentChunks = new HashSet<>();
    private List<FrequencyRate> frequencyRate = new ArrayList<>();
    private Set<PhrasalVerbUsage> phrasalVerbs = new HashSet<>();

    private Vocabulary vocabulary = new Vocabulary();
    private VerbTenses verbTenses = new VerbTenses();

    public AbstractInsights(Result result) {
        this.numberOfSentences = result.getNumberOfSentences();
        this.sentimentAnalysis = result.getSentimentAnalysis();
        this.rateOfSpeech = result.getRateOfSpeech();
        this.frequentSentences = result.getFrequentSentences();
        this.frequentChunks = result.getFrequentChunks();
        this.frequencyRate = result.getFrequencyRate();
        this.phrasalVerbs = result.getPhrasalVerbs();
        this.vocabulary = result.getVocabulary();
        this.verbTenses = result.getVerbTenses();
    }

    public AbstractInsights() {
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

    public Set<SentenceFrequency> getFrequentSentences() {
        return frequentSentences;
    }

    public void setFrequentSentences(Set<SentenceFrequency> frequentSentences) {
        this.frequentSentences = frequentSentences;
    }

    public Set<ChunkFrequency> getFrequentChunks() {
        return frequentChunks;
    }

    public void setFrequentChunks(Set<ChunkFrequency> frequentChunks) {
        this.frequentChunks = frequentChunks;
    }

    public List<FrequencyRate> getFrequencyRate() {
        return frequencyRate;
    }

    public void setFrequencyRate(List<FrequencyRate> frequencyRate) {
        this.frequencyRate = frequencyRate;
    }

    public Set<PhrasalVerbUsage> getPhrasalVerbs() {
        return phrasalVerbs;
    }

    public void setPhrasalVerbs(Set<PhrasalVerbUsage> phrasalVerbs) {
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

    public void update(AbstractInsights abstractInsights) {
        this.numberOfSentences = abstractInsights.getNumberOfSentences();
        this.sentimentAnalysis = abstractInsights.getSentimentAnalysis();
        this.rateOfSpeech = abstractInsights.getRateOfSpeech();
        this.frequentSentences = abstractInsights.getFrequentSentences();
        this.frequentChunks = abstractInsights.getFrequentChunks();
        this.frequencyRate = abstractInsights.getFrequencyRate();
        this.phrasalVerbs = abstractInsights.getPhrasalVerbs();
        this.vocabulary = abstractInsights.getVocabulary();
        this.verbTenses = abstractInsights.getVerbTenses();
    }
}
