package com.kalnee.trivor.sdk.insights.generators.sentences;


import com.kalnee.trivor.sdk.insights.generators.InsightGenerator;
import com.kalnee.trivor.sdk.models.Insight;
import com.kalnee.trivor.sdk.models.Subtitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.kalnee.trivor.sdk.models.InsightsEnum.NOUNS_SENTENCES;
import static com.kalnee.trivor.sdk.models.TagsEnum.NN;
import static com.kalnee.trivor.sdk.models.TagsEnum.NNS;
import static com.kalnee.trivor.sdk.utils.LanguageUtils.MOST_COMMON_WORDS;

public class NounsSentencesGenerator extends SentencesExampleGenerator implements InsightGenerator<Map<String, Set<String>>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(NounsSentencesGenerator.class);

	@Override
	List<String> getTags() {
		return Arrays.asList(NN.name(), NNS.name());
	}

	@Override
	public String getDescription() {
		return NOUNS_SENTENCES.getDescription();
	}

	@Override
	public String getCode() {
		return NOUNS_SENTENCES.getCode();
	}

	@Override
	public Insight<Map<String, Set<String>>> getInsight(Subtitle subtitle) {
		final Map<String, Set<String>> sentences = getSentences(subtitle, w -> !MOST_COMMON_WORDS.contains(w));
		LOGGER.info("{} - {}", getCode(), sentences);

    	return new Insight<>(getCode(), sentences);
	}
}
