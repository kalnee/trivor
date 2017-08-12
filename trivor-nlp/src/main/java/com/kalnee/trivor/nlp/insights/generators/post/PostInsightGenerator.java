package com.kalnee.trivor.nlp.insights.generators.post;


import com.kalnee.trivor.nlp.nlp.models.Insight;
import com.kalnee.trivor.nlp.nlp.models.Subtitle;

import java.util.Map;

public interface PostInsightGenerator<T> {

    default String getDescription() {
        return getCode();
    }

    String getCode();

    Insight<T> getInsight(Subtitle subtitle, Map<String, Object> insights);

    default boolean shouldRun(Subtitle subtitle, Map<String, Object> insights) {
        return true;
    }
}
