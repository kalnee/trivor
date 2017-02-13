package com.kalnee.trivor.engine.insights.generators;

import java.util.List;

import com.kalnee.trivor.engine.models.Insight;
import com.kalnee.trivor.engine.models.Sentence;
import com.kalnee.trivor.engine.models.Subtitle;

public class SentencesInsightGenerator implements InsightGenerator<Integer> {

	@Override
	public String getDescription() {
		return "Number of Sentences";
	}

	@Override
	public String getCode() {
		return "NOS";
	}

	public Insight<Integer> getInsight(Subtitle subtitle) {
		return new Insight<>(getCode(), subtitle.getSentences().size());
	}
}
