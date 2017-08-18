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


import org.kalnee.trivor.nlp.nlp.models.Insight;
import org.kalnee.trivor.nlp.nlp.models.Sentence;
import org.kalnee.trivor.nlp.nlp.models.Subtitle;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.*;
import static org.kalnee.trivor.nlp.nlp.models.InsightsEnum.FREQUENT_SENTENCES;

public class FrequentSentencesGenerator implements InsightGenerator<Map<String, Long>> {

	@Override
	public String getDescription() {
		return FREQUENT_SENTENCES.getDescription();
	}

	@Override
	public String getCode() {
		return FREQUENT_SENTENCES.getCode();
	}

	@Override
	public Insight<Map<String, Long>> getInsight(Subtitle subtitle) {
		final Map<String, Long> words = subtitle.getSentences().parallelStream()
			.filter(s -> s.getTokens().size() > 3)
			.map(Sentence::getSentence)
			.map(s -> s.replaceAll("\\.", ""))
			.collect(groupingBy(Function.identity(), counting()));

		final Map<String, Long> commonSentences = words.entrySet().parallelStream()
			.sorted(Map.Entry.<String, Long> comparingByValue().reversed())
			.filter(e -> e.getValue() > 1)
			.collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v2, LinkedHashMap::new));

    	return new Insight<>(getCode(), commonSentences);
	}
}
