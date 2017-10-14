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

package org.kalnee.trivor.nlp.insights.generators.vocabulary;

import org.junit.BeforeClass;
import org.junit.Test;
import org.kalnee.trivor.nlp.domain.WordUsage;
import org.kalnee.trivor.nlp.insights.processors.SubtitleProcessor;

import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class WordUsageGeneratorTest {

    private static SubtitleProcessor sp;

    @BeforeClass
    public static void setUp() throws URISyntaxException {
        sp = new SubtitleProcessor
                .Builder(WordUsageGeneratorTest.class.getResource("/language/tt0238784-S01E01.srt").toURI())
                .withDuration(42)
                .build();
    }

    @Test
    public void testAdverbsFrequency() {
        assertTrue(getFrequency(sp.getResult().getVocabulary().getAdverbs(), "here") == 16);
    }

    @Test
    public void testAdjectivesFrequency() {
        assertTrue(getFrequency(sp.getResult().getVocabulary().getAdjectives(), "good") == 12);
    }

    @Test
    public void testVerbsFrequency() {
        assertTrue(getFrequency(sp.getResult().getVocabulary().getVerbs(), "be") == 173);
    }

    @Test
    public void testModalsFrequency() {
        assertTrue(getFrequency(sp.getResult().getVocabulary().getModals(), "can") == 32);
    }

    @Test
    public void testNounsFrequency() {
        assertTrue(getFrequency(sp.getResult().getVocabulary().getNouns(), "school") == 32);
    }

    @Test
    public void testComparativesFrequency() {
        assertTrue(getFrequency(sp.getResult().getVocabulary().getComparatives(), "smaller") == 1);
    }

    @Test
    public void testSuperlativesFrequency() {
        assertTrue(getFrequency(sp.getResult().getVocabulary().getSuperlatives(), "least") == 2);
    }

    @Test
    public void testWhWordsFrequency() {
        assertTrue(getFrequency(sp.getResult().getVocabulary().getWhWords(), "what") == 21);
    }

    @Test
    public void testPrepositionsFrequency() {
        assertTrue(getFrequency(sp.getResult().getVocabulary().getPrepositions(), "at") == 22);
    }

    @SuppressWarnings("unchecked")
    private Integer getFrequency(List<WordUsage> words, String word) {
        return words.stream()
                .filter(w -> w.getWord().equals(word))
                .mapToInt(w -> w.getSentences().size())
                .findFirst().getAsInt();
    }
}
