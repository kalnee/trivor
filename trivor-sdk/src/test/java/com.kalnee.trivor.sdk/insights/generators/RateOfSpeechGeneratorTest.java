package com.kalnee.trivor.sdk.insights.generators;

import com.kalnee.trivor.sdk.insights.processors.SubtitleProcessor;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.kalnee.trivor.sdk.models.InsightsEnum.RATE_OF_SPEECH;
import static com.kalnee.trivor.sdk.models.RateOfSpeechEnum.FAST;
import static org.junit.Assert.assertTrue;

public class RateOfSpeechGeneratorTest {

    @Test
    public void testSelectCorrectRate() throws IOException, URISyntaxException {
        SubtitleProcessor sp = new SubtitleProcessor
                .Builder(RateOfSpeechGeneratorTest.class.getResource("/language/tt0238784-S01E01.srt").toURI())
                .withDuration(42)
                .build();

        assertTrue(FAST.name().equals(sp.getInsights().get(RATE_OF_SPEECH.getCode())));
    }
}
