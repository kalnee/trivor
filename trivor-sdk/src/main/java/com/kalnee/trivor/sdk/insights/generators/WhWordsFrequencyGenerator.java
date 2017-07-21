package com.kalnee.trivor.sdk.insights.generators;

import com.kalnee.trivor.sdk.models.Insight;
import com.kalnee.trivor.sdk.models.Subtitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.kalnee.trivor.sdk.models.InsightsEnum.WH_FREQUENCY;
import static com.kalnee.trivor.sdk.models.TagsEnum.*;

public class WhWordsFrequencyGenerator extends FrequencyGenerator implements InsightGenerator<Map<String, Long>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhWordsFrequencyGenerator.class);

    @Override
    List<String> getTags() {
        return Arrays.asList(
                WDT.name(), WP.name(), WP$.name(), WRB.name()
        );
    }

    @Override
    public String getDescription() {
        return WH_FREQUENCY.getDescription();
    }

    @Override
    public String getCode() {
        return WH_FREQUENCY.getCode();
    }

    @Override
    public Insight<Map<String, Long>> getInsight(Subtitle subtitle) {
        final Map<String, Long> commonWords = getFrequency(subtitle);
        LOGGER.info("{} - {}", getCode(), commonWords);

        return new Insight<>(getCode(), commonWords);
    }
}
