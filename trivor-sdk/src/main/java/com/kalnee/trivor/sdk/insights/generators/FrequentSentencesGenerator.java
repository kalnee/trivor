package com.kalnee.trivor.sdk.insights.generators;


import com.kalnee.trivor.sdk.models.Insight;
import com.kalnee.trivor.sdk.models.Sentence;
import com.kalnee.trivor.sdk.models.Subtitle;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import static com.kalnee.trivor.sdk.models.InsightsEnum.FREQUENT_SENTENCES;
import static java.lang.String.format;
import static java.util.stream.Collectors.*;

public class FrequentSentencesGenerator implements InsightGenerator<Map<String, Long>> {

	@Override
	public String getDescription() {
		return FREQUENT_SENTENCES.getDescription();
	}

	@Override
	public String getCode() {
		return FREQUENT_SENTENCES.getCode();
	}

	@Override
	public Insight<Map<String, Long>> getInsight(Subtitle subtitle) {
		final Map<String, Long> words = subtitle.getSentences().parallelStream()
			.filter(s -> s.getTokens().size() > 3)
			.map(Sentence::getSentence)
			.map(s -> s.replaceAll("\\.", ""))
			.collect(groupingBy(Function.identity(), counting()));

		final Map<String, Long> commonSentences = words.entrySet().parallelStream()
			.sorted(Map.Entry.<String, Long> comparingByValue().reversed())
			.filter(e -> e.getValue() > 1)
			.collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v2, LinkedHashMap::new));

    	return new Insight<>(getCode(), commonSentences);
	}
}
