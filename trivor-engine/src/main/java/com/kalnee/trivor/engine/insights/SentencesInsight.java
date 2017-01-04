package com.kalnee.trivor.engine.insights;

import java.util.List;

import com.kalnee.trivor.engine.models.Sentence;

public class SentencesInsight implements Insight<Integer> {

	private final List<Sentence> sentences;
  private Integer value;

	public SentencesInsight(List<Sentence> sentences) {
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
		value = sentences.size();
	}

	@Override
	public Integer getValue() {
		return value;
	}
}
