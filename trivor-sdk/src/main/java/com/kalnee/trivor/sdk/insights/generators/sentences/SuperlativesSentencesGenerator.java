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

import static com.kalnee.trivor.sdk.models.InsightsEnum.SUPERLATIVES_SENTENCES;
import static com.kalnee.trivor.sdk.models.TagsEnum.JJS;
import static com.kalnee.trivor.sdk.utils.LanguageUtils.NOT_ADJECTIVES;

public class SuperlativesSentencesGenerator extends SentencesExampleGenerator implements InsightGenerator<Map<String, Set<String>>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SuperlativesSentencesGenerator.class);

	@Override
	List<String> getTags() {
		return Collections.singletonList(JJS.name());
	}

	@Override
	public String getDescription() {
		return SUPERLATIVES_SENTENCES.getDescription();
	}

	@Override
	public String getCode() {
		return SUPERLATIVES_SENTENCES.getCode();
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
