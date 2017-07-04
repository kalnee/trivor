package com.kalnee.trivor.insights.services;

import com.kalnee.trivor.sdk.insights.generators.InsightGenerator;
import com.kalnee.trivor.sdk.models.Insight;
import com.kalnee.trivor.sdk.models.Subtitle;

public class SecondSentenceGenerator implements InsightGenerator<String> {

    @Override
    public String getDescription() {
        return "Second Sentence Generator";
    }

    @Override
    public String getCode() {
        return "second-sentence-generator";
    }

    @Override
    public Insight<String> getInsight(Subtitle subtitle) {
        return new Insight<>(getCode(), subtitle.getSentences().get(1).getSentence());
    }
}
