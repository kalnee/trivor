package com.kalnee.trivor.nlp.insights.generators.post;

import com.kalnee.trivor.nlp.nlp.models.Insight;
import com.kalnee.trivor.nlp.nlp.models.Sentence;
import com.kalnee.trivor.nlp.nlp.models.Subtitle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.kalnee.trivor.nlp.nlp.models.InsightsEnum.*;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

public class MixedTensesPostInsightGenerator implements PostInsightGenerator<List<String>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(MixedTensesPostInsightGenerator.class);

	private static final List<String> INSIGHTS = Arrays.asList(
		SIMPLE_PRESENT.getCode(), SIMPLE_PAST.getCode(), SIMPLE_FUTURE.getCode(), PRESENT_PROGRESSIVE.getCode(),
		PAST_PROGRESSIVE.getCode(), FUTURE_PROGRESSIVE.getCode(), PRESENT_PERFECT.getCode(), PAST_PERFECT.getCode(),
		NON_SENTENCES.getCode()
	);

	@Override
	public String getDescription() {
		return "Mixed Tenses";
	}

	@Override
	public String getCode() {
		return "mixed-tenses";
	}

	@SuppressWarnings("unchecked")
	public Insight<List<String>> getInsight(Subtitle subtitle, Map<String, Object> insights) {

		final List<String> all = insights.entrySet().stream()
			.filter(i -> INSIGHTS.contains(i.getKey()))
			.flatMap(i -> ((List<String>) i.getValue()).stream())
			.collect(toList());

		final List<String> mixed = subtitle.getSentences().stream()
			.filter(s -> !all.contains(s.getSentence()))
			.map(Sentence::getSentence)
			.collect(toList());

		LOGGER.info(
			format("%s: %d/%d (%.2f%%)", getCode(), mixed.size(), subtitle.getSentences().size(),
				(mixed.size() * 100d / subtitle.getSentences().size()))
		);

		LOGGER.info("total: {}/{}", all.size() + mixed.size(), subtitle.getSentences().size());

		return new Insight<>(getCode(), mixed);
	}
}
