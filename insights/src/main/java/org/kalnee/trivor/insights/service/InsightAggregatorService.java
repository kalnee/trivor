package org.kalnee.trivor.insights.service;

import org.kalnee.trivor.insights.domain.Subtitle;
import org.kalnee.trivor.insights.domain.insights.*;
import org.kalnee.trivor.insights.repository.*;
import org.kalnee.trivor.nlp.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.StreamSupport;

import static java.math.BigDecimal.*;
import static java.util.Comparator.reverseOrder;
import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toSet;
import static org.kalnee.trivor.nlp.domain.SentimentEnum.NEGATIVE;
import static org.kalnee.trivor.nlp.domain.SentimentEnum.POSITIVE;

@Service
public class InsightAggregatorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InsightAggregatorService.class);

    private final InsightsRepository insightsRepository;
    private final InsightsAggregatedRepository insightsAggRepository;
    private final InsightsByYearRepository insightsByYearRepository;
    private final InsightsGlobalRepository insightsGlobalRepository;
    private final SubtitleRepository subtitleRepository;

    public InsightAggregatorService(InsightsRepository insightsRepository,
                                    InsightsAggregatedRepository insightsAggRepository,
                                    InsightsByYearRepository insightsByYearRepository,
                                    InsightsGlobalRepository insightsGlobalRepository,
                                    SubtitleRepository subtitleRepository) {
        this.insightsRepository = insightsRepository;
        this.insightsAggRepository = insightsAggRepository;
        this.insightsByYearRepository = insightsByYearRepository;
        this.insightsGlobalRepository = insightsGlobalRepository;
        this.subtitleRepository = subtitleRepository;
    }

    public void aggregateAllByImdbIds() {
        final Set<String> imdbsIds = StreamSupport.stream(insightsRepository.findAll().spliterator(), true)
            .map(Insights::getImdbId)
            .collect(toSet());
        final Set<String> aggregatedImdbIds = insightsAggRepository.findAll().parallelStream()
            .map(InsightsAggregated::getImdbId)
            .collect(toSet());
        final Set<String> imdbIdsToProcess = imdbsIds.stream()
            .filter(imdbId -> !aggregatedImdbIds.contains(imdbId))
            .collect(toSet());

        aggregateByImdbIds(imdbIdsToProcess);
    }

    public void aggregateByImdbIds(Set<String> imdbIds) {
        imdbIds.forEach(imdbId -> {
            final InsightsAggregated aggInsights = new InsightsAggregated(imdbId);
            final GenericInsights genericInsights = getInsights(insightsRepository.findAllByImdbId(imdbId));
            aggInsights.update(genericInsights);

            insightsAggRepository.findOneByImdbId(imdbId).ifPresent(insightsAggRepository::delete);
            insightsAggRepository.save(aggInsights);
            LOGGER.info("{} aggregated successfully", imdbId);
        });
    }

    public void aggregateGlobal() {
        final InsightsGlobal insightsGlobal = new InsightsGlobal();
        final GenericInsights genericInsights = getInsights(insightsAggRepository.findAll());
        insightsGlobal.update(genericInsights);

        insightsGlobalRepository.deleteAll();
        insightsGlobalRepository.save(insightsGlobal);
        LOGGER.info("global insights aggregated successfully");
    }

    public void aggregateByYear(Integer year) {
        final Set<String> imdbIdsByYear = subtitleRepository.findByYear(year).stream()
            .map(Subtitle::getImdbId)
            .collect(toSet());

        final InsightsByYear insightsByYear = new InsightsByYear(year);
        final GenericInsights genericInsights = getInsights(insightsAggRepository.findByImdbIdIn(imdbIdsByYear));
        insightsByYear.update(genericInsights);

        insightsByYearRepository.findOneByYear(year).ifPresent(insightsByYearRepository::delete);
        insightsByYearRepository.save(insightsByYear);
        LOGGER.info("Year {} aggregated successfully", year);
    }

    private GenericInsights getInsights(List<? extends GenericInsights> insights) {
        final Map<String, Integer> rateOfSpeech = new HashMap<>();
        final Map<SentimentEnum, BigDecimal> sentiment = new HashMap<>();
        final GenericInsights genericInsights = new GenericInsights();

        LOGGER.info("Executing aggregator for {} items", insights.size());

        for (int i = 0; i < insights.size(); ++i) {
            final GenericInsights insight = insights.get(i);
            genericInsights.setNumberOfSentences(genericInsights.getNumberOfSentences() + insight.getNumberOfSentences());
            aggFrequentSentences(genericInsights.getFrequentSentences(), insight.getFrequentSentences());
            aggFrequentChunks(genericInsights.getFrequentChunks(), insight.getFrequentChunks());
            aggPhrasalVerbs(genericInsights.getPhrasalVerbs(), insight.getPhrasalVerbs());
            aggFrequencyRates(genericInsights.getFrequencyRate(), insight.getFrequencyRate());

            aggVocabulary(genericInsights.getVocabulary(), insight.getVocabulary());

            if (insight.getRateOfSpeech() != null) {
                final String rate = insight.getRateOfSpeech().getRate().name();
                rateOfSpeech.put(rate, rateOfSpeech.getOrDefault(rate, 0) + 1);
            }

            if (insight.getSentimentAnalysis() != null && !insight.getSentimentAnalysis().isEmpty()) {
                sentiment.put(POSITIVE,
                    sentiment.getOrDefault(POSITIVE, ZERO).add(insight.getSentimentAnalysis().get(POSITIVE)));
                sentiment.put(NEGATIVE,
                    sentiment.getOrDefault(NEGATIVE, ZERO).add(insight.getSentimentAnalysis().get(NEGATIVE)));
            }

            if (i == (insights.size() - 1)) {
                if (insight.getRateOfSpeech() != null) {
                    final String finalRateOfSpeech = rateOfSpeech.entrySet().stream()
                        .sorted(comparingByValue(reverseOrder()))
                        .map(Map.Entry::getKey).findFirst().get();
                    genericInsights.setRateOfSpeech(new RateOfSpeech(RateOfSpeechEnum.valueOf(finalRateOfSpeech)));
                }
                if (insight.getSentimentAnalysis() != null && !insight.getSentimentAnalysis().isEmpty()) {
                    sentiment.put(POSITIVE, sentiment.get(POSITIVE).divide(valueOf(insights.size()), ROUND_UP));
                    sentiment.put(NEGATIVE, sentiment.get(NEGATIVE).divide(valueOf(insights.size()), ROUND_UP));
                    genericInsights.setSentimentAnalysis(sentiment);
                }
            }
        }

        return genericInsights;
    }

    private void aggVocabulary(Vocabulary aggVocabulary, Vocabulary vocabulary) {
        aggVocabulary(aggVocabulary.getVerbs(), vocabulary.getVerbs());
        aggVocabulary(aggVocabulary.getAdverbs(), vocabulary.getAdverbs());
        aggVocabulary(aggVocabulary.getAdjectives(), vocabulary.getAdjectives());
        aggVocabulary(aggVocabulary.getPrepositions(), vocabulary.getPrepositions());
        aggVocabulary(aggVocabulary.getNouns(), vocabulary.getNouns());
        aggVocabulary(aggVocabulary.getModals(), vocabulary.getModals());
        aggVocabulary(aggVocabulary.getComparatives(), vocabulary.getComparatives());
        aggVocabulary(aggVocabulary.getSuperlatives(), vocabulary.getSuperlatives());
        aggVocabulary(aggVocabulary.getWhWords(), vocabulary.getWhWords());
    }

    private void aggVocabulary(Set<WordUsage> agg, Set<WordUsage> current) {
        if (agg.isEmpty()) {
            agg.addAll(current);
            return;
        }
        current.forEach(word -> {
                final Optional<WordUsage> aggWordOpt = agg.stream()
                    .filter(aggWord -> aggWord.getWord().equals(word.getWord()))
                    .findFirst();
                if (aggWordOpt.isPresent()) {
                    aggWordOpt.get().getSentences().addAll(word.getSentences());
                } else {
                    agg.add(word);
                }
            }
        );
    }

    private void aggFrequentSentences(Set<SentenceFrequency> agg, Set<SentenceFrequency> current) {
        if (agg.isEmpty()) {
            agg.addAll(current);
            return;
        }
        current.forEach(frequentSentence -> {
                final Optional<SentenceFrequency> aggSentenceOpt = agg.stream()
                    .filter(aggWord -> aggWord.getSentence().equals(frequentSentence.getSentence()))
                    .findFirst();
                if (aggSentenceOpt.isPresent()) {
                    final SentenceFrequency aggSentenceFrequency = aggSentenceOpt.get();
                    aggSentenceFrequency.setFrequency(
                        aggSentenceFrequency.getFrequency() + frequentSentence.getFrequency()
                    );
                } else {
                    agg.add(frequentSentence);
                }
            }
        );
    }

    private void aggFrequentChunks(Set<ChunkFrequency> agg, Set<ChunkFrequency> current) {
        if (agg.isEmpty()) {
            agg.addAll(current);
            return;
        }
        current.forEach(frequentChunk -> {
                final Optional<ChunkFrequency> aggChunkOpt = agg.stream()
                    .filter(aggWord -> aggWord.getChunk().equals(frequentChunk.getChunk()))
                    .findFirst();
                if (aggChunkOpt.isPresent()) {
                    final ChunkFrequency aggChunkFrequency = aggChunkOpt.get();
                    aggChunkFrequency.setFrequency(
                        aggChunkFrequency.getFrequency() + frequentChunk.getFrequency()
                    );
                } else {
                    agg.add(frequentChunk);
                }
            }
        );
    }

    private void aggPhrasalVerbs(Set<PhrasalVerbUsage> agg, Set<PhrasalVerbUsage> current) {
        if (agg.isEmpty()) {
            agg.addAll(current);
            return;
        }
        current.forEach(phrasalVerb -> {
                final Optional<PhrasalVerbUsage> aggPhrasalVerbOpt = agg.stream()
                    .filter(aggWord -> aggWord.getPhrasalVerb().equals(phrasalVerb.getPhrasalVerb()))
                    .findFirst();
                if (aggPhrasalVerbOpt.isPresent()) {
                    aggPhrasalVerbOpt.get().getSentences().addAll(phrasalVerb.getSentences());
                } else {
                    agg.add(phrasalVerb);
                }
            }
        );
    }

    private void aggFrequencyRates(List<FrequencyRate> agg, List<FrequencyRate> current) {
        if (agg.isEmpty()) {
            agg.addAll(current);
            return;
        }
        agg.get(0).getWords().addAll(current.get(0).getWords());
        agg.get(1).getWords().addAll(current.get(1).getWords());
        agg.get(2).getWords().addAll(current.get(2).getWords());
    }
}
