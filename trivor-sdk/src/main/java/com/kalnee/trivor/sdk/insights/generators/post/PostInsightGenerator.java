package com.kalnee.trivor.sdk.insights.generators.post;


import com.kalnee.trivor.sdk.models.Insight;
import com.kalnee.trivor.sdk.models.Subtitle;

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
