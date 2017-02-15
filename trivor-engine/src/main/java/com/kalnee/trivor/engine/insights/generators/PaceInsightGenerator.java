package com.kalnee.trivor.engine.insights.generators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kalnee.trivor.engine.models.Insight;
import com.kalnee.trivor.engine.models.Subtitle;

public class PaceInsightGenerator implements InsightGenerator<String> {

	private static final Logger LOGGER = LoggerFactory.getLogger(PaceInsightGenerator.class);

	@Override
	public String getDescription() {
		return "Speaking Pace";
	}

	@Override
	public String getCode() {
		return "pace";
	}

	public Insight<String> getInsight(Subtitle subtitle) {
		final Integer frequency = subtitle.getSentences().size() / subtitle.getDuration();
		String pace = "SUPER_FAST";

		if (frequency <= 8) {
			pace = "SLOW";
		} else if (frequency > 8 && frequency <= 15) {
			pace = "MODERATE";
		} else if (frequency > 15 && frequency <= 20) {
			pace = "FAST";
		}

		LOGGER.info("{}: {}", getCode(), pace);

		return new Insight<>(getCode(), pace);
	}
}
