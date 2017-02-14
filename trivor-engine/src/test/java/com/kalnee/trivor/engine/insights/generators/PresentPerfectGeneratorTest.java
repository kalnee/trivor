package com.kalnee.trivor.engine.insights.generators;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kalnee.trivor.engine.models.Insight;
import com.kalnee.trivor.engine.models.Sentence;
import com.kalnee.trivor.engine.models.Subtitle;
import com.kalnee.trivor.engine.utils.TestUtils;

public class PresentPerfectGeneratorTest {

  private Subtitle getSubtitle() throws IOException {
    final List<Sentence> sentences = TestUtils.readSentences(
        "fixtures/sentences.json", new TypeReference<List<Sentence>>() {}
    );
    final Subtitle subtitle = new Subtitle();
    subtitle.setSentences(sentences);

    return subtitle;
  }

  @Test
  public void testFindPresentPerfect() throws IOException {
    final PresentPerfectGenerator spg = new PresentPerfectGenerator();
    final Insight<Set<String>> insight = spg.getInsight(getSubtitle());

    assertTrue("should've have identified 3 sentences", 3 == insight.getValue().size());
  }
}
