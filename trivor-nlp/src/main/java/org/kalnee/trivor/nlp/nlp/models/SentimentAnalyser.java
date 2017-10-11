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

package org.kalnee.trivor.nlp.nlp.models;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import org.kalnee.trivor.nlp.domain.SentimentEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class SentimentAnalyser {

    private static final Logger LOGGER = LoggerFactory.getLogger(SentimentAnalyser.class);
    private static final String MODEL = "/nlp/models/en-sentiment.bin";
    private static final String NOT_IMPORTANT = ",|\\.{1,3}";

    private DocumentCategorizerME categorizer;

    public SentimentAnalyser() {
        try (InputStream modelStream = SentimentAnalyser.class.getResourceAsStream(MODEL)) {
            DoccatModel tokenizerModel = new DoccatModel(modelStream);
            categorizer = new DocumentCategorizerME(tokenizerModel);
        } catch (IOException e) {
            LOGGER.error("an error occurred while getting categories", e);
            throw new IllegalStateException(e);
        }
    }

    public Map<SentimentEnum, BigDecimal> categorize(List<String> sentences) {
        final List<String> tokens = sentences.stream()
                .flatMap(s -> Stream.of(s.split(" ")))
                .filter(t -> !t.matches(NOT_IMPORTANT))
                .map(s -> s.replace("...", ""))
                .collect(toList());

        final Map<String, Double> scoreMap = categorizer.scoreMap(tokens.toArray(new String[tokens.size()]));
        return scoreMap.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .collect(toMap(e -> SentimentEnum.valueOf(e.getKey()), e -> BigDecimal.valueOf(e.getValue())));
    }
}
