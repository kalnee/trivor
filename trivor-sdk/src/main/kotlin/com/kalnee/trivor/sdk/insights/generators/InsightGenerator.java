package com.kalnee.trivor.sdk.insights.generators;

import com.kalnee.trivor.sdk.models.Insight;
import com.kalnee.trivor.sdk.models.Subtitle;

public interface InsightGenerator<T> {
    String getDescription();

    String getCode();

    Insight<T> getInsight(Subtitle subtitle);
}
