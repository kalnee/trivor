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

    NUMBER_SENTENCES("number-sentences"),
    RATE_OF_SPEECH("rate-of-speech"),
    FREQUENCY_RATE("frequency-rate"),

    ADJECTIVES_USAGE("adjectives-usage"),
    COMPARATIVES_USAGE("comparatives-usage"),
    NOUNS_USAGE("nouns-usage"),
    SUPERLATIVES_USAGE("superlatives-usage"),
    VERBS_USAGE("verbs-usage"),
    MODALS_USAGE("modals-usage"),
    ADVERBS_USAGE("adverbs-usage"),
    WH_USAGE("wh-usage"),
    PREPOSITIONS_USAGE("prepositions-usage"),

    FREQUENT_SENTENCES("frequent-sentences"),
    FREQUENT_CHUNKS("frequent-chunks"),

    FUTURE_PERFECT("future-perfect-tense"),
    FUTURE_PROGRESSIVE("future-progressive-tense"),
    PAST_PERFECT("past-perfect-tense"),
    PAST_PROGRESSIVE("past-progressive-tense"),
    PRESENT_PERFECT("present-perfect-tense"),
    PRESENT_PROGRESSIVE("present-progressive-tense"),
    SIMPLE_FUTURE("simple-future-tense"),
    SIMPLE_PAST("simple-past-tense"),
    SIMPLE_PRESENT("simple-present-tense"),
    MIXED_TENSE("mixed-tense"),
    NON_SENTENCES("non-sentences"),

    SENTIMENT_ANALYSIS("sentiment-analysis");

    private String code;

    InsightsEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
