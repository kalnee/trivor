package com.kalnee.trivor.engine.insights.generators;

import static com.kalnee.trivor.engine.dto.TypeEnum.TV_SHOW;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.kalnee.trivor.engine.dto.TypeEnum;

@Component
public class InsightsGenerators {

	private static final List<InsightGenerator> TV_SHOW_GENERATORS = Arrays.asList(
		new SentencesInsightGenerator(),
		new FrequentWordsInsightGenerator(),
		new FrequentSentencesInsightGenerator(),
		new PaceInsightGenerator(),
		new SimplePresentGenerator(),
		new SimplePastGenerator(),
		new SimpleFutureGenerator(),
		new PresentPerfectGenerator()
	);

	private static final List<InsightGenerator> MOVIE_GENERATORS = Arrays.asList(
		new SentencesInsightGenerator(),
		new FrequentWordsInsightGenerator(),
		new PaceInsightGenerator()
	);

	public List<InsightGenerator> getGenerators(TypeEnum type) {
		if (TV_SHOW.equals(type)) {
			return TV_SHOW_GENERATORS;
		} else {
			return MOVIE_GENERATORS;
		}
  }
}
