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

package org.kalnee.trivor.nlp.insights.generators;

import org.kalnee.trivor.nlp.domain.Sentence;
import org.kalnee.trivor.nlp.domain.SentenceFrequency;
import org.kalnee.trivor.nlp.domain.Subtitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.*;
import static org.kalnee.trivor.nlp.domain.InsightsEnum.FREQUENT_SENTENCES;

public class FrequentSentencesGenerator implements Generator<List<SentenceFrequency>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FrequentSentencesGenerator.class);

    @Override
    public String getCode() {
        return FREQUENT_SENTENCES.getCode();
    }

    @Override
    public List<SentenceFrequency> generate(Subtitle subtitle) {
        final Map<String, Long> words = subtitle.getSentences().parallelStream()
                .filter(s -> s.getTokens().size() > 3)
                .map(Sentence::getSentence)
                .map(s -> s.replaceAll("\\.", ""))
                .collect(groupingBy(Function.identity(), counting()));

        final List<SentenceFrequency> sortedSentences = words.entrySet().parallelStream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .filter(e -> e.getValue() > 1)
                .map(e -> new SentenceFrequency(e.getKey(), e.getValue()))
                .collect(toList());

        LOGGER.info("{} - {}", getCode(), sortedSentences.stream().limit(2).collect(toList()));
        return sortedSentences;
    }
}
