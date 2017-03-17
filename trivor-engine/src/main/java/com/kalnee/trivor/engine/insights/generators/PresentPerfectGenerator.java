package com.kalnee.trivor.engine.insights.generators;

import com.kalnee.trivor.engine.models.Insight;
import com.kalnee.trivor.engine.models.Sentence;
import com.kalnee.trivor.engine.models.Subtitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import static com.kalnee.trivor.engine.utils.CollectionUtils.*;
import static com.kalnee.trivor.engine.utils.TagsEnum.*;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

public class PresentPerfectGenerator implements InsightGenerator<List<String>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(PresentPerfectGenerator.class);
	static final String PRESENT_PERFECT = "present-perfect";

	private static final List<String> MUST_CONTAIN = Arrays.asList(
		"Have", "Has", "Haven't", "Hasn't", "haven't", "hasn't", "have", "has", "'ve"
	);
	private static final List<String> MUST_CONTAIN_TAGS = Arrays.asList(PRP.name(), NNP.name(), NNPS.name());
	private static final List<String> MUST_CONTAIN_NON_3RD = Arrays.asList(VBP.name(), VBN.name());
	private static final List<String> MUST_CONTAIN_3RD = Arrays.asList(VBZ.name(), VBN.name());
	private static final List<String> MUST_NOT_CONTAIN = Arrays.asList(
		VBG.name(), VBD.name()
	);
  private static final List<String> MUST_NOT_CONTAIN_MODAL = Arrays.asList(
  	"will", "Will", "won't", "Won't", "'ll", "gonna"
	);

	@Override
	public String getDescription() {
		return "Present Perfect Tense";
	}

	@Override
	public String getCode() {
		return PRESENT_PERFECT;
	}

	public Insight<List<String>> getInsight(Subtitle subtitle) {
		final List<String> sentences = subtitle.getSentences()
			.stream()
			.filter(s -> anyMatch(s.getSentence(), MUST_CONTAIN)
				&& anyMatch(s.getSentenceTags(), MUST_CONTAIN_TAGS)
				&& (allMatch(s.getSentenceTags(), MUST_CONTAIN_NON_3RD) || allMatch(s.getSentenceTags(), MUST_CONTAIN_3RD))
				&& noneMatch(s.getSentenceTags(), MUST_NOT_CONTAIN)
				&& noneMatch(s.getSentence(), MUST_NOT_CONTAIN_MODAL))
			.map(Sentence::getSentence)
			.collect(toList());

		LOGGER.info(
			format("%s: %d/%d (%.2f%%)", getCode(), sentences.size(), subtitle.getSentences().size(),
			(sentences.size() * 100d / subtitle.getSentences().size()))
		);

		return new Insight<>(getCode(), sentences);
	}
}
