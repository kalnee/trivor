package com.kalnee.trivor.engine.insights.generators;

import java.util.List;

import com.kalnee.trivor.engine.models.Insight;
import com.kalnee.trivor.engine.models.Sentence;
import com.kalnee.trivor.engine.models.Subtitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumberOfSentencesGenerator implements InsightGenerator<Integer> {

	private static final Logger LOGGER = LoggerFactory.getLogger(NumberOfSentencesGenerator.class);

	@Override
	public String getDescription() {
		return "Number of Sentences";
	}

	@Override
	public String getCode() {
		return "number-of-sentences";
	}

	public Insight<Integer> getInsight(Subtitle subtitle) {
		LOGGER.info("{}: {}", getCode(), subtitle.getSentences().size());
		return new Insight<>(getCode(), subtitle.getSentences().size());
	}
}
