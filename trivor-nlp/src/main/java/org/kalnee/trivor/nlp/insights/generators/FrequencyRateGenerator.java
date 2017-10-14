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
import org.kalnee.trivor.nlp.domain.FrequencyRate;
import org.kalnee.trivor.nlp.domain.Subtitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.kalnee.trivor.nlp.domain.FrequencyEnum.*;
import static org.kalnee.trivor.nlp.domain.InsightsEnum.FREQUENCY_RATE;
import static org.kalnee.trivor.nlp.domain.TagsEnum.*;

/**
 * Checks if a token/lemma is present the most frequently used words found in
 * http://www.talkenglish.com/vocabulary/top-2000-vocabulary.aspx
 *
 */
public class FrequencyRateGenerator implements Generator<List<FrequencyRate>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FrequencyRateGenerator.class);

    @Override
    public String getCode() {
        return FREQUENCY_RATE.getCode();
    }

    @Override
    public boolean shouldRun(Subtitle subtitle) {
        return subtitle.getDuration() != null;
    }

    @Override
    public List<FrequencyRate> generate(Subtitle subtitle) {
        try {
            final InputStream highFrequencyWordsStream = getClass().getClassLoader()
                    .getResourceAsStream("language/en-high-frequency.json");
            final List<String> words = new ObjectMapper().readValue(
                    highFrequencyWordsStream, new TypeReference<List<String>>(){}
            );

            final FrequencyRate low = new FrequencyRate(LOW);
            final FrequencyRate middle = new FrequencyRate(MIDDLE);
            final FrequencyRate high = new FrequencyRate(HIGH);

            subtitle.getSentences().stream().flatMap(s -> s.getTokens().stream())
                    .filter(t -> !asList(NNP.name(), NNPS.name(), CD.name(), SYM.name()).contains(t.getTag()))
                    .filter(t -> t.getProb() > 0.6)
                    .map(t -> t.getLemma().toLowerCase())
                    .filter(l -> l.matches("[^\\d\\W]+"))
                    .forEach(lemma -> {
                        final int index = words.indexOf(lemma);
                        if (index == -1 && lemma.length() > 1) {
                            low.getWords().add(lemma);
                        } else if (index < 1001) {
                            high.getWords().add(lemma);
                        } else {
                           middle.getWords().add(lemma);
                        }
                    });
            final List<FrequencyRate> rates = Arrays.asList(low, middle, high);

            LOGGER.info("{}: {}", getCode(), rates);
            return rates;
        } catch (IOException e) {
           LOGGER.error("an error occurred while reading the high frequency json file", e);
           return emptyList();
        }
    }
}
