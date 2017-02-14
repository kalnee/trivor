package com.kalnee.trivor.engine.insights.generators;

import static com.kalnee.trivor.engine.utils.CollectionUtils.noneMatch;
import static com.kalnee.trivor.engine.utils.TagsEnum.*;
import static java.util.stream.Collectors.toSet;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.kalnee.trivor.engine.models.Insight;
import com.kalnee.trivor.engine.models.Sentence;
import com.kalnee.trivor.engine.models.Subtitle;

public class NonSentencesGenerator implements InsightGenerator<Set<String>> {

	private static final List<String> MUST_NOT_CONTAIN = Arrays.asList(
		VBN.name(), VBG.name(), VBD.name(), VBP.name(), VBZ.name(), VB.name()
	);

	@Override
	public String getDescription() {
		return "Non Sentences";
	}

	@Override
	public String getCode() {
		return "non-sentences";
	}

	public Insight<Set<String>> getInsight(Subtitle subtitle) {
		final Set<String> sentences = subtitle.getSentences()
			.stream()
			.filter(s -> noneMatch(s.getSentenceTags(), MUST_NOT_CONTAIN))
			.map(Sentence::getSentence)
			.collect(toSet());

		return new Insight<>(getCode(), sentences);
	}
}
