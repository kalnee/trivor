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

package org.kalnee.trivor.nlp.insights.generators;

import org.kalnee.trivor.nlp.insights.processors.SubtitleProcessor;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URISyntaxException;
import java.util.Map;

import static org.kalnee.trivor.nlp.nlp.models.InsightsEnum.*;
import static org.junit.Assert.assertTrue;

public class FrequencyGeneratorTest {

    private static SubtitleProcessor subtitleProcessor;

    @BeforeClass
    public static void setUp() throws URISyntaxException {
        subtitleProcessor = new SubtitleProcessor
                .Builder(FrequencyGeneratorTest.class.getResource("/language/tt0238784-S01E01.srt").toURI())
                .withDuration(42)
                .build();
    }

    @Test
    public void testAdverbsFrequency() {
        assertTrue(getFrequency(ADVERBS_FREQUENCY.getCode(), "here") == 16);
    }

    @Test
    public void testAdjectivesFrequency() {
        assertTrue(getFrequency(ADJECTIVES_FREQUENCY.getCode(), "good") == 12);
    }

    @Test
    public void testVerbsFrequency() {
        assertTrue(getFrequency(VERBS_FREQUENCY.getCode(), "be") == 173);
    }

    @Test
    public void testModalsFrequency() {
        assertTrue(getFrequency(MODALS_FREQUENCY.getCode(), "can") == 32);
    }

    @Test
    public void testNounsFrequency() {
        assertTrue(getFrequency(NOUNS_FREQUENCY.getCode(), "school") == 32);
    }

    @Test
    public void testComparativesFrequency() {
        assertTrue(getFrequency(COMPARATIVES_FREQUENCY.getCode(), "smaller") == 1);
    }

    @Test
    public void testSuperlativesFrequency() {
        assertTrue(getFrequency(SUPERLATIVES_FREQUENCY.getCode(), "least") == 2);
    }

    @Test
    public void testWhWordsFrequency() {
        assertTrue(getFrequency(WH_FREQUENCY.getCode(), "what") == 21);
    }

    @Test
    public void testPrepositionsFrequency() {
        assertTrue(getFrequency(PREPOSITIONS_FREQUENCY.getCode(), "at") == 22);
    }

    @SuppressWarnings("unchecked")
    private Integer getFrequency(String code, String word) {
        return ((Map<String, Integer>) subtitleProcessor.getInsights().get(code)).get(word);
    }
}
