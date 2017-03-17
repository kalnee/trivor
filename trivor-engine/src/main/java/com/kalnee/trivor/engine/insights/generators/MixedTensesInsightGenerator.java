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

import static com.kalnee.trivor.engine.insights.generators.FutureProgressiveGenerator.FUTURE_PROGRESSIVE;
import static com.kalnee.trivor.engine.insights.generators.NonSentencesGenerator.NON_SENTENCES;
import static com.kalnee.trivor.engine.insights.generators.PastPerfectGenerator.PAST_PERFECT;
import static com.kalnee.trivor.engine.insights.generators.PastProgressiveGenerator.PAST_PROGRESSIVE;
import static com.kalnee.trivor.engine.insights.generators.PresentPerfectGenerator.PRESENT_PERFECT;
import static com.kalnee.trivor.engine.insights.generators.PresentProgressiveGenerator.PRESENT_PROGRESSIVE;
import static com.kalnee.trivor.engine.insights.generators.SimpleFutureGenerator.SIMPLE_FUTURE;
import static com.kalnee.trivor.engine.insights.generators.SimplePastGenerator.SIMPLE_PAST;
import static com.kalnee.trivor.engine.insights.generators.SimplePresentGenerator.SIMPLE_PRESENT;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

public class MixedTensesInsightGenerator implements PostInsightGenerator<List<String>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(MixedTensesInsightGenerator.class);

	private static final List<String> INSIGHTS = Arrays.asList(
		SIMPLE_PRESENT, SIMPLE_PAST, SIMPLE_FUTURE, PRESENT_PROGRESSIVE, PAST_PROGRESSIVE,
		FUTURE_PROGRESSIVE, PRESENT_PERFECT, PAST_PERFECT, NON_SENTENCES
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
