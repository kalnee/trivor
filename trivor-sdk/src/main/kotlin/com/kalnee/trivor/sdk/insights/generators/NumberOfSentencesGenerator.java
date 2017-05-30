package com.kalnee.trivor.sdk.insights.generators;


import com.kalnee.trivor.sdk.models.Insight;
import com.kalnee.trivor.sdk.models.Subtitle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.kalnee.trivor.sdk.models.InsightsEnum.NUM_SENTENCES;

public class NumberOfSentencesGenerator implements InsightGenerator<Integer> {

	private static final Logger LOGGER = LogManager.getLogger(NumberOfSentencesGenerator.class);

	@Override
	public String getDescription() {
		return NUM_SENTENCES.getDescription();
	}

	@Override
	public String getCode() {
		return NUM_SENTENCES.getCode();
	}

	public Insight<Integer> getInsight(Subtitle subtitle) {
		LOGGER.info("{}: {}", getCode(), subtitle.getSentences().size());
		return new Insight<>(getCode(), subtitle.getSentences().size());
	}
}
