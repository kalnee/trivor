package com.kalnee.trivor.engine.insights.generators;

import static com.kalnee.trivor.engine.utils.CollectionUtils.*;
import static com.kalnee.trivor.engine.utils.TagsEnum.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.kalnee.trivor.engine.models.Insight;
import com.kalnee.trivor.engine.models.Subtitle;

public class PresentPerfectGenerator implements InsightGenerator<Set<String>> {

	private static final List<String> MUST_CONTAIN = Arrays.asList(
		"Have", "Has", "Haven't", "Hasn't", "haven't", "hasn't", "have", "has", "'ve"
	);
	private static final List<String> MUST_CONTAIN_NON_3RD = Arrays.asList(VBP.name(), VBN.name());
	private static final List<String> MUST_CONTAIN_3RD = Arrays.asList(VBZ.name(), VBN.name());
	private static final List<String> MUST_NOT_CONTAIN = Arrays.asList(
		VBG.name(), VBD.name()
	);

	@Override
	public String getDescription() {
		return "Present Perfect Tense";
	}

	@Override
	public String getCode() {
		return "PPT";
	}

	public Insight<Set<String>> getInsight(Subtitle subtitle) {
		final Set<String> simplePast = new HashSet<>();

		subtitle.getSentences().forEach(s -> {
			final String tags = s.getSentenceTags();

			if (anyMatch(s.getSentence(), MUST_CONTAIN)
				&& (allMatch(tags, MUST_CONTAIN_NON_3RD) || allMatch(tags, MUST_CONTAIN_3RD))
				&& noneMatch(tags, MUST_NOT_CONTAIN)) {
				simplePast.add(s.getSentence());
			}
		});

		return new Insight<>(getCode(), simplePast);
	}
}
