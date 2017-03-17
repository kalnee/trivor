package com.kalnee.trivor.engine.insights.generators;


import com.kalnee.trivor.engine.models.Insight;
import com.kalnee.trivor.engine.models.Sentence;
import com.kalnee.trivor.engine.models.Subtitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import static com.kalnee.trivor.engine.utils.CollectionUtils.allMatch;
import static com.kalnee.trivor.engine.utils.CollectionUtils.anyMatch;
import static com.kalnee.trivor.engine.utils.TagsEnum.*;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

public class FuturePerfectGenerator implements InsightGenerator<List<String>> {

  private static final Logger LOGGER = LoggerFactory.getLogger(PastPerfectGenerator.class);
  static final String FUTURE_PERFECT = "future-perfect";

  private static final List<String> MUST_CONTAIN_ALL = Arrays.asList(MD.name(), VBN.name());
  private static final List<String> MUST_CONTAIN_ONE = Arrays.asList(PRP.name(), NNP.name(), NNPS.name());
  private static final List<String> MUST_CONTAIN_HAVE = Arrays.asList("Have", "have");
  private static final List<String> MUST_CONTAIN_MODAL = Arrays.asList(
      "will", "Will", "won't", "Won't", "'ll"
  );

  @Override
  public String getDescription() {
    return "Future Perfect Tense";
  }

  @Override
  public String getCode() {
    return FUTURE_PERFECT;
  }

  @Override
  public Insight<List<String>> getInsight(Subtitle subtitle) {
    final List<String> sentences = subtitle.getSentences().stream()
        .filter(s -> anyMatch(s.getSentence(), MUST_CONTAIN_HAVE)
            && anyMatch(s.getSentence(), MUST_CONTAIN_MODAL)
            && allMatch(s.getSentenceTags(), MUST_CONTAIN_ALL)
            && anyMatch(s.getSentenceTags(), MUST_CONTAIN_ONE))
        .map(Sentence::getSentence).collect(toList());

    LOGGER.info(
        format("%s: %d/%d (%.2f%%)", getCode(), sentences.size(), subtitle.getSentences().size(),
        (sentences.size() * 100d / subtitle.getSentences().size()))
    );

    return new Insight<>(getCode(), sentences);
  }
}

