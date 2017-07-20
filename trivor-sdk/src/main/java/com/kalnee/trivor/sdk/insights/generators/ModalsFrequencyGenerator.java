package com.kalnee.trivor.sdk.insights.generators;

import com.kalnee.trivor.sdk.models.Insight;
import com.kalnee.trivor.sdk.models.Subtitle;
import com.kalnee.trivor.sdk.models.TagsEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.kalnee.trivor.sdk.models.InsightsEnum.MODALS_FREQUENCY;
import static com.kalnee.trivor.sdk.models.InsightsEnum.VERBS_FREQUENCY;
import static com.kalnee.trivor.sdk.models.TagsEnum.*;

public class ModalsFrequencyGenerator extends FrequencyGenerator implements InsightGenerator<Map<String, Long>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModalsFrequencyGenerator.class);

    @Override
    List<String> getTags() {
        return Collections.singletonList(MD.name());
    }

    @Override
    public String getDescription() {
        return MODALS_FREQUENCY.getDescription();
    }

    @Override
    public String getCode() {
        return MODALS_FREQUENCY.getCode();
    }

    @Override
    public Insight<Map<String, Long>> getInsight(Subtitle subtitle) {
        final Map<String, Long> commonWords = getFrequency(subtitle);
        LOGGER.info("{} - {}", getCode(), commonWords);

        return new Insight<>(getCode(), commonWords);
    }
}
