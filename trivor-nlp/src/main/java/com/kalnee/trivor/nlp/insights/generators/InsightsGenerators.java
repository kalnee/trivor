package com.kalnee.trivor.nlp.insights.generators;


import com.kalnee.trivor.nlp.insights.generators.post.MixedTensesPostInsightGenerator;
import com.kalnee.trivor.nlp.insights.generators.post.PostInsightGenerator;
import com.kalnee.trivor.nlp.insights.generators.post.frequency.*;
import com.kalnee.trivor.nlp.insights.generators.sentences.*;
import com.kalnee.trivor.nlp.insights.generators.tenses.*;

import java.util.Arrays;
import java.util.List;


public class InsightsGenerators {

    public static final List<InsightGenerator> DEFAULT_GENERATORS = Arrays.asList(
            new NumberOfSentencesGenerator(),
            new RateOfSpeechGenerator(),
            new AdjectivesSentencesGenerator(),
            new ComparativesSentencesGenerator(),
            new NounsSentencesGenerator(),
            new FrequentSentencesGenerator(),
            new SuperlativesSentencesGenerator(),
            new VerbsSentencesGenerator(),
            new ModalsSentencesGenerator(),
            new AdverbsSentencesGenerator(),
            new WhWordsSentencesGenerator(),
            new PrepositionSentencesGenerator(),
            new SimplePresentGenerator(),
            new SimplePastGenerator(),
            new SimpleFutureGenerator(),
            new PresentProgressiveGenerator(),
            new PastProgressiveGenerator(),
            new FutureProgressiveGenerator(),
            new PresentPerfectGenerator(),
            new PastPerfectGenerator(),
            new FuturePerfectGenerator(),
            new NonSentencesGenerator(),
            new SentimentGenerator()
    );

    public static final List<PostInsightGenerator> DEFAULT_POST_GENERATORS = Arrays.asList(
            new MixedTensesPostInsightGenerator(),
            new AdjectivesFrequencyGenerator(),
            new AdverbsFrequencyGenerator(),
            new ComparativesFrequencyGenerator(),
            new ModalsFrequencyGenerator(),
            new NounsFrequencyGenerator(),
            new SuperlativesFrequencyGenerator(),
            new VerbsFrequencyGenerator(),
            new WhWordsFrequencyGenerator(),
            new PrepositionsFrequencyGenerator()
    );
}
