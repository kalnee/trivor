package com.kalnee.trivor.sdk.insights.generators.post.frequency;

import com.kalnee.trivor.sdk.insights.generators.post.PostInsightGenerator;
import com.kalnee.trivor.sdk.models.Insight;
import com.kalnee.trivor.sdk.models.Subtitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static com.kalnee.trivor.sdk.models.InsightsEnum.ADVERBS_FREQUENCY;
import static com.kalnee.trivor.sdk.models.InsightsEnum.ADVERBS_SENTENCES;

public class AdverbsFrequencyGenerator extends FrequencyGenerator implements PostInsightGenerator<Map<String, Integer>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdverbsFrequencyGenerator.class);

    @Override
    public String getDescription() {
        return ADVERBS_FREQUENCY.getDescription();
    }

    @Override
    public String getCode() {
        return ADVERBS_FREQUENCY.getCode();
    }

    @Override
    String getInsightCode() {
        return ADVERBS_SENTENCES.getCode();
    }

    @Override
    public Insight<Map<String, Integer>> getInsight(Subtitle subtitle, Map<String, Object> insights) {
        final Map<String, Integer> frequency = getFrequency(insights);
        LOGGER.info("{} - {}", getCode(), frequency);

        return new Insight<>(getCode(), frequency);
    }
}
