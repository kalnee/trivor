package com.kalnee.trivor.sdk.models

enum class InsightsEnum constructor(val code: String, val description: String) {

    NUM_SENTENCES("number-of-sentences", "Number of Sentences"),
    FREQUENT_ADJECTIVES("frequent-adjectives", "Most Frequently Adjectives"),
    FREQUENT_COMPARATIVES("frequent-comparatives", "Most Frequently Used Comparatives"),
    FREQUENT_NOUNS("frequent-nouns", "Most Frequently Used Nouns"),
    FREQUENT_SENTENCES("frequent-sentences", "Most Frequently Used Sentences"),
    FREQUENT_SUPERLATIVES("frequent-superlatives", "Most Frequently Used Superlatives")
}
