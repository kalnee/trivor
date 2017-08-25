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

import org.kalnee.trivor.nlp.insights.generators.InsightGenerator;
import org.kalnee.trivor.nlp.nlp.models.Insight;
import org.kalnee.trivor.nlp.nlp.models.Sentence;
import org.kalnee.trivor.nlp.nlp.models.Subtitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.kalnee.trivor.nlp.nlp.models.InsightsEnum.FUTURE_PERFECT;
import static org.kalnee.trivor.nlp.nlp.models.TagsEnum.*;
import static org.kalnee.trivor.nlp.utils.CollectionUtils.allMatch;
import static org.kalnee.trivor.nlp.utils.CollectionUtils.anyMatch;

/**
 * Future perfect verb tense insight generator.
 *
 * @see InsightGenerator
 *
 * @since 0.0.1
 */
public class FuturePerfectGenerator implements InsightGenerator<List<String>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FuturePerfectGenerator.class);

    private static final List<String> MUST_CONTAIN_ALL = Arrays.asList(MD.name(), VBN.name());
    private static final List<String> MUST_CONTAIN_ONE = Arrays.asList(PRP.name(), NNP.name(), NNPS.name());
    private static final List<String> MUST_CONTAIN_HAVE = Arrays.asList("Have", "have");
    private static final List<String> MUST_CONTAIN_MODAL = Arrays.asList(
            "will", "Will", "won't", "Won't", "'ll"
    );

    @Override
    public String getDescription() {
        return FUTURE_PERFECT.getDescription();
    }

    @Override
    public String getCode() {
        return FUTURE_PERFECT.getCode();
    }

    @Override
    public Insight<List<String>> getInsight(Subtitle subtitle) {
        final List<String> sentences = subtitle.getSentences().stream()
                .filter(s -> anyMatch(s.getSentence(), MUST_CONTAIN_HAVE)
                        && anyMatch(s.getSentence(), MUST_CONTAIN_MODAL)
                        && allMatch(s.getSentenceTags(), MUST_CONTAIN_ALL)
                        && anyMatch(s.getSentenceTags(), MUST_CONTAIN_ONE))
                .map(Sentence::getSentence).collect(toList());

        LOGGER.info(
                format("%s: %d/%d (%.2f%%)", getCode(), sentences.size(), subtitle.getSentences().size(),
                (sentences.size() * 100d / subtitle.getSentences().size()))
        );

        return new Insight<>(getCode(), sentences);
    }
}
