package com.kalnee.trivor.engine.insights.generators;

import static com.kalnee.trivor.engine.utils.LanguageUtils.MOST_COMMON_WORDS;
import static com.kalnee.trivor.engine.utils.LanguageUtils.NOT_ADJECTIVES;
import static com.kalnee.trivor.engine.utils.TagsEnum.JJ;
import static java.lang.String.format;
import static java.util.stream.Collectors.*;

import java.util.*;
import java.util.function.Function;

import com.kalnee.trivor.engine.models.Insight;
import com.kalnee.trivor.engine.models.Sentence;
import com.kalnee.trivor.engine.models.Subtitle;
import com.kalnee.trivor.engine.utils.TagsEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrequentAdjectivesGenerator implements InsightGenerator<Map<String, Long>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(FrequentAdjectivesGenerator.class);
	private static final String WORD_REGEX = "([a-zA-Z]{2,})";
	private static final List<String> TAGS = Collections.singletonList(JJ.name());

	@Override
	public String getDescription() {
		return "Most Frequently Adjectives";
	}

	@Override
	public String getCode() {
		return "frequent-adjectives";
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
