package org.kalnee.trivor.insights.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.kalnee.trivor.insights.domain.Insights;
import org.kalnee.trivor.insights.domain.Subtitle;
import org.kalnee.trivor.insights.domain.dto.SubtitleDTO;
import org.kalnee.trivor.insights.domain.dto.TVShowDTO;
import org.kalnee.trivor.insights.repository.InsightsRepository;
import org.kalnee.trivor.insights.repository.SubtitleRepository;
import org.kalnee.trivor.nlp.insights.processors.SubtitleProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static org.kalnee.trivor.insights.domain.dto.TypeEnum.TV_SHOW;

@Service
public class SubtitleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubtitleService.class);

    private final SubtitleRepository subtitleRepository;
    private final InsightsRepository insightsRepository;

    private final LoadingCache<String, TVShowDTO> tvShowCache = CacheBuilder.newBuilder()
        .build(
            new CacheLoader<String, TVShowDTO>() {
                public TVShowDTO load(String key) {
                    return findTvShowByImdb(key);
                }
            });

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

        insightsRepository.save(new Insights(subtitle.getImdbId(), subtitle.getId(), subtitleProcessor.getResult()));

        LOGGER.info("Insights created successfully.");
    }

    private void deleteIfExists(SubtitleDTO subtitleDTO) {
        if (TV_SHOW == subtitleDTO.getType()) {
            final List<Subtitle> subtitles = subtitleRepository.findByImdbIdAndSeasonAndEpisode(
                    subtitleDTO.getImdbId(), subtitleDTO.getSeason(), subtitleDTO.getEpisode()
            );
            subtitles.forEach(s -> insightsRepository.delete(
                    insightsRepository.findAllByImdbIdAndSubtitleId(s.getImdbId(), s.getId())
            ));
            subtitleRepository.delete(subtitles);
        } else {
            subtitleRepository.delete(subtitleRepository.findByImdbId(subtitleDTO.getImdbId()));
            insightsRepository.delete(insightsRepository.findAllByImdbId(subtitleDTO.getImdbId()));
        }
    }

    public TVShowDTO findTvShowByImdb(String imdbId) {
        final Map<Integer, Set<Integer>> map = subtitleRepository.findByImdbId(imdbId).stream()
            .map(s -> new AbstractMap.SimpleEntry<>(s.getSeason(), s.getEpisode()))
            .collect(
                groupingBy(AbstractMap.SimpleEntry::getKey,
                    mapping(AbstractMap.SimpleEntry::getValue, Collectors.toSet())
                )
            );
        return new TVShowDTO(map);
    }

    public TVShowDTO findTvShowByImdbCached(String imdbId) {
        try {
            return tvShowCache.get(imdbId);
        } catch (ExecutionException e) {
            throw new IllegalStateException("an error occurred while fetching tv show metadata", e.getCause());
        }
    }
}
