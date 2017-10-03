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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kalnee.trivor.nlp.nlp.models.Insight;
import org.kalnee.trivor.nlp.nlp.models.Subtitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyMap;
import static org.kalnee.trivor.nlp.nlp.models.FrequencyEnum.*;
import static org.kalnee.trivor.nlp.nlp.models.InsightsEnum.FREQUENCY_RATE;
import static org.kalnee.trivor.nlp.nlp.models.TagsEnum.*;

/**
 * Checks if a token/lemma is present the most frequently used words found in
 * http://www.talkenglish.com/vocabulary/top-2000-vocabulary.aspx
 *
 */
public class FrequencyRateGenerator implements InsightGenerator<Map<String, Integer>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FrequencyRateGenerator.class);

    @Override
    public String getDescription() {
        return FREQUENCY_RATE.getDescription();
    }

    @Override
    public String getCode() {
        return FREQUENCY_RATE.getCode();
    }

    @Override
    public boolean shouldRun(Subtitle subtitle) {
        return subtitle.getDuration() != null;
    }

    @Override
    public Insight<Map<String, Integer>> getInsight(Subtitle subtitle) {
        try {
            final InputStream highFrequencyWordsStream = getClass().getClassLoader()
                    .getResourceAsStream("language/en-high-frequency.json");
            final List<String> words = new ObjectMapper().readValue(
                    highFrequencyWordsStream, new TypeReference<List<String>>(){}
            );
            final Map<String, Integer> frequency = new HashMap<>();

            subtitle.getSentences().stream().flatMap(s -> s.getTokens().stream())
                    .filter(t -> !asList(NNP.name(), NNPS.name(), CD.name(), SYM.name()).contains(t.getTag()))
                    .map(t -> t.getLemma().toLowerCase())
                    .filter(l -> l.matches("\\w+"))
                    .forEach(l -> {
                        final int index = words.indexOf(l);
                        if (index == -1) {
                            frequency.put(LOW.name(), frequency.getOrDefault(LOW.name(), 0) + 1);
                        } else if (index < 1001) {
                            frequency.put(HIGH.name(), frequency.getOrDefault(HIGH.name(), 0) + 1);
                        } else {
                            frequency.put(MIDDLE.name(), frequency.getOrDefault(MIDDLE.name(), 0) + 1);
                        }
                    });

            LOGGER.info("{}: {}", getCode(), frequency);

            return new Insight<>(getCode(), frequency);
        } catch (IOException e) {
           LOGGER.error("an error occurred while reading the high frequency json file", e);
           return new Insight<>(getCode(), emptyMap());
        }
    }
}
