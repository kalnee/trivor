package com.kalnee.trivor.sdk.insights.generators;

import com.kalnee.trivor.sdk.insights.processors.SubtitleProcessor;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URISyntaxException;
import java.util.Map;

import static com.kalnee.trivor.sdk.models.InsightsEnum.*;
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
        assertTrue(getFrequency(ADVERBS_FREQUENCY.getCode(), "here") == 18);
    }

    @Test
    public void testAdjectivesFrequency() {
        assertTrue(getFrequency(ADJECTIVES_FREQUENCY.getCode(), "good") == 12);
    }

    @Test
    public void testVerbsFrequency() {
        assertTrue(getFrequency(VERBS_FREQUENCY.getCode(), "be") == 185);
    }

    @Test
    public void testModalsFrequency() {
        assertTrue(getFrequency(MODALS_FREQUENCY.getCode(), "can") == 33);
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

    @SuppressWarnings("unchecked")
    private Long getFrequency(String code, String word) {
        return ((Map<String, Long>) subtitleProcessor.getInsights().get(code)).get(word);
    }
}
