package org.kalnee.trivor.insights.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
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

import java.util.*;
import java.util.concurrent.ExecutionException;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

@Service
public class InsightService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InsightService.class);

    private final InsightsRepository insightsRepository;
    private SubtitleRepository subtitleRepository;

    private final LoadingCache<String, Map<String, Object>> summaryCache = CacheBuilder.newBuilder()
        .build(
            new CacheLoader<String, Map<String, Object>>() {
                public Map<String, Object> load(String key) {
                    return getInsightsSummary(key);
                }
            });

    @Autowired
    public InsightService(InsightsRepository insightsRepository, SubtitleRepository subtitleRepository) {
        this.insightsRepository = insightsRepository;
        this.subtitleRepository = subtitleRepository;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Integer> findFrequencyByInsightAndImdb(String insight, String imdbId) {
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

    @SuppressWarnings("unchecked")
    public Map<String, List<String>> findSentencesByInsightAndImdb(String insight, String imdbId) {
        return insightsRepository.findAllByImdbId(imdbId).stream()
            .flatMap(i -> i.getInsights().entrySet().stream())
            .filter(i -> i.getKey().equals(insight))
            .flatMap(i -> ((LinkedHashMap<String, List<String>>) i.getValue()).entrySet().stream())
            .sorted(Comparator.<Map.Entry<String, List<String>>>comparingInt(o -> o.getValue().size()).reversed())
            .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v2, LinkedHashMap::new));
    }

    @SuppressWarnings("unchecked")
    public Set<String> findVerbTensesByInsightAndImdb(String insight, String imdbId) {
        return insightsRepository.findAllByImdbId(imdbId).stream()
            .flatMap(i -> i.getInsights().entrySet().stream())
            .filter(i -> i.getKey().equals(insight))
            .flatMap(i -> ((List<String>) i.getValue()).stream())
            .collect(toSet());
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

    private Map<String, Object> getInsightsSummary(String imdbId) {
        final Map<String, Object> summary = new HashMap<>();
        insightsRepository.findAllByImdbId(imdbId).stream()
            .flatMap(i -> i.getInsights().entrySet().stream())
            .forEach(entry -> {
                if (entry.getValue() instanceof Map) {
                    summary.put(entry.getKey(), ((Map) entry.getValue()).values().size());
                } else if (entry.getValue() instanceof Collection) {
                    summary.put(entry.getKey(), ((Collection) entry.getValue()).size());
                } else {
                    summary.put(entry.getKey(), entry.getValue());
                }
            });
        return summary;
    }

    public Map<String, Object> getInsightsSummaryCached(String imdbId) {
        try {
            return summaryCache.get(imdbId);
        } catch (ExecutionException e) {
            throw new IllegalStateException("an error occurred while fetching insights summary", e.getCause());
        }
    }
}
