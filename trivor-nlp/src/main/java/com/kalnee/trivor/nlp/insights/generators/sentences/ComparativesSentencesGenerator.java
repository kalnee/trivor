package com.kalnee.trivor.nlp.insights.generators.sentences;

import com.kalnee.trivor.nlp.insights.generators.InsightGenerator;
import com.kalnee.trivor.nlp.nlp.models.Insight;
import com.kalnee.trivor.nlp.nlp.models.Subtitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.kalnee.trivor.nlp.nlp.models.InsightsEnum.COMPARATIVES_SENTENCES;
import static com.kalnee.trivor.nlp.nlp.models.TagsEnum.JJR;
import static com.kalnee.trivor.nlp.utils.LanguageUtils.NOT_ADJECTIVES;

public class ComparativesSentencesGenerator extends SentencesExampleGenerator
		implements InsightGenerator<Map<String, Set<String>>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ComparativesSentencesGenerator.class);

	@Override
	List<String> getTags() {
		return Collections.singletonList(JJR.name());
	}

	@Override
	public String getDescription() {
		return COMPARATIVES_SENTENCES.getDescription();
	}

	@Override
	public String getCode() {
		return COMPARATIVES_SENTENCES.getCode();
	}

	@Override
	public Insight<Map<String, Set<String>>> getInsight(Subtitle subtitle) {
		final Map<String, Set<String>> sentences = getSentences(
				subtitle, t -> t.getToken().toLowerCase(), w -> !NOT_ADJECTIVES.contains(w)
		);
		LOGGER.info("{} - {}", getCode(), sentences);

    	return new Insight<>(getCode(), sentences);
	}
}
