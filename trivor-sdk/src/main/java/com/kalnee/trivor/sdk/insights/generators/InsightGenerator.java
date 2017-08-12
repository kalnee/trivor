package com.kalnee.trivor.sdk.insights.generators;

import com.kalnee.trivor.sdk.models.Insight;
import com.kalnee.trivor.sdk.models.Subtitle;

public interface InsightGenerator<T> {

    default String getDescription() {
        return getCode();
    }

    String getCode();

    Insight<T> getInsight(Subtitle subtitle);

    default boolean shouldRun(Subtitle subtitle) {
        return true;
    }
}
