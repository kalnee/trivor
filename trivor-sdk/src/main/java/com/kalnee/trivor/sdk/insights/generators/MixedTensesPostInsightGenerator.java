package com.kalnee.trivor.sdk.insights.generators;

import com.kalnee.trivor.sdk.models.Insight;
import com.kalnee.trivor.sdk.models.Sentence;
import com.kalnee.trivor.sdk.models.Subtitle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

import static com.kalnee.trivor.sdk.models.InsightsEnum.*;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

public class MixedTensesPostInsightGenerator implements PostInsightGenerator<List<String>> {

	private static final Logger LOGGER = LogManager.getLogger(MixedTensesPostInsightGenerator.class);

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
	public Insight<List<String>> getInsight(Subtitle subtitle, List<Insight> insights) {
		final List<String> all = insights.stream()
			.filter(i -> INSIGHTS.contains(i.getCode()))
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
