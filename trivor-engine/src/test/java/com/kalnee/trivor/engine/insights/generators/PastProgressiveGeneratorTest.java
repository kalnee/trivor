package com.kalnee.trivor.engine.insights.generators;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kalnee.trivor.engine.models.Insight;
import com.kalnee.trivor.engine.models.Sentence;
import com.kalnee.trivor.engine.models.Subtitle;
import com.kalnee.trivor.engine.utils.TestUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class PastProgressiveGeneratorTest {
    private Subtitle getSubtitle() throws IOException {
        final List<Sentence> sentences = TestUtils.readSentences(
                "fixtures/sentences.json", new TypeReference<List<Sentence>>() {}
        );
        final Subtitle subtitle = new Subtitle();
        subtitle.setSentences(sentences);

        return subtitle;
    }

    @Test
    public void testFindPastProgressive() throws IOException {
        final PastProgressiveGenerator ppg = new PastProgressiveGenerator();
        final Insight<Set<String>> insight = ppg.getInsight(getSubtitle());

        assertTrue("should've have identified 3 sentences", 3 == insight.getValue().size());
    }
}
