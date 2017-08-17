/* Copyright (c) 2016 Kalnee
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
