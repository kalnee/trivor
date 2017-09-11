package org.kalnee.trivor.insights.service;

import org.kalnee.trivor.insights.domain.Insights;
import org.kalnee.trivor.insights.domain.Subtitle;
import org.kalnee.trivor.insights.domain.dto.SubtitleDTO;
import org.kalnee.trivor.insights.repository.InsightsRepository;
import org.kalnee.trivor.insights.repository.SubtitleRepository;
import org.kalnee.trivor.nlp.insights.processors.SubtitleProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

import static org.kalnee.trivor.insights.domain.dto.TypeEnum.TV_SHOW;

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
            final List<Subtitle> subtitles = subtitleRepository.findByImdbIdAndSeasonAndEpisode(
                    subtitleDTO.getImdbId(), subtitleDTO.getSeason(), subtitleDTO.getEpisode()
            );
            subtitles.forEach(s -> insightsRepository.delete(
                    insightsRepository.findByImdbIdAndSubtitleId(s.getImdbId(), s.getId())
            ));
            subtitleRepository.delete(subtitles);
        } else {
            subtitleRepository.delete(subtitleRepository.findByImdbId(subtitleDTO.getImdbId()));
            insightsRepository.delete(insightsRepository.findAllByImdbId(subtitleDTO.getImdbId()));
        }
    }
}
