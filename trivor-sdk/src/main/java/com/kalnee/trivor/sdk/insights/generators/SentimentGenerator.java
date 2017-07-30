package com.kalnee.trivor.sdk.insights.generators;


import com.kalnee.trivor.sdk.models.Insight;
import com.kalnee.trivor.sdk.models.SentimentEnum;
import com.kalnee.trivor.sdk.models.Subtitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Map;

import static com.kalnee.trivor.sdk.models.InsightsEnum.SENTIMENT_ANALYSIS;

public class SentimentGenerator implements InsightGenerator<Map<SentimentEnum, BigDecimal>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SentimentGenerator.class);

	@Override
	public String getDescription() {
		return SENTIMENT_ANALYSIS.getDescription();
	}

	@Override
	public String getCode() {
		return SENTIMENT_ANALYSIS.getCode();
	}

	@Override
	public boolean shouldRun(Subtitle subtitle) {
		return subtitle.getSentiment() != null;
	}

	public Insight<Map<SentimentEnum, BigDecimal>> getInsight(Subtitle subtitle) {
		LOGGER.info("{}: {}", getCode(), subtitle.getSentiment());
		return new Insight<>(getCode(), subtitle.getSentiment());
	}
}
