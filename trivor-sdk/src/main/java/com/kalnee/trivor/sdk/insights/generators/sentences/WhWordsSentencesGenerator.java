package com.kalnee.trivor.sdk.insights.generators.sentences;

import com.kalnee.trivor.sdk.insights.generators.InsightGenerator;
import com.kalnee.trivor.sdk.models.Insight;
import com.kalnee.trivor.sdk.models.Subtitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.kalnee.trivor.sdk.models.InsightsEnum.WH_SENTENCES;
import static com.kalnee.trivor.sdk.models.TagsEnum.*;

public class WhWordsSentencesGenerator extends SentencesExampleGenerator implements InsightGenerator<Map<String, Set<String>>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhWordsSentencesGenerator.class);

    @Override
    List<String> getTags() {
        return Arrays.asList(
                WDT.name(), WP.name(), WP$.name(), WRB.name()
        );
    }

    @Override
    public String getDescription() {
        return WH_SENTENCES.getDescription();
    }

    @Override
    public String getCode() {
        return WH_SENTENCES.getCode();
    }

    @Override
    public Insight<Map<String, Set<String>>> getInsight(Subtitle subtitle) {
        final Map<String, Set<String>> sentences = getSentences(subtitle);
        LOGGER.info("{} - {}", getCode(), sentences);

        return new Insight<>(getCode(), sentences);
    }
}
