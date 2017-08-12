package com.kalnee.trivor.nlp.nlp.models;

public enum InsightsEnum {

    RATE_OF_SPEECH("rate-of-speech", "Rate of Speech"),
    NUM_SENTENCES("number-of-sentences", "Number of Sentences"),
    ADJECTIVES_FREQUENCY("adjectives-frequency", "Adjectives Frequency"),
    COMPARATIVES_FREQUENCY("comparatives-frequency", "Comparatives Frequency"),
    NOUNS_FREQUENCY("nouns-frequency", "Nouns Frequency"),
    SUPERLATIVES_FREQUENCY("superlatives-frequency", "Superlatives Frequency"),
    VERBS_FREQUENCY("verbs-frequency", "Verbs Frequency"),
    MODALS_FREQUENCY("modals-frequency", "Modals Frequency"),
    ADVERBS_FREQUENCY("adverbs-frequency", "Adverbs Frequency"),
    WH_FREQUENCY("wh-frequency", "Wh-words Frequency"),
    PREPOSITIONS_FREQUENCY("prepositions-frequency", "Prepositions Frequency"),
    ADJECTIVES_SENTENCES("adjectives-sentences", "Adjectives Sentences"),
    COMPARATIVES_SENTENCES("comparatives-sentences", "Comparatives Sentences"),
    NOUNS_SENTENCES("nouns-sentences", "Nouns Sentences"),
    SUPERLATIVES_SENTENCES("superlatives-sentences", "Superlatives Sentences"),
    VERBS_SENTENCES("verbs-sentences", "Verbs Sentences"),
    MODALS_SENTENCES("modals-sentences", "Modals Sentences"),
    ADVERBS_SENTENCES("adverbs-sentences", "Adverbs Sentences"),
    WH_SENTENCES("wh-sentences", "Wh-words Sentences"),
    PREPOSITIONS_SENTENCES("prepositions-sentences", "Prepositions Sentences"),
    FREQUENT_SENTENCES("frequent-sentences", "Most Frequently Used Sentences"),
    FUTURE_PERFECT("future-perfect", "Future Perfect"),
    FUTURE_PROGRESSIVE("future-progressive", "Future Progressive"),
    PAST_PERFECT("past-perfect", "Past Perfect"),
    PAST_PROGRESSIVE("past-progressive", "Past Progressive"),
    PRESENT_PERFECT("present-perfect", "Present Perfect"),
    PRESENT_PROGRESSIVE("present-progressive", "Present Progressive"),
    SIMPLE_FUTURE("simple-future", "Simple Future"),
    SIMPLE_PAST("simple-past", "Simple Past"),
    SIMPLE_PRESENT("simple-present", "Simple Present"),
    NON_SENTENCES("non-sentences", "Non Sentences"),
    SENTIMENT_ANALYSIS("sentiment-analysis", "Sentiment Analysis");

    private String code;
    private String description;

    InsightsEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}