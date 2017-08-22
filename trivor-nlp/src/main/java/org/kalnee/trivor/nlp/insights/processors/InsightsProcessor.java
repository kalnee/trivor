/* Copyright (c) 2016 Kalnee
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.kalnee.trivor.nlp.insights.processors;

import org.apache.commons.lang3.time.StopWatch;
import org.kalnee.trivor.nlp.insights.generators.InsightGenerator;
import org.kalnee.trivor.nlp.insights.generators.InsightsGenerators;
import org.kalnee.trivor.nlp.insights.generators.post.PostInsightGenerator;
import org.kalnee.trivor.nlp.nlp.models.Insight;
import org.kalnee.trivor.nlp.nlp.models.Subtitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toMap;

class InsightsProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(InsightsProcessor.class);

    InsightsProcessor() {
    }

    Map<String, Object> process(Subtitle subtitle) {
        return process(subtitle, emptyList(), emptyList(), emptyList(), emptyList());
    }

    Map<String, Object> process(Subtitle subtitle,
                                List<InsightGenerator> insightGenerators,
                                List<PostInsightGenerator> postInsightGenerators,
                                List<String> skipInsights,
                                List<String> skipPostInsights) {
        final StopWatch sw = new StopWatch();
        sw.start();

        LOGGER.info("Running Insight Generators");
        final List<InsightGenerator> generators = new ArrayList<>(InsightsGenerators.DEFAULT_GENERATORS);
        generators.addAll(insightGenerators);
        final Map<String, Object> insights = generators
                .parallelStream()
                .filter(i -> !skipInsights.contains(i.getCode()))
                .filter(i -> i.shouldRun(subtitle))
                .map(i -> i.getInsight(subtitle))
                .collect(toMap(Insight::getCode, Insight::getValue));

        LOGGER.info("Running Post Insight Generators");
        final List<PostInsightGenerator> postGenerators = new ArrayList<>(InsightsGenerators.DEFAULT_POST_GENERATORS);
        postGenerators.addAll(postInsightGenerators);
        final Map<String, Object> postInsights = postGenerators
                .parallelStream()
                .filter(i -> !skipPostInsights.contains(i.getCode()))
                .filter(i -> i.shouldRun(subtitle, insights))
                .map(i -> i.getInsight(subtitle, insights))
                .collect(toMap(Insight::getCode, Insight::getValue));

        insights.putAll(postInsights);

        sw.stop();
        LOGGER.info(
                "Insights generated in {}ms, average {}ms/insight",
                new Object[]{sw.getTime(), sw.getTime() / insights.size()}
        );

        return insights;
    }
}
