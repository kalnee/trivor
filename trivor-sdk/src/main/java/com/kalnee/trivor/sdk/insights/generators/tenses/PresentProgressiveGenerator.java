package com.kalnee.trivor.sdk.insights.generators.tenses;

import com.kalnee.trivor.sdk.insights.generators.InsightGenerator;
import com.kalnee.trivor.sdk.models.Insight;
import com.kalnee.trivor.sdk.models.Sentence;
import com.kalnee.trivor.sdk.models.Subtitle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.kalnee.trivor.sdk.models.InsightsEnum.PRESENT_PROGRESSIVE;
import static com.kalnee.trivor.sdk.models.TagsEnum.*;
import static com.kalnee.trivor.sdk.utils.CollectionUtils.*;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

public class PresentProgressiveGenerator implements InsightGenerator<List<String>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PresentProgressiveGenerator.class);

    private static final List<String> MUST_CONTAIN = Collections.singletonList(VBG.name());
    private static final List<String> MUST_CONTAIN_TAGS = Arrays.asList(PRP.name(), NNP.name(), NNPS.name());
    private static final List<String> MUST_NOT_CONTAIN = Arrays.asList(VBN.name(), VBD.name());
    private static final List<String> MUST_NOT_CONTAIN_WORDS = Arrays.asList(
            "Will", "will", "Won't", "won't", "'ll", "going to", "gonna"
    );

    @Override
    public String getDescription() {
        return PRESENT_PROGRESSIVE.getDescription();
    }

    @Override
    public String getCode() {
        return PRESENT_PROGRESSIVE.getCode();
    }

    @Override
    public Insight<List<String>> getInsight(Subtitle subtitle) {
        final List<String> sentences = subtitle.getSentences()
                .stream()
                .filter(s -> allMatch(s.getSentenceTags(), MUST_CONTAIN)
                        && anyMatch(s.getSentenceTags(), MUST_CONTAIN_TAGS)
                        && noneMatch(s.getSentenceTags(), MUST_NOT_CONTAIN)
                        && noneMatch(s.getSentence().toLowerCase(), MUST_NOT_CONTAIN_WORDS))
                .map(Sentence::getSentence)
                .collect(toList());

        LOGGER.info(
                format("%s: %d/%d (%.2f%%)", getCode(), sentences.size(), subtitle.getSentences().size(),
                (sentences.size() * 100d / subtitle.getSentences().size()))
        );

        return new Insight<>(getCode(), sentences);
    }
}
