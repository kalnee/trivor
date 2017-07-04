package com.kalnee.trivor.insights.services;

import com.kalnee.trivor.sdk.insights.generators.PostInsightGenerator;
import com.kalnee.trivor.sdk.models.Insight;
import com.kalnee.trivor.sdk.models.Subtitle;

import java.util.Map;

public class LengthPostInsightGenerator implements PostInsightGenerator<Integer> {

    @Override
    public String getDescription() {
        return "First Sentence Generator";
    }

    @Override
    public String getCode() {
        return "length-1st-2nd-sentences";
    }

    @Override
    public Insight<Integer> getInsight(Subtitle subtitle, Map<String, Object> insights) {
        final Integer length1stSentence = insights.get("first-sentence-generator").toString().length();
        final Integer length2ndSentence = insights.get("second-sentence-generator").toString().length();
        return new Insight<>(getCode(), length1stSentence + length2ndSentence);
    }
}
