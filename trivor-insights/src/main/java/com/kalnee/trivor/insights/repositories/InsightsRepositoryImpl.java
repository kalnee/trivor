package com.kalnee.trivor.insights.repositories;

import com.kalnee.trivor.insights.models.Subtitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class InsightsRepositoryImpl implements InsightsRepositoryCustom {

    private static final Logger LOGGER = LoggerFactory.getLogger(InsightsRepositoryImpl.class);

    @Autowired
    private SubtitleRepository subtitleRepository;
    @Autowired
    private InsightsRepository insightsRepository;

    public InsightsRepositoryImpl() {
    }

    @Override
    public List<Object> findInsightsByCodeAndGenre(String code, String genre) {
        final List<String> imdbIds = subtitleRepository.findByGenres(genre).stream()
                .map(Subtitle::getImdbId)
                .collect(toList());
        LOGGER.info("############### IMDBIDS: " + imdbIds);
        final List<Object> insights = insightsRepository.findByImdbIdIn(imdbIds).stream()
                .map(i -> i.getInsights().get(code))
                .collect(toList());
        LOGGER.info("############### INSIGHTS: " + insights);
        return insights;
    }
}
