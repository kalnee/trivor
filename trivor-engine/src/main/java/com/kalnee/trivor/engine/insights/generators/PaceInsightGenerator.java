package com.kalnee.trivor.engine.insights.generators;

import java.util.List;

import com.kalnee.trivor.engine.models.Insight;
import com.kalnee.trivor.engine.models.Sentence;

public class PaceInsightGenerator implements InsightGenerator<String> {

	private final List<Sentence> sentences;
  private Insight<String> insight;
	private Integer duration;

	public PaceInsightGenerator(List<Sentence> sentences, Integer duration) {
		this.sentences = sentences;
		this.duration = duration;
	}

	@Override
	public String getDescription() {
		return "Speaking Pace";
	}

	@Override
	public String getCode() {
		return "PAC";
	}

	@Override
	public void generate() {
		Integer frequency = sentences.size() / duration;
		String pace = "SUPER_FAST";

		if (frequency <= 8) {
			pace = "SLOW";
		} else if (frequency > 8 && frequency <= 15) {
			pace = "MODERATE";
		} else if (frequency > 15 && frequency <= 20) {
			pace = "FAST";
		}

		insight = new Insight<>(getCode(), pace);
	}

	public Insight<String> getInsight() {
		return insight;
	}
}
