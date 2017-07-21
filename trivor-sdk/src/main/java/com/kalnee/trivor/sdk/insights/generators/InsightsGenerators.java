package com.kalnee.trivor.sdk.insights.generators;


import java.util.Arrays;
import java.util.List;


public class InsightsGenerators {

    public static final List<InsightGenerator> DEFAULT_GENERATORS = Arrays.asList(
            new NumberOfSentencesGenerator(),
            new RateOfSpeechGenerator(),
            new AdjectivesFrequencyGenerator(),
            new ComparativesFrequencyGenerator(),
            new NounsFrequencyGenerator(),
            new FrequentSentencesGenerator(),
            new SuperlativesFrequencyGenerator(),
            new VerbsFrequencyGenerator(),
            new ModalsFrequencyGenerator(),
            new AdverbsFrequencyGenerator(),
            new WhWordsFrequencyGenerator(),
            new SimplePresentGenerator(),
            new SimplePastGenerator(),
            new SimpleFutureGenerator(),
            new PresentProgressiveGenerator(),
            new PastProgressiveGenerator(),
            new FutureProgressiveGenerator(),
            new PresentPerfectGenerator(),
            new PastPerfectGenerator(),
            new FuturePerfectGenerator(),
            new NonSentencesGenerator()

    );

    public static final List<PostInsightGenerator> DEFAULT_POST_GENERATORS = Arrays.asList(
            new MixedTensesPostInsightGenerator()
    );
}
