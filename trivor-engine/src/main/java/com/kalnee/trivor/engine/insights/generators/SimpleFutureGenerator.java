package com.kalnee.trivor.engine.insights.generators;

import static com.kalnee.trivor.engine.utils.CollectionUtils.anyMatch;
import static com.kalnee.trivor.engine.utils.CollectionUtils.noneMatch;
import static com.kalnee.trivor.engine.utils.TagsEnum.VBD;
import static com.kalnee.trivor.engine.utils.TagsEnum.VBN;
import static java.util.stream.Collectors.toSet;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.kalnee.trivor.engine.models.Insight;
import com.kalnee.trivor.engine.models.Sentence;
import com.kalnee.trivor.engine.models.Subtitle;

public class SimpleFutureGenerator implements InsightGenerator<Set<String>> {

	private static final List<String> MUST_CONTAIN = Arrays.asList(
		"Will", "will", "Won't", "won't", "'ll", "going to", "gonna"
	);
	private static final List<String> MUST_NOT_CONTAIN = Arrays.asList(VBN.name(), VBD.name());

	@Override
	public String getDescription() {
		return "Simple Future Tense";
	}

	@Override
	public String getCode() {
		return "simple-future";
	}

	public Insight<Set<String>> getInsight(Subtitle subtitle) {
		final Set<String> sentences = subtitle.getSentences()
			.stream()
			.filter(s -> anyMatch(s.getSentence(), MUST_CONTAIN)
				&& noneMatch(s.getSentenceTags(), MUST_NOT_CONTAIN))
			.map(Sentence::getSentence)
			.collect(toSet());

		return new Insight<>(getCode(), sentences);
	}
}
