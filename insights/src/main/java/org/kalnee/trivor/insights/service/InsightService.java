package org.kalnee.trivor.insights.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.kalnee.trivor.insights.domain.Insights;
import org.kalnee.trivor.insights.domain.Subtitle;
import org.kalnee.trivor.insights.repository.InsightsRepository;
import org.kalnee.trivor.insights.repository.SubtitleRepository;
import org.kalnee.trivor.nlp.domain.FrequencyEnum;
import org.kalnee.trivor.nlp.domain.WordUsage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

import static java.util.stream.Collectors.*;

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
    public Map<String, Integer> findVerbFrequencyByImdbId(String imdbId, Integer limit) {
        final Comparator<Map.Entry<String, Integer>> comparator = limit == null || limit < 0
            ? Map.Entry.<String, Integer>comparingByValue()
            : Map.Entry.<String, Integer>comparingByValue().reversed();
        final Map<String, Integer> allInsights = new HashMap<>();

        insightsRepository.findAllByImdbId(imdbId).stream()
                .flatMap(i -> i.getVocabulary().getVerbs().stream())
                .forEach(i -> allInsights.put(i.getWord(), i.getSentences().size() + allInsights.getOrDefault(i.getWord(), 0)));

        return allInsights.entrySet().stream().sorted(comparator)
            .limit(limit != null ? Math.abs(limit) : Long.MAX_VALUE)
            .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v2, LinkedHashMap::new));
    }

    @SuppressWarnings("unchecked")
    public List<WordUsage> findVerbUsageByImdbAndSeasonAndEpisode(String imdbId,
                                                                            Integer season, Integer episode) {
        Insights insights;

        if (season != null && episode != null) {
            final Subtitle subtitle = subtitleRepository.findOneByImdbIdAndSeasonAndEpisode(imdbId, season, episode)
                .orElseThrow(() -> new IllegalStateException("subtitle not found"));
            insights = insightsRepository.findOneByImdbIdAndSubtitleId(imdbId, subtitle.getId());
        } else {
            insights = insightsRepository.findOneByImdbId(imdbId);
        }

        return insights.getVocabulary().getVerbs().stream()
            .sorted(Comparator.<WordUsage>comparingInt(o -> o.getSentences().size()).reversed())
            .collect(toList());
    }

    @SuppressWarnings("unchecked")
    public Set<String> findVerbTensesByInsightAndImdb(String imdbId) {
        return insightsRepository.findAllByImdbId(imdbId).stream()
            .flatMap(i -> i.getVerbTenses().getSimplePresent().stream())
            .collect(toSet());
    }

    public List<Insights> findInsightsByInsightAndGenre(String genre) {
        final List<String> imdbIds = subtitleRepository.findByGenres(genre).stream()
                .map(Subtitle::getImdbId)
                .collect(toList());
        return insightsRepository.findByImdbIdIn(imdbIds);
    }

    public List<Insights> findInsightsByInsightAndKeyword(String keyword) {
        final List<String> imdbIds = subtitleRepository.findByKeywords(keyword).stream()
                .map(Subtitle::getImdbId)
                .collect(toList());
        return insightsRepository.findByImdbIdIn(imdbIds);
    }

    public Page<Insights> findByImdbId(String imdbId, Pageable pageable) {
        return insightsRepository.findByImdbId(imdbId, pageable);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getInsightsSummary(String imdbId) {
        final Map<String, Object> summary = new HashMap<>();
        final Map<String, Set<String>> keys = new HashMap<>();
        final Map<String, Integer> rateOfSpeech = new HashMap<>();
        final Map<FrequencyEnum, Integer> frequencyRate = new HashMap<>();

//        insightsRepository.findAllByImdbId(imdbId).forEach(i -> {
//                if (i.getCode().endsWith("-usage")) {
//                    final Set<String> words = new HashSet<>(keys.getOrDefault(i.getCode(), emptySet()));
//                    final Set<String> current = ((List<WordUsage>) i.getValue()).stream()
//                        .map(WordUsage::getWord)
//                        .collect(toSet());
//                    words.addAll(current);
//                    keys.put(i.getCode(), words);
//                    summary.put(i.getCode(), keys.get(i.getCode()).size());
//                } else if (i.getCode().equals(NUMBER_SENTENCES.getCode())) {
//                    int currentValue = (Integer) i.getValue();
//                    summary.put(i.getCode(), currentValue + ((Integer)summary.getOrDefault(i.getCode(), 0)));
//                } else if (i.getCode().equals(RATE_OF_SPEECH.getCode())) {
//                    String rate = ((RateOfSpeech)i.getValue()).getRate().name();
//                    rateOfSpeech.put(rate, rateOfSpeech.getOrDefault(rate, 0) + 1);
//                } else if (i.getCode().equals(FREQUENCY_RATE.getCode())) {
//                    final List<FrequencyRate> frequency = ((List<FrequencyRate>)i.getValue());
//                    frequency.forEach(f -> {
//                        frequencyRate.put(
//                            f.getFrequency(), frequencyRate.getOrDefault(f.getFrequency(), 0) + f.getWords().size()
//                        );
//                    });
//                } else if (i.getValue() instanceof Collection) {
//                    int currentSize = ((Collection) i.getValue()).size();
//                    summary.put(i.getCode(), currentSize + ((Integer)summary.getOrDefault(i.getCode(), 0)));
//                } else {
//                    summary.put(i.getCode(), i.getValue());
//                }
//            });
//
//        summary.put(
//            RATE_OF_SPEECH.getCode(), rateOfSpeech.entrySet().stream()
//                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
//                .map(Map.Entry::getKey).findFirst().get()
//        );
//        summary.put(FREQUENCY_RATE.getCode(), frequencyRate);

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
