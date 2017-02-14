package com.kalnee.trivor.engine.insights.generators;

import static com.kalnee.trivor.engine.utils.CollectionUtils.anyMatch;
import static com.kalnee.trivor.engine.utils.CollectionUtils.noneMatch;
import static com.kalnee.trivor.engine.utils.TagsEnum.*;
import static java.util.stream.Collectors.toSet;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.kalnee.trivor.engine.models.Insight;
import com.kalnee.trivor.engine.models.Sentence;
import com.kalnee.trivor.engine.models.Subtitle;

public class SimplePastGenerator implements InsightGenerator<Set<String>> {

	private static final List<String> MUST_CONTAIN = Collections.singletonList(VBD.name());
	private static final List<String> MUST_NOT_CONTAIN = Arrays.asList(
		VBN.name(), VBG.name(), VBZ.name()
	);

	@Override
	public String getDescription() {
		return "Simple Past Tense";
	}

	@Override
	public String getCode() {
		return "simple-past";
	}

	public Insight<Set<String>> getInsight(Subtitle subtitle) {
		final Set<String> sentences = subtitle.getSentences()
			.stream()
			.filter(s -> anyMatch(s.getSentenceTags(), MUST_CONTAIN)
				&& noneMatch(s.getSentenceTags(), MUST_NOT_CONTAIN))
			.map(Sentence::getSentence)
			.collect(toSet());

		return new Insight<>(getCode(), sentences);
	}
}
