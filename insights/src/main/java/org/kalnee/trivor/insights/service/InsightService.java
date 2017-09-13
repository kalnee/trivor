package org.kalnee.trivor.insights.service;

import org.kalnee.trivor.insights.domain.Insights;
import org.kalnee.trivor.insights.domain.Subtitle;
import org.kalnee.trivor.insights.repository.InsightsRepository;
import org.kalnee.trivor.insights.repository.SubtitleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Service
public class InsightService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InsightService.class);

    private final InsightsRepository insightsRepository;
    private SubtitleRepository subtitleRepository;

    @Autowired
    public InsightService(InsightsRepository insightsRepository, SubtitleRepository subtitleRepository) {
        this.insightsRepository = insightsRepository;
        this.subtitleRepository = subtitleRepository;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Integer> findByInsightAndImdb(String insight, String imdbId) {
        final Map<String, Integer> allInsights = new HashMap<>();

        insightsRepository.findAllByImdbId(imdbId).stream().flatMap(i -> i.getInsights().entrySet().stream())
                .filter(i -> i.getKey().equals(insight))
                .flatMap(i -> ((LinkedHashMap<String, Integer>) i.getValue()).entrySet().stream())
                .forEach(i -> allInsights.put(i.getKey(), i.getValue() + allInsights.getOrDefault(i.getKey(), 0)));

        return allInsights.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .filter(e -> e.getValue() > 1)
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> {
                    throw new RuntimeException(format("Duplicate key for values %s and %s", v1, v2));
                }, LinkedHashMap::new));
    }

    public List<Object> findInsightsByInsightAndGenre(String insight, String genre) {
        final List<String> imdbIds = subtitleRepository.findByGenres(genre).stream()
                .map(Subtitle::getImdbId)
                .collect(toList());
        return insightsRepository.findByImdbIdIn(imdbIds).stream()
                .map(i -> i.getInsights().get(insight))
                .collect(toList());
    }

    public List<Object> findInsightsByInsightAndKeyword(String insight, String keyword) {
        final List<String> imdbIds = subtitleRepository.findByKeywords(keyword).stream()
                .map(Subtitle::getImdbId)
                .collect(toList());
        return insightsRepository.findByImdbIdIn(imdbIds).stream()
                .map(i -> i.getInsights().get(insight))
                .collect(toList());
    }

    public Page<Insights> findByImdbId(String imdbId, Pageable pageable) {
        return insightsRepository.findByImdbId(imdbId, pageable);
    }
}
