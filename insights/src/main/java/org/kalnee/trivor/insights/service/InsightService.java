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
import static java.util.stream.Collectors.*;
import static org.kalnee.trivor.nlp.nlp.models.FrequencyEnum.*;
import static org.kalnee.trivor.nlp.nlp.models.InsightsEnum.*;

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
    public Map<String, Integer> findFrequencyByInsightAndImdbId(String insight, String imdbId) {
        final Map<String, Integer> allInsights = new HashMap<>();

        insightsRepository.findAllByImdbId(imdbId).stream()
                .flatMap(i -> i.getInsights().entrySet().stream())
                .filter(i -> i.getKey().equals(insight))
                .flatMap(i -> ((LinkedHashMap<String, Integer>) i.getValue()).entrySet().stream())
                .forEach(i -> allInsights.put(i.getKey(), i.getValue() + allInsights.getOrDefault(i.getKey(), 0)));

        return allInsights.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> {
                    throw new RuntimeException(format("Duplicate key for values %s and %s", v1, v2));
                }, LinkedHashMap::new));
    }

    @SuppressWarnings("unchecked")
    public Map<String, Integer> findTopFrequencyByInsightAndImdbId(String insight, String imdbId, Integer limit) {
        final Map<String, Integer> allInsights = new HashMap<>();

        insightsRepository.findAllByImdbId(imdbId).stream()
            .flatMap(i -> i.getInsights().entrySet().stream())
            .filter(i -> i.getKey().equals(insight))
            .flatMap(i -> ((LinkedHashMap<String, Integer>) i.getValue()).entrySet().stream())
            .forEach(i -> allInsights.put(i.getKey(), i.getValue() + allInsights.getOrDefault(i.getKey(), 0)));

        final Comparator<Map.Entry<String, Integer>> comparator = limit < 0
            ? Map.Entry.<String, Integer>comparingByValue()
            : Map.Entry.<String, Integer>comparingByValue().reversed();
        return allInsights.entrySet().stream().sorted(comparator)
            .limit(Math.abs(limit))
            .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v2, LinkedHashMap::new));
    }

    @SuppressWarnings("unchecked")
    public Map<String, List<String>> findSentencesByInsightAndImdbAndSeasonAndEpisode(String insight, String imdbId,
                                                                                      Integer season, Integer episode) {
        Insights insights;

        if (season != null && episode != null) {
            final Subtitle subtitle = subtitleRepository.findOneByImdbIdAndSeasonAndEpisode(imdbId, season, episode)
                .orElseThrow(() -> new IllegalStateException("subtitle not found"));
            insights = insightsRepository.findOneByImdbIdAndSubtitleId(imdbId, subtitle.getId());
        } else {
            insights = insightsRepository.findOneByImdbId(imdbId);
        }

        return insights.getInsights()
            .entrySet().stream()
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

    @SuppressWarnings("unchecked")
    public Map<String, Object> getInsightsSummary(String imdbId) {
        final Map<String, Object> summary = new HashMap<>();
        final Map<String, Set<String>> keys = new HashMap<>();
        final Map<String, Integer> rateOfSpeech = new HashMap<>();
        final Map<String, Integer> frequencyRate = new HashMap<>();

        insightsRepository.findAllByImdbId(imdbId).stream()
            .flatMap(i -> i.getInsights().entrySet().stream())
            .filter(i -> !i.getKey().endsWith("-sentences")
                || i.getKey().equals(NON_SENTENCES.getCode())
                || i.getKey().equals(NUM_SENTENCES.getCode()))
            .forEach(entry -> {
                if (entry.getKey().endsWith("-frequency")) {
                    final Set<String> words = new HashSet<>(keys.getOrDefault(entry.getKey(), Collections.emptySet()));
                    final Set<String> current = ((Map<String, Integer>) entry.getValue()).entrySet()
                        .stream().map(Map.Entry::getKey)
                        .collect(toSet());
                    final Set<String> newSet = new HashSet<>();

                    newSet.addAll(words);
                    newSet.addAll(current);

                    keys.put(entry.getKey(), newSet);
                    summary.put(entry.getKey(), keys.get(entry.getKey()).size());
                } else if (entry.getKey().equals(NUM_SENTENCES.getCode())) {
                    int currentValue = (Integer) entry.getValue();
                    summary.put(entry.getKey(), currentValue + ((Integer)summary.getOrDefault(entry.getKey(), 0)));
                } else if (entry.getKey().equals(RATE_OF_SPEECH.getCode())) {
                    rateOfSpeech.put(
                        entry.getValue().toString(), rateOfSpeech.getOrDefault(entry.getValue().toString(), 0) + 1
                    );
                } else if (entry.getKey().equals(FREQUENCY_RATE.getCode())) {
                    final Map<String, Integer> frequencyMap = ((Map<String, Integer>)entry.getValue());
                    frequencyRate.put(
                        HIGH.name(), frequencyRate.getOrDefault(HIGH.name(), 0) + frequencyMap.get(HIGH.name())
                    );
                    frequencyRate.put(
                        MIDDLE.name(), frequencyRate.getOrDefault(MIDDLE.name(), 0) + frequencyMap.get(MIDDLE.name())
                    );
                    frequencyRate.put(
                        LOW.name(), frequencyRate.getOrDefault(LOW.name(), 0) + frequencyMap.get(LOW.name())
                    );
                } else if (entry.getValue() instanceof Collection) {
                    int currentSize = ((Collection) entry.getValue()).size();
                    summary.put(entry.getKey(), currentSize + ((Integer)summary.getOrDefault(entry.getKey(), 0)));
                } else {
                    summary.put(entry.getKey(), entry.getValue());
                }
            });

        summary.put(
            RATE_OF_SPEECH.getCode(), rateOfSpeech.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey).findFirst().get()
        );
        summary.put(FREQUENCY_RATE.getCode(), frequencyRate);

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
