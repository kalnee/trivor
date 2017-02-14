package com.kalnee.trivor.engine.insights.generators;

import java.util.List;

import com.kalnee.trivor.engine.models.Insight;
import com.kalnee.trivor.engine.models.Sentence;
import com.kalnee.trivor.engine.models.Subtitle;

public class NumberOfSentencesGenerator implements InsightGenerator<Integer> {

	@Override
	public String getDescription() {
		return "Number of Sentences";
	}

	@Override
	public String getCode() {
		return "number-of-sentences";
	}

	public Insight<Integer> getInsight(Subtitle subtitle) {
		return new Insight<>(getCode(), subtitle.getSentences().size());
	}
}
