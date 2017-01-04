package com.kalnee.trivor.engine.insights;

import static com.kalnee.trivor.engine.utils.LanguageUtils.MOST_COMMON_WORDS;
import static java.lang.String.format;
import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.kalnee.trivor.engine.models.Sentence;

public class FrequentWordsInsight implements Insight<Map<String, Long>> {

	private static final String WORD_REGEX = "([a-zA-Z]{2,})";
	private static final List<String> NON_WORDS_TAGS = Arrays.asList("NNP", "NNPS");
	private final List<Sentence> sentences;
	private Map<String, Long> value;

	public FrequentWordsInsight(List<Sentence> sentences) {
		this.sentences = sentences;
	}

	@Override
	public String getDescription() {
		return "Most Frequently Used Words";
	}

	@Override
	public String getCode() {
		return "FUW";
	}

	@Override
	public Map<String, Long> getValue() {
		return value;
	}

	@Override
	public void generate() {

		final Map<String, Long> words = sentences.stream()
			.flatMap(s -> s.getTokens().stream())
			.filter(t -> t.getToken().matches(WORD_REGEX))
			.filter(t -> !NON_WORDS_TAGS.contains(t.getTag()))
			.map(t -> t.getToken().toLowerCase())
			.filter(w -> !MOST_COMMON_WORDS.contains(w))
			.collect(groupingBy(Function.identity(), counting()));

		value = words.entrySet().stream()
			.sorted(Map.Entry.<String, Long> comparingByValue().reversed())
			//.limit(10)
			.collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> {
				throw new RuntimeException(format("Duplicate key for values %s and %s", v1, v2));
			}, LinkedHashMap::new));
	}
}
