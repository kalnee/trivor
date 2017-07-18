package com.kalnee.trivor.sdk.insights.generators;


import com.kalnee.trivor.sdk.models.Insight;
import com.kalnee.trivor.sdk.models.Subtitle;

import com.kalnee.trivor.sdk.models.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.kalnee.trivor.sdk.models.InsightsEnum.FREQUENT_NOUNS;
import static com.kalnee.trivor.sdk.models.TagsEnum.NN;
import static com.kalnee.trivor.sdk.models.TagsEnum.NNS;
import static com.kalnee.trivor.sdk.utils.LanguageUtils.MOST_COMMON_WORDS;
import static java.lang.Character.isUpperCase;
import static java.lang.String.format;
import static java.util.stream.Collectors.*;

public class FrequentNounsGenerator implements InsightGenerator<Map<String, Long>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(FrequentNounsGenerator.class);
	private static final String WORD_REGEX = "([a-zA-Z]{2,})";
	private static final List<String> TAGS = Arrays.asList(NN.name(), NNS.name());

	@Override
	public String getDescription() {
		return FREQUENT_NOUNS.getDescription();
	}

	@Override
	public String getCode() {
		return FREQUENT_NOUNS.getCode();
	}

	@Override
	public Insight<Map<String, Long>> getInsight(Subtitle subtitle) {

		final Map<String, Long> words = subtitle.getSentences().parallelStream()
			.flatMap(s -> s.getTokens().stream())
			.filter(t -> t.getToken().matches(WORD_REGEX))
			.filter(t -> TAGS.contains(t.getTag()))
			.filter(t -> !isUpperCase(t.getToken().charAt(0)))
			.map(Token::getLemma)
			.filter(w -> !MOST_COMMON_WORDS.contains(w))
			.collect(groupingBy(Function.identity(), counting()));

		final Map<String, Long> commonWords = words.entrySet().parallelStream()
			.sorted(Map.Entry.<String, Long> comparingByValue().reversed())
			.collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> {
				throw new RuntimeException(format("Duplicate key for values %s and %s", v1, v2));
			}, LinkedHashMap::new));

		LOGGER.info("{} - {}", getCode(), commonWords);

    	return new Insight<>(getCode(), commonWords);
	}
}
