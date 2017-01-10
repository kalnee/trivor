package com.kalnee.trivor.engine.insights.processors;

import com.kalnee.trivor.engine.insights.generators.FrequentWordsInsightGenerator;
import com.kalnee.trivor.engine.insights.generators.InsightGenerator;
import com.kalnee.trivor.engine.insights.generators.PaceInsightGenerator;
import com.kalnee.trivor.engine.insights.generators.SentencesInsightGenerator;
import com.kalnee.trivor.engine.models.Insight;
import com.kalnee.trivor.engine.models.Insights;
import com.kalnee.trivor.engine.models.Subtitle;
import com.kalnee.trivor.engine.repositories.InsightsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Component
public class InsightsProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(InsightsProcessor.class);
    
	private final InsightsRepository repository;

	@Autowired
	public InsightsProcessor(InsightsRepository repository) {
		this.repository = repository;
	}

	public void process(Subtitle subtitle) {
		LOGGER.info("############# GENERATING INSIGHTS ###########");
		LOGGER.info("{} - S{}E{}", subtitle.getName(), subtitle.getSeason(), subtitle.getEpisode());
		LOGGER.info("Duration: {}min", subtitle.getDuration());

		final List<Insight> insights = Stream.of(
				new SentencesInsightGenerator(subtitle.getSentences()),
				new FrequentWordsInsightGenerator(subtitle.getSentences()),
				new PaceInsightGenerator(subtitle.getSentences(), subtitle.getDuration())
    ).peek(i -> {
					LOGGER.info("{} - {}", i.getCode(), i.getDescription());
					i.generate();
					LOGGER.info("Value generated: {}", i.getInsight().getValue());
    }).map(InsightGenerator::getInsight)
      .collect(toList());

    repository.save(new Insights(subtitle.getImdbId(), subtitle.getId(), insights));
    LOGGER.info("Insights created successfully.");
  }
}
