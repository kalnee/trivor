package com.kalnee.trivor.engine.insights.processors;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.kalnee.trivor.engine.models.Sentence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalnee.trivor.engine.insights.generators.InsightsGenerators;
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
		LOGGER.info("{} - S{}E{}", subtitle.getName(), subtitle.getSeason(), subtitle.getEpisode());
		LOGGER.info("Duration: {}min", subtitle.getDuration());

		final List<Insight> insights = insightsGenerators.getGenerators(subtitle.getType())
			.stream()
			.map(i -> i.getInsight(subtitle))
			.collect(toList());

		// TODO Remove
		List<String> all = insights.stream()
			.filter(i -> i.getValue() instanceof List)
			.flatMap(i -> ((List<String>) i.getValue()).stream())
			.collect(Collectors.toList());

		List<String> notIdentified = subtitle.getSentences().stream()
			.filter(s -> !all.contains(s.getSentence()))
			.map(Sentence::getSentence)
			//.peek(s -> LOGGER.info("\n{}\n{}\n", s.getSentence(), s.getSentenceTags()))
			.collect(Collectors.toList());

		Insight<List<String>> notIdentifiedInsight = new Insight<>();
		notIdentifiedInsight.setCode("not-identified");
		notIdentifiedInsight.setValue(notIdentified);
		insights.add(notIdentifiedInsight);

		LOGGER.info(
			format("not-identified: %d/%d (%.2f%%)", notIdentified.size(), subtitle.getSentences().size(),
				(notIdentified.size() * 100d / subtitle.getSentences().size()))
		);
		LOGGER.info("total: {}/{}", all.size() + notIdentified.size(), subtitle.getSentences().size());

    repository.save(new Insights(subtitle.getImdbId(), subtitle.getId(), insights));
    LOGGER.info("Insights created successfully.");
  }
}
