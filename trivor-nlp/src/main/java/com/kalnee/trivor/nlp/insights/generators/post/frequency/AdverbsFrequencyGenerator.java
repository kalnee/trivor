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

package com.kalnee.trivor.nlp.insights.generators.post.frequency;

import com.kalnee.trivor.nlp.insights.generators.post.PostInsightGenerator;
import com.kalnee.trivor.nlp.nlp.models.Insight;
import com.kalnee.trivor.nlp.nlp.models.Subtitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static com.kalnee.trivor.nlp.nlp.models.InsightsEnum.ADVERBS_FREQUENCY;
import static com.kalnee.trivor.nlp.nlp.models.InsightsEnum.ADVERBS_SENTENCES;

public class AdverbsFrequencyGenerator extends FrequencyGenerator implements PostInsightGenerator<Map<String, Integer>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdverbsFrequencyGenerator.class);

    @Override
    public String getDescription() {
        return ADVERBS_FREQUENCY.getDescription();
    }

    @Override
    public String getCode() {
        return ADVERBS_FREQUENCY.getCode();
    }

    @Override
    String getInsightCode() {
        return ADVERBS_SENTENCES.getCode();
    }

    @Override
    public Insight<Map<String, Integer>> getInsight(Subtitle subtitle, Map<String, Object> insights) {
        final Map<String, Integer> frequency = getFrequency(insights);
        LOGGER.info("{} - {}", getCode(), frequency);

        return new Insight<>(getCode(), frequency);
    }
}
