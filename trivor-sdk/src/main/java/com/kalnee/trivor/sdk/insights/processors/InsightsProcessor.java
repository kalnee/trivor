package com.kalnee.trivor.sdk.insights.processors;

import com.kalnee.trivor.sdk.insights.generators.InsightGenerator;
import com.kalnee.trivor.sdk.insights.generators.post.PostInsightGenerator;
import com.kalnee.trivor.sdk.models.Insight;
import com.kalnee.trivor.sdk.models.Subtitle;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.kalnee.trivor.sdk.insights.generators.InsightsGenerators.DEFAULT_GENERATORS;
import static com.kalnee.trivor.sdk.insights.generators.InsightsGenerators.DEFAULT_POST_GENERATORS;
import static com.kalnee.trivor.sdk.models.TypeEnum.TV_SHOW;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toMap;

class InsightsProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(InsightsProcessor.class);

    InsightsProcessor() {
    }

    Map<String, Object> process(Subtitle subtitle) {
        return process(subtitle, emptyList(), emptyList());
    }

    Map<String, Object> process(Subtitle subtitle, List<InsightGenerator> insightGenerators,
                                List<PostInsightGenerator> postInsightGenerators) {
        LOGGER.info("############# GENERATING INSIGHTS ###########");
        LOGGER.info("{}", subtitle.getName());
        if (TV_SHOW.equals(subtitle.getType())) {
            LOGGER.info("S{}E{}", subtitle.getSeason(), subtitle.getEpisode());
        }
        LOGGER.info("Duration: {}min", subtitle.getDuration());

        final StopWatch sw = new StopWatch();
        sw.start();

        LOGGER.info("Running Insight Generators");
        final List<InsightGenerator> generators = new ArrayList<>(DEFAULT_GENERATORS);
        generators.addAll(insightGenerators);
        final Map<String, Object> insights = generators
                .parallelStream()
                .filter(i -> i.shouldRun(subtitle))
                .map(i -> i.getInsight(subtitle))
                .collect(toMap(Insight::getCode, Insight::getValue));

        LOGGER.info("Running Post Insight Generators");
        final List<PostInsightGenerator> postGenerators = new ArrayList<>(DEFAULT_POST_GENERATORS);
        postGenerators.addAll(postInsightGenerators);
        final Map<String, Object> postInsights = postGenerators
                .parallelStream()
                .filter(i -> i.shouldRun(subtitle, insights))
                .map(i -> i.getInsight(subtitle, insights))
                .collect(toMap(Insight::getCode, Insight::getValue));

        insights.putAll(postInsights);

        sw.stop();
        LOGGER.info(
                "Total time {}ms, average {}ms/insight",
                new Object[]{sw.getTime(), sw.getTime() / insights.size()}
        );

        return insights;
    }
}
