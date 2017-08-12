package com.kalnee.trivor.nlp.insights.generators.tenses;

import com.kalnee.trivor.nlp.insights.generators.InsightGenerator;
import com.kalnee.trivor.nlp.nlp.models.Insight;
import com.kalnee.trivor.nlp.nlp.models.Sentence;
import com.kalnee.trivor.nlp.nlp.models.Subtitle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.kalnee.trivor.nlp.nlp.models.InsightsEnum.SIMPLE_PAST;
import static com.kalnee.trivor.nlp.nlp.models.TagsEnum.*;
import static com.kalnee.trivor.nlp.utils.CollectionUtils.*;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

public class SimplePastGenerator implements InsightGenerator<List<String>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SimplePastGenerator.class);

	private static final List<String> MUST_CONTAIN = Arrays.asList(PRP.name(), NNP.name(), NNPS.name());
	private static final List<String> MUST_CONTAIN_VERB = Collections.singletonList(VBD.name());
	private static final List<String> MUST_NOT_CONTAIN = Arrays.asList(
		VBN.name(), VBG.name(), VBZ.name()
	);
	private static final List<String> MUST_NOT_CONTAIN_WORDS = Arrays.asList(
		"Will", "will", "Won't", "won't", "'ll", "going to", "gonna"
	);

	@Override
	public String getDescription() {
		return SIMPLE_PAST.getDescription();
	}

	@Override
	public String getCode() {
		return SIMPLE_PAST.getCode();
	}

	public Insight<List<String>> getInsight(Subtitle subtitle) {
		final List<String> sentences = subtitle.getSentences()
			.stream()
			.filter(s -> anyMatch(s.getSentenceTags(), MUST_CONTAIN)
				&& allMatch(s.getSentenceTags(), MUST_CONTAIN_VERB)
				&& noneMatch(s.getSentenceTags(), MUST_NOT_CONTAIN)
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