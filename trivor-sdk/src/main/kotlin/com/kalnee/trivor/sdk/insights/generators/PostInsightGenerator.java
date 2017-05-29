package com.kalnee.trivor.sdk.insights.generators;


import com.kalnee.trivor.sdk.models.Insight;
import com.kalnee.trivor.sdk.models.Subtitle;

import java.util.List;

public interface PostInsightGenerator<T> {
    String getDescription();

    String getCode();

    Insight<T> getInsight(Subtitle subtitle, List<Insight> insights);
}
