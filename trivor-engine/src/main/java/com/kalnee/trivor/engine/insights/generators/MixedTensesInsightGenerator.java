package com.kalnee.trivor.engine.insights.generators;

import com.kalnee.trivor.engine.models.Sentence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kalnee.trivor.engine.models.Insight;
import com.kalnee.trivor.engine.models.Subtitle;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

public class MixedTensesInsightGenerator implements PostInsightGenerator<List<String>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(MixedTensesInsightGenerator.class);

	private static final List<String> INSIGHTS = Arrays.asList(
		"simple-present", "simple-past", "simple-future", "present-progressive", "past-progressive",
		"future-progressive", "present-perfect", "past-perfect", "non-sentences"
	);

	@Override
	public String getDescription() {
		return "Mixed Tenses";
	}

	@Override
	public String getCode() {
		return "mixed-tenses";
	}

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
