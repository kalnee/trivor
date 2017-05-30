package com.kalnee.trivor.sdk.insights.processors;

import com.kalnee.trivor.sdk.insights.generators.InsightsGenerators;
import com.kalnee.trivor.sdk.models.Insight;
import com.kalnee.trivor.sdk.models.Subtitle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.kalnee.trivor.sdk.models.TypeEnum.TV_SHOW;
import static java.util.stream.Collectors.toList;

public class InsightsProcessor {

    private static final Logger LOGGER = LogManager.getLogger(InsightsProcessor.class);

    public InsightsProcessor() {
    }

    public List<Insight> process(Subtitle subtitle) {
        LOGGER.info("############# GENERATING INSIGHTS ###########");
        LOGGER.info("{}", subtitle.getName());
        if (TV_SHOW.equals(subtitle.getType())) {
            LOGGER.info("S{}E{}", subtitle.getSeason(), subtitle.getEpisode());
        }
        LOGGER.info("Duration: {}min", subtitle.getDuration());

        final List<Insight> insights = InsightsGenerators.GENERATORS
                .stream()
                .map(i -> i.getInsight(subtitle))
                .collect(toList());

        final List<Insight> postInsights = InsightsGenerators.POST_GENERATORS
                .stream()
                .map(i -> i.getInsight(subtitle, insights))
                .collect(toList());

        insights.addAll(postInsights);

        LOGGER.info("Insights created successfully.");
        return insights;
    }
}
