package com.kalnee.trivor.engine.insights.generators;

import java.util.List;

import com.kalnee.trivor.engine.models.Insight;
import com.kalnee.trivor.engine.models.Subtitle;

public interface PostInsightGenerator<T> {
	String getDescription();

	String getCode();

  Insight<T> getInsight(Subtitle subtitle, List<Insight> insights);
}
