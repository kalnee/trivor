package com.kalnee.trivor.sdk.insights.generators;

import com.kalnee.trivor.sdk.models.Insight;
import com.kalnee.trivor.sdk.models.Subtitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.kalnee.trivor.sdk.models.InsightsEnum.SUPERLATIVES_FREQUENCY;
import static com.kalnee.trivor.sdk.models.TagsEnum.JJS;
import static com.kalnee.trivor.sdk.utils.LanguageUtils.NOT_ADJECTIVES;

public class SuperlativesFrequencyGenerator extends FrequencyGenerator implements InsightGenerator<Map<String, Long>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SuperlativesFrequencyGenerator.class);

	@Override
	List<String> getTags() {
		return Collections.singletonList(JJS.name());
	}

	@Override
	public String getDescription() {
		return SUPERLATIVES_FREQUENCY.getDescription();
	}

	@Override
	public String getCode() {
		return SUPERLATIVES_FREQUENCY.getCode();
	}

	@Override
	public Insight<Map<String, Long>> getInsight(Subtitle subtitle) {
		final Map<String, Long> commonWords = getFrequency(
				subtitle, t -> t.getToken().toLowerCase(), w -> !NOT_ADJECTIVES.contains(w)
		);
		LOGGER.info("{} - {}", getCode(), commonWords);

    	return new Insight<>(getCode(), commonWords);
	}
}
