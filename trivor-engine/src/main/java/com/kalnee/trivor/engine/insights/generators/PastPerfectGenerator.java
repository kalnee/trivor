package com.kalnee.trivor.engine.insights.generators;

import static com.kalnee.trivor.engine.utils.CollectionUtils.*;
import static com.kalnee.trivor.engine.utils.TagsEnum.*;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kalnee.trivor.engine.models.Insight;
import com.kalnee.trivor.engine.models.Sentence;
import com.kalnee.trivor.engine.models.Subtitle;

public class PastPerfectGenerator implements InsightGenerator<List<String>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(PastPerfectGenerator.class);

	private static final List<String> MUST_CONTAIN = Collections.singletonList(VBN.name());
	private static final List<String> MUST_CONTAIN_WORDS = Arrays.asList(
		"Had", "Hadn't", "had", "hadn't", "'d"
	);
	private static final List<String> MUST_NOT_CONTAIN = Arrays.asList(
		VBG.name(), VBP.name(), VBZ.name(), VB.name()
	);

	@Override
	public String getDescription() {
		return "Past Perfect Tense";
	}

	@Override
	public String getCode() {
		return "past-perfect";
	}

	public Insight<List<String>> getInsight(Subtitle subtitle) {
		final List<String> sentences = subtitle.getSentences()
			.stream()
			.filter(s -> anyMatch(s.getSentence(), MUST_CONTAIN_WORDS)
				&& allMatch(s.getSentenceTags(), MUST_CONTAIN)
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
