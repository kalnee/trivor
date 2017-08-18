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

package org.kalnee.trivor.nlp.insights.generators.sentences;

import org.kalnee.trivor.nlp.insights.generators.InsightGenerator;
import org.kalnee.trivor.nlp.nlp.models.Insight;
import org.kalnee.trivor.nlp.nlp.models.InsightsEnum;
import org.kalnee.trivor.nlp.nlp.models.Subtitle;
import org.kalnee.trivor.nlp.nlp.models.TagsEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PrepositionSentencesGenerator extends SentencesExampleGenerator implements InsightGenerator<Map<String, Set<String>>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(PrepositionSentencesGenerator.class);

	@Override
	List<String> getTags() {
		return Collections.singletonList(TagsEnum.IN.name());
	}

	@Override
	public String getDescription() {
		return InsightsEnum.PREPOSITIONS_SENTENCES.getDescription();
	}

	@Override
	public String getCode() {
		return InsightsEnum.PREPOSITIONS_SENTENCES.getCode();
	}

	@Override
	public Insight<Map<String, Set<String>>> getInsight(Subtitle subtitle) {
		final Map<String, Set<String>> sentences = getSentences(subtitle);
		LOGGER.info("{} - {}", getCode(), sentences);

    	return new Insight<>(getCode(), sentences);
	}
}
