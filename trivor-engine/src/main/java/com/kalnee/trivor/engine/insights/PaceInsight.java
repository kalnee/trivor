package com.kalnee.trivor.engine.insights;

import java.util.List;

import com.kalnee.trivor.engine.models.Sentence;

public class PaceInsight implements Insight<String> {

	private final List<Sentence> sentences;
  private String value;
	private Integer duration;

	public PaceInsight(List<Sentence> sentences, Integer duration) {
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

		if (frequency <= 8) {
			value = "SLOW";
		} else if (frequency > 8 && frequency <= 15) {
			value = "MODERATE";
		} else if (frequency > 15 && frequency <= 20) {
			value = "FAST";
		} else {
			value = "SUPER_FAST";
		}
	}

	@Override
	public String getValue() {
		return value;
	}
}
