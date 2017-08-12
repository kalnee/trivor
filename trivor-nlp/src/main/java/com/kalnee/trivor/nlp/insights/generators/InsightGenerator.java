package com.kalnee.trivor.nlp.insights.generators;

import com.kalnee.trivor.nlp.nlp.models.Insight;
import com.kalnee.trivor.nlp.nlp.models.Subtitle;

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
