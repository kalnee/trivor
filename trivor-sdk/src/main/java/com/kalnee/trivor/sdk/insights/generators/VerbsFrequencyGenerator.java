package com.kalnee.trivor.sdk.insights.generators;

import com.kalnee.trivor.sdk.models.Insight;
import com.kalnee.trivor.sdk.models.Subtitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.kalnee.trivor.sdk.models.InsightsEnum.VERBS_FREQUENCY;
import static com.kalnee.trivor.sdk.models.TagsEnum.*;

public class VerbsFrequencyGenerator extends FrequencyGenerator implements InsightGenerator<Map<String, Long>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(VerbsFrequencyGenerator.class);

    @Override
    List<String> getTags() {
        return Arrays.asList(
                VB.name(), VBD.name(), VBG.name(), VBN.name(), VBP.name(), VBZ.name()
        );
    }

    @Override
    public String getDescription() {
        return VERBS_FREQUENCY.getDescription();
    }

    @Override
    public String getCode() {
        return VERBS_FREQUENCY.getCode();
    }

    @Override
    public Insight<Map<String, Long>> getInsight(Subtitle subtitle) {
        final Map<String, Long> commonWords = getFrequency(subtitle);
        LOGGER.info("{} - {}", getCode(), commonWords);

        return new Insight<>(getCode(), commonWords);
    }
}
