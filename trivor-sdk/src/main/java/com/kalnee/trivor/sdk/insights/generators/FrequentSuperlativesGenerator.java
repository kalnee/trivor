package com.kalnee.trivor.sdk.insights.generators;

import com.kalnee.trivor.sdk.models.Insight;
import com.kalnee.trivor.sdk.models.Subtitle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.kalnee.trivor.sdk.models.InsightsEnum.FREQUENT_SUPERLATIVES;
import static com.kalnee.trivor.sdk.models.TagsEnum.JJS;
import static com.kalnee.trivor.sdk.utils.LanguageUtils.NOT_ADJECTIVES;
import static java.lang.String.format;
import static java.util.stream.Collectors.*;

public class FrequentSuperlativesGenerator implements InsightGenerator<Map<String, Long>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(FrequentSuperlativesGenerator.class);
	private static final String WORD_REGEX = "([a-zA-Z]{2,})";
	private static final List<String> TAGS = Collections.singletonList(JJS.name());

	@Override
	public String getDescription() {
		return FREQUENT_SUPERLATIVES.getDescription();
	}

	@Override
	public String getCode() {
		return FREQUENT_SUPERLATIVES.getCode();
	}

	@Override
	public Insight<Map<String, Long>> getInsight(Subtitle subtitle) {

		final Map<String, Long> words = subtitle.getSentences().parallelStream()
			.flatMap(s -> s.getTokens().stream())
			.filter(t -> t.getToken().matches(WORD_REGEX))
			.filter(t -> TAGS.contains(t.getTag()))
			.map(t -> t.getToken().toLowerCase())
			.filter(w -> !NOT_ADJECTIVES.contains(w))
			.collect(groupingBy(Function.identity(), counting()));

		final Map<String, Long> commonWords = words.entrySet().parallelStream()
			.sorted(Map.Entry.<String, Long> comparingByValue().reversed())
			//.limit(10)
			.collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> {
				throw new RuntimeException(format("Duplicate key for values %s and %s", v1, v2));
			}, LinkedHashMap::new));

		LOGGER.info("{} - {}", getCode(), commonWords);

    	return new Insight<>(getCode(), commonWords);
	}
}
