package com.kalnee.trivor.engine.insights.generators;

import java.util.List;

import com.kalnee.trivor.engine.models.Insight;
import com.kalnee.trivor.engine.models.Sentence;

public class SentencesInsightGenerator implements InsightGenerator<Integer> {

	private final List<Sentence> sentences;
  private Insight<Integer> insight;

	public SentencesInsightGenerator(List<Sentence> sentences) {
		this.sentences = sentences;
	}

	@Override
	public String getDescription() {
		return "Number of Sentences";
	}

	@Override
	public String getCode() {
		return "NOS";
	}

	@Override
	public void generate() {
		insight = new Insight<>(getCode(), sentences.size());
	}

	public Insight<Integer> getInsight() {
		return insight;
	}
}
