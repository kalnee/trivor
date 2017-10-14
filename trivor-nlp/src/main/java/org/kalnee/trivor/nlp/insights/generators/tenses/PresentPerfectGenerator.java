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

package org.kalnee.trivor.nlp.insights.generators.tenses;

import org.kalnee.trivor.nlp.domain.InsightsEnum;
import org.kalnee.trivor.nlp.domain.Sentence;
import org.kalnee.trivor.nlp.domain.Subtitle;
import org.kalnee.trivor.nlp.domain.TagsEnum;
import org.kalnee.trivor.nlp.insights.generators.Generator;
import org.kalnee.trivor.nlp.utils.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

/**
 * Present perfect verb tense insight generator.
 *
 * @see Generator
 *
 * @since 0.0.1
 */
public class PresentPerfectGenerator implements Generator<List<String>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(PresentPerfectGenerator.class);

	private static final List<String> MUST_CONTAIN = Arrays.asList(
		"Have", "Has", "Haven't", "Hasn't", "haven't", "hasn't", "have", "has", "'ve"
	);
	private static final List<String> MUST_CONTAIN_TAGS = Arrays.asList(TagsEnum.PRP.name(), TagsEnum.NNP.name(), TagsEnum.NNPS.name());
	private static final List<String> MUST_CONTAIN_NON_3RD = Arrays.asList(TagsEnum.VBP.name(), TagsEnum.VBN.name());
	private static final List<String> MUST_CONTAIN_3RD = Arrays.asList(TagsEnum.VBZ.name(), TagsEnum.VBN.name());
	private static final List<String> MUST_NOT_CONTAIN = Arrays.asList(
		TagsEnum.VBG.name(), TagsEnum.VBD.name()
	);
  private static final List<String> MUST_NOT_CONTAIN_MODAL = Arrays.asList(
  	"will", "Will", "won't", "Won't", "'ll", "gonna"
	);

	@Override
	public String getCode() {
		return InsightsEnum.PRESENT_PERFECT.getCode();
	}

	public List<String> generate(Subtitle subtitle) {
		final List<String> sentences = subtitle.getSentences()
			.stream()
			.filter(s -> CollectionUtils.anyMatch(s.getSentence(), MUST_CONTAIN)
				&& CollectionUtils.anyMatch(s.getSentenceTags(), MUST_CONTAIN_TAGS)
				&& (CollectionUtils.allMatch(s.getSentenceTags(), MUST_CONTAIN_NON_3RD) || CollectionUtils.allMatch(s.getSentenceTags(), MUST_CONTAIN_3RD))
				&& CollectionUtils.noneMatch(s.getSentenceTags(), MUST_NOT_CONTAIN)
				&& CollectionUtils.noneMatch(s.getSentence(), MUST_NOT_CONTAIN_MODAL))
			.map(Sentence::getSentence)
			.collect(toList());

		LOGGER.info(
			format("%s: %d/%d (%.2f%%)", getCode(), sentences.size(), subtitle.getSentences().size(),
			(sentences.size() * 100d / subtitle.getSentences().size()))
		);

		return sentences;
	}
}
