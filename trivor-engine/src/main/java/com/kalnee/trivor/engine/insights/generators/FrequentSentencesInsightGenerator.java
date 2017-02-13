package com.kalnee.trivor.engine.insights.generators;

import static java.lang.String.format;
import static java.util.stream.Collectors.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import com.kalnee.trivor.engine.models.Insight;
import com.kalnee.trivor.engine.models.Sentence;
import com.kalnee.trivor.engine.models.Subtitle;

public class FrequentSentencesInsightGenerator implements InsightGenerator<Map<String, Long>> {

	@Override
	public String getDescription() {
		return "Most Frequently Used Sentences";
	}

	@Override
	public String getCode() {
		return "FUS";
	}

	@Override
	public Insight<Map<String, Long>> getInsight(Subtitle subtitle) {

		final Map<String, Long> words = subtitle.getSentences().stream()
			.filter(s -> s.getTokens().size() > 3)
			.map(Sentence::getSentence)
			.map(s -> s.replaceAll("\\.", ""))
			.collect(groupingBy(Function.identity(), counting()));

		final Map<String, Long> commonSentences = words.entrySet().stream()
			.sorted(Map.Entry.<String, Long> comparingByValue().reversed())
			.limit(10)
			.collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> {
				throw new RuntimeException(format("Duplicate key for values %s and %s", v1, v2));
			}, LinkedHashMap::new));

    return new Insight<>(getCode(), commonSentences);
	}
}
