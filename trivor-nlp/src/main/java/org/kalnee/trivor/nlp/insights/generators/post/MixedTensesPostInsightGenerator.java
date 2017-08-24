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

package org.kalnee.trivor.nlp.insights.generators.post;

import org.kalnee.trivor.nlp.nlp.models.Insight;
import org.kalnee.trivor.nlp.nlp.models.Sentence;
import org.kalnee.trivor.nlp.nlp.models.Subtitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.kalnee.trivor.nlp.nlp.models.InsightsEnum.*;

/**
 * Generator for sentences that have more than one verb tense.
 *
 * @see PostInsightGenerator
 *
 * @since 0.0.1
 */
public class MixedTensesPostInsightGenerator implements PostInsightGenerator<List<String>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(MixedTensesPostInsightGenerator.class);

	private static final List<String> INSIGHTS = Arrays.asList(
		SIMPLE_PRESENT.getCode(), SIMPLE_PAST.getCode(), SIMPLE_FUTURE.getCode(), PRESENT_PROGRESSIVE.getCode(),
		PAST_PROGRESSIVE.getCode(), FUTURE_PROGRESSIVE.getCode(), PRESENT_PERFECT.getCode(), PAST_PERFECT.getCode(),
		NON_SENTENCES.getCode()
	);

	@Override
	public String getDescription() {
		return "Mixed Tenses";
	}

	@Override
	public String getCode() {
		return "mixed-tenses";
	}

	@SuppressWarnings("unchecked")
	public Insight<List<String>> getInsight(Subtitle subtitle, Map<String, Object> insights) {

		final List<String> all = insights.entrySet().stream()
			.filter(i -> INSIGHTS.contains(i.getKey()))
			.flatMap(i -> ((List<String>) i.getValue()).stream())
			.collect(toList());

		final List<String> mixed = subtitle.getSentences().stream()
			.filter(s -> !all.contains(s.getSentence()))
			.map(Sentence::getSentence)
			.collect(toList());

		LOGGER.info(
			format("%s: %d/%d (%.2f%%)", getCode(), mixed.size(), subtitle.getSentences().size(),
				(mixed.size() * 100d / subtitle.getSentences().size()))
		);

		LOGGER.info("total: {}/{}", all.size() + mixed.size(), subtitle.getSentences().size());

		return new Insight<>(getCode(), mixed);
	}
}
