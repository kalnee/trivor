package org.kalnee.trivor.insights.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.kalnee.trivor.insights.config.exception.NotFoundException;
import org.kalnee.trivor.insights.domain.insights.InsightsAggregated;
import org.kalnee.trivor.insights.domain.insights.Insights;
import org.kalnee.trivor.insights.domain.Subtitle;
import org.kalnee.trivor.insights.repository.InsightsAggregatedRepository;
import org.kalnee.trivor.insights.repository.InsightsRepository;
import org.kalnee.trivor.insights.repository.SubtitleRepository;
import org.kalnee.trivor.nlp.domain.FrequencyRate;
import org.kalnee.trivor.nlp.domain.VerbTenses;
import org.kalnee.trivor.nlp.domain.Vocabulary;
import org.kalnee.trivor.nlp.domain.WordUsage;
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
        Insights insights;

        if (season != null && episode != null) {
            final Subtitle subtitle = subtitleRepository.findOneByImdbIdAndSeasonAndEpisode(imdbId, season, episode)
                .orElseThrow(() -> new IllegalStateException("subtitle not found"));
            insights = insightsRepository.findOneByImdbIdAndSubtitleId(imdbId, subtitle.getId());
        } else {
            insights = insightsRepository.findOneByImdbId(imdbId);
        }

        return getVocabularyStream(vocabulary).apply(insights.getVocabulary())
            .sorted(Comparator.<WordUsage>comparingInt(o -> o.getSentences().size()).reversed())
            .collect(toList());
    }

    @SuppressWarnings("unchecked")
    public Set<String> findVerbTensesUsageByImdbId(String tense, String imdbId) {
        return insightsRepository.findAllByImdbId(imdbId).stream()
            .flatMap(i -> getVerbTenseStream(tense).apply(i.getVerbTenses()))
            .collect(toSet());
    }

    private Function<VerbTenses, Stream<String>> getVerbTenseStream(String tense) {
        switch (tense) {
            case "simple-present":
                return v -> v.getSimplePresent().stream();
            case "simple-past":
                return v -> v.getSimplePast().stream();
            case "simple-future":
                return v -> v.getSimpleFuture().stream();
            case "present-progressive":
                return v -> v.getPresentProgressive().stream();
            case "past-progressive":
                return v -> v.getPastProgressive().stream();
            case "future-progressive":
                return v -> v.getFutureProgressive().stream();
            case "present-perfect":
                return v -> v.getPresentPerfect().stream();
            case "past-perfect":
                return v -> v.getPastPerfect().stream();
            case "future-perfect":
                return v -> v.getFuturePerfect().stream();
            case "non-sentences":
                return v -> v.getNonSentences().stream();
            case "mixed-tenses":
                return v -> v.getMixedTenses().stream();
            default:
                throw new IllegalArgumentException("verb tense not existent");
        }
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
        summary.put(RATE_OF_SPEECH.getCode(), aggInsights.getRateOfSpeech().getRate().name());
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

        summary.put(SIMPLE_PRESENT.getCode(), aggInsights.getVerbTenses().getSimplePresent().size());
        summary.put(SIMPLE_PAST.getCode(), aggInsights.getVerbTenses().getSimplePast().size());
        summary.put(SIMPLE_FUTURE.getCode(), aggInsights.getVerbTenses().getSimpleFuture().size());
        summary.put(PRESENT_PROGRESSIVE.getCode(), aggInsights.getVerbTenses().getPresentProgressive().size());
        summary.put(PAST_PROGRESSIVE.getCode(), aggInsights.getVerbTenses().getPastProgressive().size());
        summary.put(FUTURE_PROGRESSIVE.getCode(), aggInsights.getVerbTenses().getFutureProgressive().size());
        summary.put(PRESENT_PERFECT.getCode(), aggInsights.getVerbTenses().getPresentPerfect().size());
        summary.put(PAST_PERFECT.getCode(), aggInsights.getVerbTenses().getPastPerfect().size());
        summary.put(FUTURE_PERFECT.getCode(), aggInsights.getVerbTenses().getFuturePerfect().size());
        summary.put(NON_SENTENCES.getCode(), aggInsights.getVerbTenses().getNonSentences().size());
        summary.put(MIXED_TENSE.getCode(), aggInsights.getVerbTenses().getMixedTenses().size());

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
