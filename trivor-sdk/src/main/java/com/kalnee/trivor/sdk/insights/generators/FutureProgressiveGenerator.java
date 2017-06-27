package com.kalnee.trivor.sdk.insights.generators;

import com.kalnee.trivor.sdk.models.Insight;
import com.kalnee.trivor.sdk.models.Sentence;
import com.kalnee.trivor.sdk.models.Subtitle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.kalnee.trivor.sdk.models.InsightsEnum.FUTURE_PROGRESSIVE;
import static com.kalnee.trivor.sdk.models.TagsEnum.*;
import static com.kalnee.trivor.sdk.utils.CollectionUtils.*;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

public class FutureProgressiveGenerator implements InsightGenerator<List<String>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(FutureProgressiveGenerator.class);

	private static final List<String> MUST_CONTAIN = Arrays.asList(PRP.name(), NNP.name(), NNPS.name());
	private static final List<String> MUST_CONTAIN_VERB = Collections.singletonList(VBG.name());
	private static final List<String> MUST_CONTAIN_BE = Collections.singletonList("be");
	private static final List<String> MUST_CONTAIN_WORDS = Arrays.asList(
		"Will", "will be", "Won't", "won't be", "'ll be", "gonna be"
	);
	private static final List<String> MUST_NOT_CONTAIN = Arrays.asList(VBN.name(), VBD.name());

	@Override
	public String getDescription() {
		return FUTURE_PROGRESSIVE.getDescription();
	}

	@Override
	public String getCode() {
		return FUTURE_PROGRESSIVE.getCode();
	}

	public Insight<List<String>> getInsight(Subtitle subtitle) {
		final List<String> sentences = subtitle.getSentences()
			.stream()
			.filter(s -> allMatch(s.getSentence(), MUST_CONTAIN_BE)
				&& allMatch(s.getSentenceTags(), MUST_CONTAIN_VERB)
				&& anyMatch(s.getSentenceTags(), MUST_CONTAIN)
				&& anyMatch(s.getSentence(), MUST_CONTAIN_WORDS)
				&& noneMatch(s.getSentenceTags(), MUST_NOT_CONTAIN))
			.map(Sentence::getSentence)
			.collect(toList());


		LOGGER.info(
			format("%s: %d/%d (%.2f%%)", getCode(), sentences.size(), subtitle.getSentences().size(),
			(sentences.size() * 100d / subtitle.getSentences().size()))
		);

		return new Insight<>(getCode(), sentences);
	}
}
