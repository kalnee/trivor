package com.kalnee.trivor.engine.insights.processors;

import static com.kalnee.trivor.engine.dto.TypeEnum.TV_SHOW;
import static java.util.stream.Collectors.toList;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalnee.trivor.engine.insights.generators.InsightsGenerators;
import com.kalnee.trivor.engine.insights.generators.MixedTensesInsightGenerator;
import com.kalnee.trivor.engine.models.Insight;
import com.kalnee.trivor.engine.models.Insights;
import com.kalnee.trivor.engine.models.Subtitle;
import com.kalnee.trivor.engine.repositories.InsightsRepository;

@Component
public class InsightsProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(InsightsProcessor.class);
    
	private final InsightsRepository repository;
	private final InsightsGenerators insightsGenerators;

	@Autowired
	public InsightsProcessor(InsightsRepository repository, InsightsGenerators insightsGenerators) {
		this.repository = repository;
		this.insightsGenerators = insightsGenerators;
	}

	public void process(Subtitle subtitle) {
		LOGGER.info("############# GENERATING INSIGHTS ###########");
		LOGGER.info("{}", subtitle.getName());
		if (TV_SHOW.equals(subtitle.getType())) {
			LOGGER.info("S{}E{}", subtitle.getSeason(), subtitle.getEpisode());
		}
		LOGGER.info("Duration: {}min", subtitle.getDuration());

		final List<Insight> insights = insightsGenerators.getGenerators(subtitle.getType())
			.stream()
			.map(i -> i.getInsight(subtitle))
			.collect(toList());

		final List<Insight> postInsights = insightsGenerators.getPostGenerators(subtitle.getType())
			.stream()
			.map(i -> i.getInsight(subtitle, insights))
			.collect(toList());

		insights.addAll(postInsights);

    repository.save(new Insights(subtitle.getImdbId(), subtitle.getId(), insights));
    LOGGER.info("Insights created successfully.");
  }
}
