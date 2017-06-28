package com.kalnee.trivor.sdk.insights.processors;

import com.kalnee.trivor.sdk.insights.generators.InsightsGenerators;
import com.kalnee.trivor.sdk.models.Insight;
import com.kalnee.trivor.sdk.models.Subtitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static com.kalnee.trivor.sdk.models.TypeEnum.TV_SHOW;
import static java.util.stream.Collectors.toMap;

public class InsightsProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(InsightsProcessor.class);

    public InsightsProcessor() {
    }

    public Map<String, Object> process(Subtitle subtitle) {
        LOGGER.info("############# GENERATING INSIGHTS ###########");
        LOGGER.info("{}", subtitle.getName());
        if (TV_SHOW.equals(subtitle.getType())) {
            LOGGER.info("S{}E{}", subtitle.getSeason(), subtitle.getEpisode());
        }
        LOGGER.info("Duration: {}min", subtitle.getDuration());

        final Map<String, Object> insights = InsightsGenerators.GENERATORS
                .stream()
                .map(i -> i.getInsight(subtitle))
                .collect(toMap(Insight::getCode, Insight::getValue));

        final Map<String, Object> postInsights = InsightsGenerators.POST_GENERATORS
                .stream()
                .map(i -> i.getInsight(subtitle, insights))
                .collect(toMap(Insight::getCode, Insight::getValue));

        insights.putAll(postInsights);

        return insights;
    }
}
