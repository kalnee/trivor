package com.kalnee.trivor.nlp.insights.generators.post.frequency;

import java.util.*;

import static java.util.Collections.emptyMap;
import static java.util.stream.Collectors.toMap;

abstract class FrequencyGenerator {

    abstract String getInsightCode();

    @SuppressWarnings("unchecked")
    Map<String, Integer> getFrequency(Map<String, Object> insights) {
        final Map<String, Set<String>> words = ((Map<String, Set<String>>) insights.get(getInsightCode()));
        return Optional.ofNullable(words).orElse(emptyMap()).entrySet().stream()
                .collect(toMap(Map.Entry::getKey, e -> e.getValue().size()))
                .entrySet().parallelStream()
                .sorted(Map.Entry.<String, Integer> comparingByValue().reversed())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v2, LinkedHashMap::new));
    }
}
