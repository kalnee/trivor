package com.kalnee.trivor.engine.insights.generators;

import com.kalnee.trivor.engine.models.Insight;
import com.kalnee.trivor.engine.models.Sentence;
import com.kalnee.trivor.engine.models.Subtitle;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.kalnee.trivor.engine.utils.CollectionUtils.allMatch;
import static com.kalnee.trivor.engine.utils.CollectionUtils.noneMatch;
import static com.kalnee.trivor.engine.utils.TagsEnum.*;
import static java.util.stream.Collectors.toSet;

public class PresentProgressiveGenerator implements InsightGenerator<Set<String>> {

    private static final List<String> MUST_CONTAIN = Arrays.asList(PRP.name(), VBG.name(), VBP.name());
    private static final List<String> MUST_NOT_CONTAIN = Arrays.asList("going to", "gonna");

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
        final Set<String> presentProgressive = subtitle.getSentences()
                .stream()
                .filter(s -> allMatch(s.getSentenceTags(), MUST_CONTAIN)
                        && noneMatch(s.getSentence().toLowerCase(), MUST_NOT_CONTAIN))
                .map(Sentence::getSentence)
                .collect(toSet());

        return new Insight<>(getCode(), presentProgressive);
    }
}
