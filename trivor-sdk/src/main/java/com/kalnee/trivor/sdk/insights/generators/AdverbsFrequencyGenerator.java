package com.kalnee.trivor.sdk.insights.generators;

import com.kalnee.trivor.sdk.models.Insight;
import com.kalnee.trivor.sdk.models.Subtitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.kalnee.trivor.sdk.models.InsightsEnum.ADVERBS_FREQUENCY;
import static com.kalnee.trivor.sdk.models.TagsEnum.RB;

public class AdverbsFrequencyGenerator extends FrequencyGenerator implements InsightGenerator<Map<String, Long>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdverbsFrequencyGenerator.class);

    @Override
    List<String> getTags() {
        return Collections.singletonList(RB.name());
    }

    @Override
    public String getDescription() {
        return ADVERBS_FREQUENCY.getDescription();
    }

    @Override
    public String getCode() {
        return ADVERBS_FREQUENCY.getCode();
    }

    @Override
    public Insight<Map<String, Long>> getInsight(Subtitle subtitle) {
        final Map<String, Long> commonWords = getFrequency(subtitle);
        LOGGER.info("{} - {}", getCode(), commonWords);

        return new Insight<>(getCode(), commonWords);
    }
}
