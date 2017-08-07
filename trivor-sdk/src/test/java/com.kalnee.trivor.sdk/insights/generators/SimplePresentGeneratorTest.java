package com.kalnee.trivor.sdk.insights.generators;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kalnee.trivor.sdk.insights.generators.tenses.SimplePresentGenerator;
import com.kalnee.trivor.sdk.models.Insight;
import com.kalnee.trivor.sdk.models.Sentence;
import com.kalnee.trivor.sdk.models.Subtitle;
import com.kalnee.trivor.sdk.utils.TestUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class SimplePresentGeneratorTest {

  private Subtitle getSubtitle() throws IOException {
    final List<Sentence> sentences = TestUtils.readSentences(
        "fixtures/sentences.json", new TypeReference<List<Sentence>>() {}
    );
    final Subtitle subtitle = new Subtitle();
    subtitle.setSentences(sentences);

    return subtitle;
  }

  @Test
  public void testFindSimplePresent() throws IOException {
    final SimplePresentGenerator spg = new SimplePresentGenerator();
    final Insight<List<String>> insight = spg.getInsight(getSubtitle());

    assertTrue("should've have identified 3 sentences", 3 == insight.getValue().size());
  }
}
