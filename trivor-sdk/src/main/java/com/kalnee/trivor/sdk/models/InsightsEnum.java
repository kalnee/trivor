package com.kalnee.trivor.sdk.models;

public enum InsightsEnum {

    NUM_SENTENCES("number-of-sentences", "Number of Sentences"),
    FREQUENT_ADJECTIVES("frequent-adjectives", "Most Frequently Adjectives"),
    FREQUENT_COMPARATIVES("frequent-comparatives", "Most Frequently Used Comparatives"),
    FREQUENT_NOUNS("frequent-nouns", "Most Frequently Used Nouns"),
    FREQUENT_SENTENCES("frequent-sentences", "Most Frequently Used Sentences"),
    FREQUENT_SUPERLATIVES("frequent-superlatives", "Most Frequently Used Superlatives"),
    FUTURE_PERFECT("future-perfect", "Future Perfect"),
    FUTURE_PROGRESSIVE("future-progressive", "Future Progressive"),
    PAST_PERFECT("past-perfect", "Past Perfect"),
    PAST_PROGRESSIVE("past-progressive", "Past Progressive"),
    PRESENT_PERFECT("present-perfect", "Present Perfect"),
    PRESENT_PROGRESSIVE("present-progressive", "Present Progressive"),
    SIMPLE_FUTURE("simple-future", "Simple Future"),
    SIMPLE_PAST("simple-past", "Simple Past"),
    SIMPLE_PRESENT("simple-present", "Simple Present"),
    NON_SENTENCES("non-sentences", "Non Sentences");

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
