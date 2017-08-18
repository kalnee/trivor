/* Copyright (c) 2016 Kalnee
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.kalnee.trivor.nlp.nlp.models;

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
