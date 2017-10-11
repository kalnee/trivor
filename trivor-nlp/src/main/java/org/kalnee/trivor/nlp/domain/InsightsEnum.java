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

package org.kalnee.trivor.nlp.domain;

public enum InsightsEnum {

    NUMBER_SENTENCES("number-sentences", "Number of Sentences"),
    RATE_OF_SPEECH("rate-of-speech", "Rate of Speech"),
    FREQUENCY_RATE("frequency-rate", "Frequency Rate"),

    ADJECTIVES_USAGE("adjectives-usage", "Adjectives Usage"),
    COMPARATIVES_USAGE("comparatives-usage", "Comparatives Usage"),
    NOUNS_USAGE("nouns-usage", "Nouns Usage"),
    SUPERLATIVES_USAGE("superlatives-usage", "Superlatives Usage"),
    VERBS_USAGE("verbs-usage", "Verbs Usage"),
    MODALS_USAGE("modals-usage", "Modals Usage"),
    ADVERBS_USAGE("adverbs-usage", "Adverbs Usage"),
    WH_USAGE("wh-usage", "Wh-words Usage"),
    PREPOSITIONS_USAGE("prepositions-usage", "Prepositions Usage"),

    FREQUENT_SENTENCES("frequent-sentences", "Most Frequently Used Sentences"),

    FUTURE_PERFECT("future-perfect-tense", "Future Perfect"),
    FUTURE_PROGRESSIVE("future-progressive-tense", "Future Progressive"),
    PAST_PERFECT("past-perfect-tense", "Past Perfect"),
    PAST_PROGRESSIVE("past-progressive-tense", "Past Progressive"),
    PRESENT_PERFECT("present-perfect-tense", "Present Perfect"),
    PRESENT_PROGRESSIVE("present-progressive-tense", "Present Progressive"),
    SIMPLE_FUTURE("simple-future-tense", "Simple Future"),
    SIMPLE_PAST("simple-past-tense", "Simple Past"),
    SIMPLE_PRESENT("simple-present-tense", "Simple Present"),
    MIXED_TENSE("mixed-tense", "Mixed Tense"),
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
