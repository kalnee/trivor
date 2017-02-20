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
		new PresentProgressiveGenerator(),
		new PastProgressiveGenerator(),
		new FutureProgressiveGenerator(),
		new PresentPerfectGenerator(),
		new PastPerfectGenerator(),
		new FuturePerfectGenerator(),
		new NonSentencesGenerator()
	);

	private static final List<PostInsightGenerator> TV_SHOW_POST_GENERATORS = Arrays.asList(
		new MixedTensesInsightGenerator()
	);

	private static final List<InsightGenerator> MOVIE_GENERATORS = Arrays.asList(
		new NumberOfSentencesGenerator(),
		new FrequentWordsGenerator(),
		new FrequentSentencesGenerator(),
		new PaceInsightGenerator(),
		new SimplePresentGenerator(),
		new SimplePastGenerator(),
		new SimpleFutureGenerator(),
		new PresentProgressiveGenerator(),
		new PastProgressiveGenerator(),
		new FutureProgressiveGenerator(),
		new PresentPerfectGenerator(),
		new PastPerfectGenerator(),
		new FuturePerfectGenerator(),
		new NonSentencesGenerator()
	);

	private static final List<PostInsightGenerator> MOVIE_POST_GENERATORS = Arrays.asList(
		new MixedTensesInsightGenerator()
	);

	public List<InsightGenerator> getGenerators(TypeEnum type) {
		if (TV_SHOW.equals(type)) {
			return TV_SHOW_GENERATORS;
		} else {
			return MOVIE_GENERATORS;
		}
  }

	public List<PostInsightGenerator> getPostGenerators(TypeEnum type) {
		if (TV_SHOW.equals(type)) {
			return TV_SHOW_POST_GENERATORS;
		} else {
			return MOVIE_POST_GENERATORS;
		}
	}
}
