package com.kalnee.trivor.sdk.insights.generators;


import java.util.Arrays;
import java.util.List;


public class InsightsGenerators {

    public static final List<InsightGenerator> GENERATORS = Arrays.asList(
            new NumberOfSentencesGenerator(),
            new FrequentAdjectivesGenerator(),
            new FrequentComparativesGenerator(),
            new FrequentNounsGenerator(),
            new FrequentSentencesGenerator(),
            new FrequentSuperlativesGenerator()
    );

    public static final List<PostInsightGenerator> POST_GENERATORS = Arrays.asList(

    );
}
