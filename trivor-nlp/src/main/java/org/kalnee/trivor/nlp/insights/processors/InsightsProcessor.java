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

package org.kalnee.trivor.nlp.insights.processors;

import org.apache.commons.lang3.time.StopWatch;
import org.kalnee.trivor.nlp.domain.Result;
import org.kalnee.trivor.nlp.domain.Subtitle;
import org.kalnee.trivor.nlp.domain.VerbTenses;
import org.kalnee.trivor.nlp.domain.Vocabulary;
import org.kalnee.trivor.nlp.insights.generators.*;
import org.kalnee.trivor.nlp.insights.generators.tenses.*;
import org.kalnee.trivor.nlp.insights.generators.vocabulary.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class InsightsProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(InsightsProcessor.class);

    InsightsProcessor() {
    }

    Result process(Subtitle subtitle) {
        final StopWatch sw = new StopWatch();
        sw.start();
        LOGGER.info("Running Insight Generators");

        final Result result = new Result();

        result.setNumberOfSentences(new NumberOfSentencesGenerator().generate(subtitle));
        result.setSentimentAnalysis(new SentimentGenerator().generate(subtitle));
        result.setRateOfSpeech(new RateOfSpeechGenerator().generate(subtitle));
        result.setFrequentSentences(new FrequentSentencesGenerator().generate(subtitle));
        result.setFrequentChunks(new FrequentChunksGenerator().generate(subtitle));
        result.setFrequencyRate(new FrequencyRateGenerator().generate(subtitle));

        final Vocabulary vocabulary = new Vocabulary();
        vocabulary.setAdjectives(new AdjectivesGenerator().generate(subtitle));
        vocabulary.setAdverbs(new AdverbsGenerator().generate(subtitle));
        vocabulary.setComparatives(new ComparativesGenerator().generate(subtitle));
        vocabulary.setModals(new ModalsGenerator().generate(subtitle));
        vocabulary.setNouns(new NounsGenerator().generate(subtitle));
        vocabulary.setPrepositions(new PrepositionGenerator().generate(subtitle));
        vocabulary.setSuperlatives(new SuperlativesGenerator().generate(subtitle));
        vocabulary.setVerbs(new VerbsGenerator().generate(subtitle));
        vocabulary.setWhWords(new WhWordsGenerator().generate(subtitle));
        result.setVocabulary(vocabulary);
        result.setPhrasalVerbs(new PhrasalVerbsGenerator(vocabulary.getVerbs()).generate(subtitle));

        final VerbTenses verbTenses = new VerbTenses();
        verbTenses.setSimplePresent(new SimplePresentGenerator().generate(subtitle));
        verbTenses.setSimplePast(new SimplePastGenerator().generate(subtitle));
        verbTenses.setSimpleFuture(new SimpleFutureGenerator().generate(subtitle));
        verbTenses.setPresentProgressive(new PresentProgressiveGenerator().generate(subtitle));
        verbTenses.setPastProgressive(new PastProgressiveGenerator().generate(subtitle));
        verbTenses.setFutureProgressive(new FutureProgressiveGenerator().generate(subtitle));
        verbTenses.setPresentPerfect(new PresentPerfectGenerator().generate(subtitle));
        verbTenses.setPastPerfect(new PastPerfectGenerator().generate(subtitle));
        verbTenses.setFuturePerfect(new FuturePerfectGenerator().generate(subtitle));
        verbTenses.setNonSentences(new NonSentencesGenerator().generate(subtitle));
        verbTenses.setMixedTenses(new MixedTensesGenerator(verbTenses).generate(subtitle));
        result.setVerbTenses(verbTenses);

        sw.stop();
        LOGGER.info("Insights generated in {}ms", new Object[]{sw.getTime()});

        return result;
    }
}
