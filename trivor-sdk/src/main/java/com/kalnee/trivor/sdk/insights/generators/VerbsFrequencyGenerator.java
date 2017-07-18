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

import static com.kalnee.trivor.sdk.models.InsightsEnum.VERBS_FREQUENCY;
import static com.kalnee.trivor.sdk.models.TagsEnum.*;
import static com.kalnee.trivor.sdk.utils.LanguageUtils.NOT_ADJECTIVES;
import static java.lang.Character.isUpperCase;
import static java.lang.String.format;
import static java.util.stream.Collectors.*;

public class VerbsFrequencyGenerator implements InsightGenerator<Map<String, Long>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(VerbsFrequencyGenerator.class);
	private static final String WORD_REGEX = "([a-zA-Z]{2,})";
	private static final List<String> TAGS = Arrays.asList(
			VB.name(), VBD.name(), VBG.name(), VBN.name(), VBP.name(), VBZ.name()
	);

	@Override
	public String getDescription() {
		return VERBS_FREQUENCY.getDescription();
	}

	@Override
	public String getCode() {
		return VERBS_FREQUENCY.getCode();
	}

	@Override
	public Insight<Map<String, Long>> getInsight(Subtitle subtitle) {
		final Map<String, Long> words = subtitle.getSentences().parallelStream()
			.flatMap(s -> s.getTokens().stream())
			.filter(t -> t.getToken().matches(WORD_REGEX))
			.filter(t -> TAGS.contains(t.getTag()))
			.filter(t -> !isUpperCase(t.getToken().charAt(0)))
			.filter(t -> t.getProb() >= 0.90)
			.map(Token::getLemma)
			.collect(groupingBy(Function.identity(), counting()));

		final Map<String, Long> commonWords = words.entrySet().parallelStream()
			.sorted(Map.Entry.<String, Long> comparingByValue().reversed())
			.collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v2, LinkedHashMap::new));

		LOGGER.info("{} - {}", getCode(), commonWords);

    	return new Insight<>(getCode(), commonWords);
	}
}
