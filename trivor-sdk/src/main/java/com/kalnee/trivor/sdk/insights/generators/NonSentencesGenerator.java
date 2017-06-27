package com.kalnee.trivor.sdk.insights.generators;

import com.kalnee.trivor.sdk.models.Insight;
import com.kalnee.trivor.sdk.models.Sentence;
import com.kalnee.trivor.sdk.models.Subtitle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import static com.kalnee.trivor.sdk.models.InsightsEnum.NON_SENTENCES;
import static com.kalnee.trivor.sdk.models.TagsEnum.*;
import static com.kalnee.trivor.sdk.utils.CollectionUtils.noneMatch;
import static com.kalnee.trivor.sdk.utils.CollectionUtils.singleMatch;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

public class NonSentencesGenerator implements InsightGenerator<List<String>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(NonSentencesGenerator.class);

	private static final List<String> MUST_NOT_CONTAIN = Arrays.asList(
		VBN.name(), VBG.name(), VBD.name(), VBP.name(), VBZ.name(), PRP.name(), VB.name(), NNP.name(), NNPS.name()
	);
	private static final List<String> MUST_NOT_CONTAIN_WORDS = Arrays.asList(
		"Will", "will", "Won't", "won't", "'ll", "going to", "gonna"
	);

	@Override
	public String getDescription() {
		return NON_SENTENCES.getDescription();
	}

	@Override
	public String getCode() {
		return NON_SENTENCES.getCode();
	}

	public Insight<List<String>> getInsight(Subtitle subtitle) {
		final List<String> sentences = subtitle.getSentences()
			.stream()
			.filter(s -> (noneMatch(s.getSentenceTags(), MUST_NOT_CONTAIN)
				|| singleMatch(s.getSentenceTags(), MUST_NOT_CONTAIN))
			 	&& noneMatch(s.getSentence(), MUST_NOT_CONTAIN_WORDS))
			.map(Sentence::getSentence)
			.collect(toList());

		LOGGER.info(
			format("%s: %d/%d (%.2f%%)", getCode(), sentences.size(), subtitle.getSentences().size(),
			(sentences.size() * 100d / subtitle.getSentences().size()))
		);

		return new Insight<>(getCode(), sentences);
	}
}
