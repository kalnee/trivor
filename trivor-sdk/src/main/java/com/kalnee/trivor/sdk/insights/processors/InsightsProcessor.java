package com.kalnee.trivor.sdk.insights.processors;

import com.kalnee.trivor.sdk.insights.generators.InsightGenerator;
import com.kalnee.trivor.sdk.models.Insight;
import com.kalnee.trivor.sdk.models.Subtitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.kalnee.trivor.sdk.insights.generators.InsightsGenerators.DEFAULT_GENERATORS;
import static com.kalnee.trivor.sdk.insights.generators.InsightsGenerators.DEFAULT_POST_GENERATORS;
import static com.kalnee.trivor.sdk.models.TypeEnum.TV_SHOW;
import static java.util.stream.Collectors.toMap;

class InsightsProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(InsightsProcessor.class);

    InsightsProcessor() {
    }

    Map<String, Object> process(Subtitle subtitle) {
        return process(subtitle, Collections.emptyList());
    }

    Map<String, Object> process(Subtitle subtitle, List<InsightGenerator> insightGenerators) {
        LOGGER.info("############# GENERATING INSIGHTS ###########");
        LOGGER.info("{}", subtitle.getName());
        if (TV_SHOW.equals(subtitle.getType())) {
            LOGGER.info("S{}E{}", subtitle.getSeason(), subtitle.getEpisode());
        }
        LOGGER.info("Duration: {}min", subtitle.getDuration());

        final List<InsightGenerator> generators = new ArrayList<>(DEFAULT_GENERATORS);
        generators.addAll(insightGenerators);
        final Map<String, Object> insights = generators
                .stream()
                .map(i -> i.getInsight(subtitle))
                .collect(toMap(Insight::getCode, Insight::getValue));

        final Map<String, Object> postInsights = DEFAULT_POST_GENERATORS
                .stream()
                .map(i -> i.getInsight(subtitle, insights))
                .collect(toMap(Insight::getCode, Insight::getValue));

        insights.putAll(postInsights);

        return insights;
    }
}
