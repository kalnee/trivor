package com.kalnee.trivor.nlp.insights.generators.sentences;

import com.kalnee.trivor.nlp.insights.generators.InsightGenerator;
import com.kalnee.trivor.nlp.nlp.models.Insight;
import com.kalnee.trivor.nlp.nlp.models.Subtitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.kalnee.trivor.nlp.nlp.models.InsightsEnum.VERBS_SENTENCES;
import static com.kalnee.trivor.nlp.nlp.models.TagsEnum.*;

public class VerbsSentencesGenerator extends SentencesExampleGenerator implements InsightGenerator<Map<String, Set<String>>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(VerbsSentencesGenerator.class);

    @Override
    List<String> getTags() {
        return Arrays.asList(
                VB.name(), VBD.name(), VBG.name(), VBN.name(), VBP.name(), VBZ.name()
        );
    }

    @Override
    public String getDescription() {
        return VERBS_SENTENCES.getDescription();
    }

    @Override
    public String getCode() {
        return VERBS_SENTENCES.getCode();
    }

    @Override
    public Insight<Map<String, Set<String>>> getInsight(Subtitle subtitle) {
        final Map<String, Set<String>> sentences = getSentences(subtitle);
        LOGGER.info("{} - {}", getCode(), sentences);

        return new Insight<>(getCode(), sentences);
    }
}
