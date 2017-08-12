package com.kalnee.trivor.nlp.insights.generators;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kalnee.trivor.nlp.insights.generators.tenses.FuturePerfectGenerator;
import com.kalnee.trivor.nlp.nlp.models.Insight;
import com.kalnee.trivor.nlp.nlp.models.Sentence;
import com.kalnee.trivor.nlp.nlp.models.Subtitle;
import com.kalnee.trivor.nlp.utils.TestUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class FuturePerfectGeneratorTest {
    private Subtitle getSubtitle() throws IOException {
        final List<Sentence> sentences = TestUtils.readSentences(
                "fixtures/sentences.json", new TypeReference<List<Sentence>>() {}
        );
        final Subtitle subtitle = new Subtitle();
        subtitle.setSentences(sentences);

        return subtitle;
    }

    @Test
    public void testFindFuturePerfect() throws IOException {
        final FuturePerfectGenerator fpg = new FuturePerfectGenerator();
        final Insight<List<String>> insight = fpg.getInsight(getSubtitle());

        assertTrue("should've have identified 3 sentences", 3 == insight.getValue().size());
    }
}
