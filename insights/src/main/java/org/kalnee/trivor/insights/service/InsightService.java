package org.kalnee.trivor.insights.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.kalnee.trivor.insights.config.exception.NotFoundException;
import org.kalnee.trivor.insights.domain.Subtitle;
import org.kalnee.trivor.insights.domain.insights.GenericInsights;
import org.kalnee.trivor.insights.domain.insights.Insights;
import org.kalnee.trivor.insights.domain.insights.InsightsAggregated;
import org.kalnee.trivor.insights.repository.InsightsAggregatedRepository;
import org.kalnee.trivor.insights.repository.InsightsRepository;
import org.kalnee.trivor.insights.repository.SubtitleRepository;
import org.kalnee.trivor.nlp.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;
import static org.kalnee.trivor.nlp.domain.InsightsEnum.*;

@Service
public class InsightService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InsightService.class);

    private final InsightsRepository insightsRepository;
    private final InsightsAggregatedRepository aggInsightsRepository;
    private SubtitleRepository subtitleRepository;

    private final LoadingCache<String, Map<String, Object>> summaryCache = CacheBuilder.newBuilder()
        .build(
            new CacheLoader<String, Map<String, Object>>() {
                public Map<String, Object> load(String key) {
                    return getInsightsSummary(key);
                }
            });

    @Autowired
    public InsightService(InsightsRepository insightsRepository, SubtitleRepository subtitleRepository,
                          InsightsAggregatedRepository aggInsightsRepository) {
        this.insightsRepository = insightsRepository;
        this.subtitleRepository = subtitleRepository;
        this.aggInsightsRepository = aggInsightsRepository;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Integer> findVocabularyFrequencyByImdbId(String vocabulary, String imdbId, Integer limit) {
        final Comparator<Map.Entry<String, Integer>> comparator = limit == null || limit < 0
            ? Map.Entry.<String, Integer>comparingByValue()
            : Map.Entry.<String, Integer>comparingByValue().reversed();

        final InsightsAggregated aggInsights = aggInsightsRepository.findOneByImdbId(imdbId)
            .orElseThrow(NotFoundException::new);
        final Map<String, Integer> frequency = getVocabularyStream(vocabulary).apply(aggInsights.getVocabulary())
            .collect(toMap(WordUsage::getWord, a -> a.getSentences().size()));

        return frequency.entrySet().stream().sorted(comparator)
            .limit(limit != null ? Math.abs(limit) : Long.MAX_VALUE)
            .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v2, LinkedHashMap::new));
    }

    private Function<Vocabulary, Stream<WordUsage>> getVocabularyStream(String vocabulary) {
        switch (vocabulary) {
            case "nouns":
                return v -> v.getNouns().stream();
            case "verbs":
                return v -> v.getVerbs().stream();
            case "adjectives":
                return v -> v.getAdjectives().stream();
            case "adverbs":
                return v -> v.getAdverbs().stream();
            case "prepositions":
                return v -> v.getPrepositions().stream();
            case "modals":
                return v -> v.getModals().stream();
            case "comparatives":
                return v -> v.getComparatives().stream();
            case "superlatives":
                return v -> v.getSuperlatives().stream();
            default:
                throw new IllegalArgumentException("vocabulary not existent");
        }
    }

    @SuppressWarnings("unchecked")
    public List<WordUsage> findVocabularyUsageByImdbAndSeasonAndEpisode(String vocabulary, String imdbId, Integer season,
                                                                        Integer episode) {
        GenericInsights insights;

        if (season != null && episode != null) {
            final Subtitle subtitle = subtitleRepository.findOneByImdbIdAndSeasonAndEpisode(imdbId, season, episode)
                .orElseThrow(NotFoundException::new);
            insights = insightsRepository.findOneByImdbIdAndSubtitleId(imdbId, subtitle.getId());
        } else {
            insights = aggInsightsRepository.findOneByImdbId(imdbId).orElseThrow(NotFoundException::new);
        }

        return getVocabularyStream(vocabulary).apply(insights.getVocabulary())
            .sorted(Comparator.<WordUsage>comparingInt(o -> o.getSentences().size()).reversed())
            .collect(toList());
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

        final InsightsAggregated aggInsights = aggInsightsRepository.findOneByImdbId(imdbId)
            .orElseThrow(NotFoundException::new);

        summary.put(NUMBER_SENTENCES.getCode(), aggInsights.getNumberOfSentences());
        summary.put(RATE_OF_SPEECH.getCode(),
            aggInsights.getRateOfSpeech() != null ? aggInsights.getRateOfSpeech().getRate().name() : "NONE"
        );
        summary.put(SENTIMENT_ANALYSIS.getCode(), aggInsights.getSentimentAnalysis());

        summary.put(FREQUENCY_RATE.getCode(), aggInsights.getFrequencyRate().stream()
                .collect(toMap(FrequencyRate::getFrequency, f -> f.getWords().size())));

        summary.put(NOUNS_USAGE.getCode(), aggInsights.getVocabulary().getNouns().size());
        summary.put(VERBS_USAGE.getCode(), aggInsights.getVocabulary().getVerbs().size());
        summary.put(ADJECTIVES_USAGE.getCode(), aggInsights.getVocabulary().getAdjectives().size());
        summary.put(ADVERBS_USAGE.getCode(), aggInsights.getVocabulary().getAdverbs().size());
        summary.put(PREPOSITIONS_USAGE.getCode(), aggInsights.getVocabulary().getPrepositions().size());
        summary.put(MODALS_USAGE.getCode(), aggInsights.getVocabulary().getModals().size());
        summary.put(COMPARATIVES_USAGE.getCode(), aggInsights.getVocabulary().getComparatives().size());
        summary.put(SUPERLATIVES_USAGE.getCode(), aggInsights.getVocabulary().getSuperlatives().size());

        return summary;
    }

    public Map<String, Object> getInsightsSummaryCached(String imdbId) {
        try {
            return summaryCache.get(imdbId);
        } catch (ExecutionException e) {
            throw new IllegalStateException("an error occurred while fetching insights summary", e.getCause());
        }
    }

    public List<SentenceFrequency> findSentencesFrequencyByImdbId(String imdbId, Integer limit) {
        final Comparator<SentenceFrequency> comparator = limit == null || limit < 0
            ? comparing(SentenceFrequency::getFrequency)
            : comparing(SentenceFrequency::getFrequency).reversed();

        final InsightsAggregated aggInsights = aggInsightsRepository.findOneByImdbId(imdbId)
            .orElseThrow(NotFoundException::new);
        return aggInsights.getFrequentSentences().stream()
            .sorted(comparator)
            .limit(limit != null ? Math.abs(limit) : Long.MAX_VALUE)
            .collect(toList());
    }

    public List<ChunkFrequency> findChunksFrequencyByImdbId(String imdbId, Integer limit) {
        final Comparator<ChunkFrequency> comparator = limit == null || limit < 0
            ? comparing(ChunkFrequency::getFrequency)
            : comparing(ChunkFrequency::getFrequency).reversed();

        final InsightsAggregated aggInsights = aggInsightsRepository.findOneByImdbId(imdbId)
            .orElseThrow(NotFoundException::new);
        return aggInsights.getFrequentChunks().stream()
            .sorted(comparator)
            .limit(limit != null ? Math.abs(limit) : Long.MAX_VALUE)
            .collect(toList());
    }

    public List<PhrasalVerbUsage> findPhrasalVerbsUsageByImdbId(String imdbId) {
        final InsightsAggregated aggInsights = aggInsightsRepository.findOneByImdbId(imdbId)
            .orElseThrow(NotFoundException::new);
        return aggInsights.getPhrasalVerbs().stream()
            .sorted((p1, p2) -> p2.getSentences().size() - p1.getSentences().size())
            .collect(toList());
    }
}
