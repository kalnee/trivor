package com.kalnee.trivor.sdk.insights.generators.tenses;

import com.kalnee.trivor.sdk.insights.generators.InsightGenerator;
import com.kalnee.trivor.sdk.models.Insight;
import com.kalnee.trivor.sdk.models.Sentence;
import com.kalnee.trivor.sdk.models.Subtitle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import static com.kalnee.trivor.sdk.models.InsightsEnum.SIMPLE_FUTURE;
import static com.kalnee.trivor.sdk.models.TagsEnum.*;
import static com.kalnee.trivor.sdk.utils.CollectionUtils.anyMatch;
import static com.kalnee.trivor.sdk.utils.CollectionUtils.noneMatch;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

public class SimpleFutureGenerator implements InsightGenerator<List<String>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleFutureGenerator.class);

	private static final List<String> MUST_CONTAIN = Arrays.asList(PRP.name(), NNP.name(), NNPS.name());
	private static final List<String> MUST_CONTAIN_WORDS = Arrays.asList(
		"Will", "will", "Won't", "won't", "'ll", "going to", "gonna"
	);
	private static final List<String> MUST_NOT_CONTAIN = Arrays.asList(VBN.name(), VBD.name(), VBG.name());

	@Override
	public String getDescription() {
		return SIMPLE_FUTURE.getDescription();
	}

	@Override
	public String getCode() {
		return SIMPLE_FUTURE.getCode();
	}

	public Insight<List<String>> getInsight(Subtitle subtitle) {
		final List<String> sentences = subtitle.getSentences()
			.stream()
			.filter(s -> anyMatch(s.getSentenceTags(), MUST_CONTAIN)
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
