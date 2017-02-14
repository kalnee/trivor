package com.kalnee.trivor.engine.insights.generators;

import com.kalnee.trivor.engine.dto.TypeEnum;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.kalnee.trivor.engine.dto.TypeEnum.TV_SHOW;

@Component
public class InsightsGenerators {

	private static final List<InsightGenerator> TV_SHOW_GENERATORS = Arrays.asList(
		new NumberOfSentencesGenerator(),
		new FrequentWordsGenerator(),
		new FrequentSentencesGenerator(),
		new PaceInsightGenerator(),
		new SimplePresentGenerator(),
		new SimplePastGenerator(),
		new SimpleFutureGenerator(),
		new PresentPerfectGenerator(),
		new PresentProgressiveGenerator(),
		new PastProgressiveGenerator(),
		new PastPerfectGenerator(),
		new NonSentencesGenerator()
	);

	private static final List<InsightGenerator> MOVIE_GENERATORS = Arrays.asList(
		new NumberOfSentencesGenerator(),
		new FrequentWordsGenerator(),
		new FrequentSentencesGenerator(),
		new PaceInsightGenerator(),
		new SimplePresentGenerator(),
		new SimplePastGenerator(),
		new SimpleFutureGenerator(),
		new PresentPerfectGenerator(),
		new PresentProgressiveGenerator(),
		new PastProgressiveGenerator(),
		new PastPerfectGenerator(),
		new NonSentencesGenerator()
	);

	public List<InsightGenerator> getGenerators(TypeEnum type) {
		if (TV_SHOW.equals(type)) {
			return TV_SHOW_GENERATORS;
		} else {
			return MOVIE_GENERATORS;
		}
  }
}
