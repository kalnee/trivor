package com.kalnee.trivor.nlp.insights.generators.post.frequency;

import com.kalnee.trivor.nlp.insights.generators.post.PostInsightGenerator;
import com.kalnee.trivor.nlp.nlp.models.Insight;
import com.kalnee.trivor.nlp.nlp.models.Subtitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static com.kalnee.trivor.nlp.nlp.models.InsightsEnum.SUPERLATIVES_FREQUENCY;
import static com.kalnee.trivor.nlp.nlp.models.InsightsEnum.SUPERLATIVES_SENTENCES;

public class SuperlativesFrequencyGenerator extends FrequencyGenerator implements PostInsightGenerator<Map<String, Integer>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SuperlativesFrequencyGenerator.class);

    @Override
    public String getDescription() {
        return SUPERLATIVES_FREQUENCY.getDescription();
    }

    @Override
    public String getCode() {
        return SUPERLATIVES_FREQUENCY.getCode();
    }

    @Override
    String getInsightCode() {
        return SUPERLATIVES_SENTENCES.getCode();
    }

    @Override
    public Insight<Map<String, Integer>> getInsight(Subtitle subtitle, Map<String, Object> insights) {
        final Map<String, Integer> frequency = getFrequency(insights);
        LOGGER.info("{} - {}", getCode(), frequency);

        return new Insight<>(getCode(), frequency);
    }
}
