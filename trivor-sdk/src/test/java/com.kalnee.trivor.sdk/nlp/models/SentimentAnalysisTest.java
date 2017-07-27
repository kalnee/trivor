package com.kalnee.trivor.sdk.nlp.models;

import com.kalnee.trivor.sdk.models.SentimentEnum;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.kalnee.trivor.sdk.models.SentimentEnum.*;
import static org.junit.Assert.assertEquals;

public class SentimentAnalysisTest {

    private static SentimentAnalysis sentimentAnalysis;

    @BeforeClass
    public static void setUp() {
        sentimentAnalysis = new SentimentAnalysis();
    }

    @Test
    public void testPositiveSentences() {
        final SentimentEnum simpleSentenceResult = sentimentAnalysis.categorize(getTokens("i 'm glad you came"));
        assertEquals(POSITIVE, simpleSentenceResult);

        final SentimentEnum longSentenceResult = sentimentAnalysis.categorize(
                getTokens("i love you so much that i can 't even wait until any longer")
        );
        assertEquals(POSITIVE, longSentenceResult);
    }

    @Test
    public void testNegativeSentences() {
        final SentimentEnum simpleSentenceResult = sentimentAnalysis.categorize(getTokens("that 's bad"));
        assertEquals(NEGATIVE, simpleSentenceResult);

        final SentimentEnum longSentenceResult = sentimentAnalysis.categorize(
                getTokens("that 's the worst thing you 've ever done to him")
        );
        assertEquals(NEGATIVE, longSentenceResult);
    }

    @Test
    public void testNeutralSentences() {
        final SentimentEnum simpleSentenceResult = sentimentAnalysis.categorize(getTokens("this is my new apartment"));
        assertEquals(NEUTRAL, simpleSentenceResult);

        final SentimentEnum longSentenceResult = sentimentAnalysis.categorize(
                getTokens("my new apartment has a desk and four chairs")
        );
        assertEquals(NEUTRAL, longSentenceResult);
    }

    private List<String> getTokens(String sentence) {
        return Arrays.asList(sentence.split(" "));
    }
}
