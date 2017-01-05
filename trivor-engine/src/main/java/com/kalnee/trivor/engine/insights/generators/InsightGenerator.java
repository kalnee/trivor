package com.kalnee.trivor.engine.insights.generators;

import com.kalnee.trivor.engine.models.Insight;

public interface InsightGenerator<T> {
	String getDescription();

	String getCode();

  Insight<T> getInsight();

  void generate();
}
