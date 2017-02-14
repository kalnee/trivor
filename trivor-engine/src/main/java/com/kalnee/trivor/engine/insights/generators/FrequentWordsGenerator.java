package com.kalnee.trivor.engine.insights.generators;

import static com.kalnee.trivor.engine.utils.LanguageUtils.MOST_COMMON_WORDS;
import static java.lang.String.format;
import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.kalnee.trivor.engine.models.Insight;
import com.kalnee.trivor.engine.models.Sentence;
import com.kalnee.trivor.engine.models.Subtitle;

public class FrequentWordsGenerator implements InsightGenerator<Map<String, Long>> {

	private static final String WORD_REGEX = "([a-zA-Z]{2,})";
	private static final List<String> NON_WORDS_TAGS = Arrays.asList("NNP", "NNPS");

	@Override
	public String getDescription() {
		return "Most Frequently Used Words";
	}

	@Override
	public String getCode() {
		return "frequent-words";
	}

	@Override
	public Insight<Map<String, Long>> getInsight(Subtitle subtitle) {

		final Map<String, Long> words = subtitle.getSentences().stream()
			.flatMap(s -> s.getTokens().stream())
			.filter(t -> t.getToken().matches(WORD_REGEX))
			.filter(t -> !NON_WORDS_TAGS.contains(t.getTag()))
			.map(t -> t.getToken().toLowerCase())
			.filter(w -> !MOST_COMMON_WORDS.contains(w))
			.collect(groupingBy(Function.identity(), counting()));

		final Map<String, Long> commonWords = words.entrySet().stream()
			.sorted(Map.Entry.<String, Long> comparingByValue().reversed())
			.limit(10)
			.collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> {
				throw new RuntimeException(format("Duplicate key for values %s and %s", v1, v2));
			}, LinkedHashMap::new));

    return new Insight<>(getCode(), commonWords);
	}
}
