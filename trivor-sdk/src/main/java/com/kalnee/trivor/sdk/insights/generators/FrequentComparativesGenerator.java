package com.kalnee.trivor.sdk.insights.generators;

import com.kalnee.trivor.sdk.models.Insight;
import com.kalnee.trivor.sdk.models.Subtitle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.kalnee.trivor.sdk.models.InsightsEnum.FREQUENT_COMPARATIVES;
import static com.kalnee.trivor.sdk.models.TagsEnum.JJR;
import static com.kalnee.trivor.sdk.utils.LanguageUtils.NOT_ADJECTIVES;
import static java.lang.String.format;
import static java.util.stream.Collectors.*;

public class FrequentComparativesGenerator implements InsightGenerator<Map<String, Long>> {

	private static final Logger LOGGER = LogManager.getLogger(FrequentComparativesGenerator.class);
	private static final String WORD_REGEX = "([a-zA-Z]{2,})";
	private static final List<String> TAGS = Collections.singletonList(JJR.name());

	@Override
	public String getDescription() {
		return FREQUENT_COMPARATIVES.getDescription();
	}

	@Override
	public String getCode() {
		return FREQUENT_COMPARATIVES.getCode();
	}

	@Override
	public Insight<Map<String, Long>> getInsight(Subtitle subtitle) {

		final Map<String, Long> words = subtitle.getSentences().stream()
			.flatMap(s -> s.getTokens().stream())
			.filter(t -> t.getToken().matches(WORD_REGEX))
			.filter(t -> TAGS.contains(t.getTag()))
			.map(t -> t.getToken().toLowerCase())
			.filter(w -> !NOT_ADJECTIVES.contains(w))
			.collect(groupingBy(Function.identity(), counting()));

		final Map<String, Long> commonWords = words.entrySet().stream()
			.sorted(Map.Entry.<String, Long> comparingByValue().reversed())
			//.limit(10)
			.collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> {
				throw new RuntimeException(format("Duplicate key for values %s and %s", v1, v2));
			}, LinkedHashMap::new));

		LOGGER.info("{} - {}", getCode(), commonWords);

    return new Insight<>(getCode(), commonWords);
	}
}
