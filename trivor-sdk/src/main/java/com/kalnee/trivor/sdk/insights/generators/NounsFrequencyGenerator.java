package com.kalnee.trivor.sdk.insights.generators;


import com.kalnee.trivor.sdk.models.Insight;
import com.kalnee.trivor.sdk.models.Subtitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.kalnee.trivor.sdk.models.InsightsEnum.NOUNS_FREQUENCY;
import static com.kalnee.trivor.sdk.models.TagsEnum.NN;
import static com.kalnee.trivor.sdk.models.TagsEnum.NNS;
import static com.kalnee.trivor.sdk.utils.LanguageUtils.MOST_COMMON_WORDS;

public class NounsFrequencyGenerator extends FrequencyGenerator implements InsightGenerator<Map<String, Long>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(NounsFrequencyGenerator.class);

	@Override
	List<String> getTags() {
		return Arrays.asList(NN.name(), NNS.name());
	}

	@Override
	public String getDescription() {
		return NOUNS_FREQUENCY.getDescription();
	}

	@Override
	public String getCode() {
		return NOUNS_FREQUENCY.getCode();
	}

	@Override
	public Insight<Map<String, Long>> getInsight(Subtitle subtitle) {
		final Map<String, Long> commonWords = getFrequency(subtitle, w -> !MOST_COMMON_WORDS.contains(w));
		LOGGER.info("{} - {}", getCode(), commonWords);

    	return new Insight<>(getCode(), commonWords);
	}
}
