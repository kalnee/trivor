package com.kalnee.trivor.sdk.insights.generators;


import com.kalnee.trivor.sdk.models.Insight;
import com.kalnee.trivor.sdk.models.Subtitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.function.Function;

import static com.kalnee.trivor.sdk.models.InsightsEnum.SENTIMENT_ANALYSIS;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class SentimentGenerator implements InsightGenerator<Map<String, Long>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SentimentGenerator.class);

	@Override
	public String getDescription() {
		return SENTIMENT_ANALYSIS.getDescription();
	}

	@Override
	public String getCode() {
		return SENTIMENT_ANALYSIS.getCode();
	}

	public Insight<Map<String, Long>> getInsight(Subtitle subtitle) {
		final Map<String, Long> sentiments = subtitle.getSentences().stream()
				.filter(s -> s.getSentiment() != null)
				.map(s -> s.getSentiment().name())
				.collect(groupingBy(Function.identity(), counting()));

		LOGGER.info("{}: {}", getCode(), sentiments);
		return new Insight<>(getCode(), sentiments);
	}
}
