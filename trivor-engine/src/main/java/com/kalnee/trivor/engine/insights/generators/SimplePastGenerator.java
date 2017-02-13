package com.kalnee.trivor.engine.insights.generators;

import static com.kalnee.trivor.engine.utils.CollectionUtils.anyMatch;
import static com.kalnee.trivor.engine.utils.CollectionUtils.noneMatch;
import static com.kalnee.trivor.engine.utils.TagsEnum.*;

import java.util.*;

import com.kalnee.trivor.engine.models.Insight;
import com.kalnee.trivor.engine.models.Subtitle;

public class SimplePastGenerator implements InsightGenerator<Set<String>> {

	private static final List<String> MUST_CONTAIN = Collections.singletonList(VBD.name());
	private static final List<String> MUST_NOT_CONTAIN = Arrays.asList(
		VBN.name(), VBG.name(), VBP.name(), VBZ.name()
	);

	@Override
	public String getDescription() {
		return "Simple Past Tense";
	}

	@Override
	public String getCode() {
		return "SPA";
	}

	public Insight<Set<String>> getInsight(Subtitle subtitle) {
		final Set<String> simplePast = new HashSet<>();

		subtitle.getSentences().forEach(s -> {
			final String tags = s.getSentenceTags();

			if (anyMatch(tags, MUST_CONTAIN) && noneMatch(tags, MUST_NOT_CONTAIN)) {
				simplePast.add(s.getSentence());
			}
		});

		return new Insight<>(getCode(), simplePast);
	}
}
