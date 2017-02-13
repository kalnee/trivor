package com.kalnee.trivor.engine.insights.generators;

import com.kalnee.trivor.engine.models.Insight;
import com.kalnee.trivor.engine.models.Subtitle;

public interface InsightGenerator<T> {
	String getDescription();

	String getCode();

  Insight<T> getInsight(Subtitle subtitle);
}
