package com.kalnee.trivor.insights.services;

import com.kalnee.trivor.insights.repositories.InsightsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.util.stream.Collectors.toMap;

@Service
public class InsightService {

    private final InsightsRepository insightsRepository;

    @Autowired
    public InsightService(InsightsRepository insightsRepository) {
        this.insightsRepository = insightsRepository;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Long> findByInsightAndImdb(String insight, String imdbId) {
        final Map<String, Long> allInsights = new HashMap<>();

        insightsRepository.findAllByImdbId(imdbId).stream().flatMap(i -> i.getInsights().entrySet().stream())
                .filter(i -> i.getKey().equals(insight))
                .flatMap(i -> ((LinkedHashMap<String, Long>) i.getValue()).entrySet().stream())
                .forEach(i -> allInsights.put(i.getKey(), i.getValue() + allInsights.getOrDefault(i.getKey(), 0L)));

        return allInsights.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .filter(e -> e.getValue() > 1)
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> {
                    throw new RuntimeException(format("Duplicate key for values %s and %s", v1, v2));
                }, LinkedHashMap::new));
    }


    public List<Object> findInsightsByCodeAndGenre(String insight, String genre) {
        return insightsRepository.findInsightsByCodeAndGenre(insight, genre);
    }
}
