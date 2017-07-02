package com.kalnee.trivor.insights.services;

import com.kalnee.trivor.insights.dto.SubtitleDTO;
import com.kalnee.trivor.insights.models.Insights;
import com.kalnee.trivor.insights.models.Subtitle;
import com.kalnee.trivor.insights.repositories.InsightsRepository;
import com.kalnee.trivor.insights.repositories.SubtitleRepository;
import com.kalnee.trivor.sdk.insights.processors.SubtitleProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class SubtitleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubtitleService.class);

    private final SubtitleRepository subtitleRepository;
    private final InsightsRepository insightsRepository;

    @Autowired
    public SubtitleService(SubtitleRepository subtitleRepository,
                           InsightsRepository insightsRepository) {
        this.subtitleRepository = subtitleRepository;
        this.insightsRepository = insightsRepository;
    }

    public void process(URI uri, SubtitleDTO subtitleDTO) {
        final SubtitleProcessor subtitleProcessor = new SubtitleProcessor.Builder(uri)
                .withDuration(subtitleDTO.getDuration())
                .addInsightGenerators(new FirstSentenceGenerator(), new SecondSentenceGenerator())
                .addPostInsightGenerators(new LengthPostInsightGenerator())
                .build();

        final Subtitle subtitle = subtitleRepository.save(
                new Subtitle(subtitleDTO, subtitleProcessor.getSubtitle().getSentences())
        );

        LOGGER.info("Subtitle created successfully.");

        insightsRepository.save(
                new Insights(subtitle.getImdbId(), subtitle.getId(), subtitleProcessor.getInsights())
        );

        LOGGER.info("Insights created successfully.");
    }
}
