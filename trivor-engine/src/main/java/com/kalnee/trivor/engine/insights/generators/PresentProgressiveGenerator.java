package com.kalnee.trivor.engine.insights.generators;

import static com.kalnee.trivor.engine.utils.CollectionUtils.allMatch;
import static com.kalnee.trivor.engine.utils.CollectionUtils.anyMatch;
import static com.kalnee.trivor.engine.utils.CollectionUtils.noneMatch;
import static com.kalnee.trivor.engine.utils.TagsEnum.*;
import static java.util.stream.Collectors.toSet;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.kalnee.trivor.engine.models.Insight;
import com.kalnee.trivor.engine.models.Sentence;
import com.kalnee.trivor.engine.models.Subtitle;

public class PresentProgressiveGenerator implements InsightGenerator<Set<String>> {

  private static final List<String> MUST_CONTAIN = Collections.singletonList(VBG.name());
  private static final List<String> MUST_NOT_CONTAIN = Arrays.asList(VBN.name(), VBD.name());
  private static final List<String> MUST_NOT_CONTAIN_WORDS = Arrays.asList(
      "Will", "will", "Won't", "won't", "'ll", "going to", "gonna"
  );

  @Override
  public String getDescription() {
    return "Present Progressive Tense";
  }

  @Override
  public String getCode() {
    return "present-progressive";
  }

  @Override
  public Insight<Set<String>> getInsight(Subtitle subtitle) {
    final Set<String> sentences = subtitle.getSentences()
        .stream()
        .filter(s -> allMatch(s.getSentenceTags(), MUST_CONTAIN)
            && noneMatch(s.getSentenceTags(), MUST_NOT_CONTAIN)
            && noneMatch(s.getSentence().toLowerCase(), MUST_NOT_CONTAIN_WORDS))
        .map(Sentence::getSentence)
        .collect(toSet());

    return new Insight<>(getCode(), sentences);
  }
}
