package org.kalnee.trivor.insights.services;

import org.kalnee.trivor.insights.dto.SubtitleDTO;
import org.kalnee.trivor.insights.models.Insights;
import org.kalnee.trivor.insights.models.Subtitle;
import org.kalnee.trivor.insights.repositories.InsightsRepository;
import org.kalnee.trivor.insights.repositories.SubtitleRepository;
import org.kalnee.trivor.nlp.insights.processors.SubtitleProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;

import static org.kalnee.trivor.insights.dto.TypeEnum.TV_SHOW;

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
        deleteIfExists(subtitleDTO);

        final SubtitleProcessor subtitleProcessor = new SubtitleProcessor.Builder(uri)
                .withDuration(subtitleDTO.getDuration())
                .build();

        final Subtitle subtitle = subtitleRepository.save(
                new Subtitle(subtitleDTO, subtitleProcessor.getSubtitle().getSentences(),
                        subtitleProcessor.getSubtitle().getSentiment())
        );

        LOGGER.info("Subtitle created successfully.");

        insightsRepository.save(
                new Insights(subtitle.getImdbId(), subtitle.getId(), subtitleProcessor.getInsights())
        );

        LOGGER.info("Insights created successfully.");
    }

    private void deleteIfExists(SubtitleDTO subtitleDTO) {
        if (TV_SHOW == subtitleDTO.getType()) {
            subtitleRepository.delete(
                    subtitleRepository.findByImdbIdAndSeasonAndEpisode(
                    subtitleDTO.getImdbId(), subtitleDTO.getSeason(), subtitleDTO.getEpisode())
            );
        } else {
            subtitleRepository.delete(subtitleRepository.findByImdbId(subtitleDTO.getImdbId()));
        }
    }
}
