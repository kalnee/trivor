package com.kalnee.trivor.engine.insights.generators;

import static com.kalnee.trivor.engine.utils.CollectionUtils.anyMatch;
import static com.kalnee.trivor.engine.utils.CollectionUtils.noneMatch;
import static com.kalnee.trivor.engine.utils.TagsEnum.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.kalnee.trivor.engine.models.Insight;
import com.kalnee.trivor.engine.models.Subtitle;

public class SimplePresentGenerator implements InsightGenerator<Set<String>> {

	private static final List<String> MUST_CONTAIN = Arrays.asList(VBP.name(), VBZ.name());
	private static final List<String> MUST_NOT_CONTAIN = Arrays.asList(VBN.name(), VBG.name(), VBD.name());

	@Override
	public String getDescription() {
		return "Simple Present Tense";
	}

	@Override
	public String getCode() {
		return "SPT";
	}

	public Insight<Set<String>> getInsight(Subtitle subtitle) {
		final Set<String> simplePresent = new HashSet<>();

		subtitle.getSentences().forEach(s -> {
			final String tags = s.getSentenceTags();

			if (anyMatch(tags, MUST_CONTAIN) && noneMatch(tags, MUST_NOT_CONTAIN)) {
				simplePresent.add(s.getSentence());
			}
		});

		return new Insight<>(getCode(), simplePresent);
	}
}
