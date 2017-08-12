package com.kalnee.trivor.nlp.insights.generators.post.frequency;

import com.kalnee.trivor.nlp.insights.generators.post.PostInsightGenerator;
import com.kalnee.trivor.nlp.nlp.models.Insight;
import com.kalnee.trivor.nlp.nlp.models.Subtitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static com.kalnee.trivor.nlp.nlp.models.InsightsEnum.PREPOSITIONS_FREQUENCY;
import static com.kalnee.trivor.nlp.nlp.models.InsightsEnum.PREPOSITIONS_SENTENCES;

public class PrepositionsFrequencyGenerator extends FrequencyGenerator implements PostInsightGenerator<Map<String, Integer>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrepositionsFrequencyGenerator.class);

    @Override
    public String getDescription() {
        return PREPOSITIONS_FREQUENCY.getDescription();
    }

    @Override
    public String getCode() {
        return PREPOSITIONS_FREQUENCY.getCode();
    }

    @Override
    String getInsightCode() {
        return PREPOSITIONS_SENTENCES.getCode();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Insight<Map<String, Integer>> getInsight(Subtitle subtitle, Map<String, Object> insights) {
        final Map<String, Integer> frequency = getFrequency(insights);
        LOGGER.info("{} - {}", getCode(), frequency);

        return new Insight<>(getCode(), frequency);
    }
}
