package com.kalnee.trivor.sdk.insights.generators.post.frequency;

import com.kalnee.trivor.sdk.insights.generators.post.PostInsightGenerator;
import com.kalnee.trivor.sdk.models.Insight;
import com.kalnee.trivor.sdk.models.Subtitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static com.kalnee.trivor.sdk.models.InsightsEnum.MODALS_FREQUENCY;
import static com.kalnee.trivor.sdk.models.InsightsEnum.MODALS_SENTENCES;

public class ModalsFrequencyGenerator extends FrequencyGenerator implements PostInsightGenerator<Map<String, Integer>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModalsFrequencyGenerator.class);

    @Override
    public String getDescription() {
        return MODALS_FREQUENCY.getDescription();
    }

    @Override
    public String getCode() {
        return MODALS_FREQUENCY.getCode();
    }

    @Override
    String getInsightCode() {
        return MODALS_SENTENCES.getCode();
    }

    @Override
    public Insight<Map<String, Integer>> getInsight(Subtitle subtitle, Map<String, Object> insights) {
        final Map<String, Integer> frequency = getFrequency(insights);
        LOGGER.info("{} - {}", getCode(), frequency);

        return new Insight<>(getCode(), frequency);
    }
}
