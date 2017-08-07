package com.kalnee.trivor.sdk.insights.generators.sentences;

import com.kalnee.trivor.sdk.insights.generators.InsightGenerator;
import com.kalnee.trivor.sdk.models.Insight;
import com.kalnee.trivor.sdk.models.Subtitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.kalnee.trivor.sdk.models.InsightsEnum.ADJECTIVES_SENTENCES;
import static com.kalnee.trivor.sdk.models.TagsEnum.JJ;
import static com.kalnee.trivor.sdk.utils.LanguageUtils.NOT_ADJECTIVES;

public class AdjectivesSentencesGenerator extends SentencesExampleGenerator implements InsightGenerator<Map<String, Set<String>>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdjectivesSentencesGenerator.class);

	@Override
	List<String> getTags() {
		return Collections.singletonList(JJ.name());
	}

	@Override
	public String getDescription() {
		return ADJECTIVES_SENTENCES.getDescription();
	}

	@Override
	public String getCode() {
		return ADJECTIVES_SENTENCES.getCode();
	}

	@Override
	public Insight<Map<String, Set<String>>> getInsight(Subtitle subtitle) {
		final Map<String, Set<String>> sentences = getSentences(subtitle, w -> !NOT_ADJECTIVES.contains(w));
		LOGGER.info("{} - {}", getCode(), sentences);

    	return new Insight<>(getCode(), sentences);
	}
}
