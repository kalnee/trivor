package com.kalnee.trivor.sdk.insights.generators;

import com.kalnee.trivor.sdk.models.Subtitle;
import com.kalnee.trivor.sdk.models.Token;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.lang.Character.isUpperCase;
import static java.util.stream.Collectors.*;

abstract class FrequencyGenerator {

    private static final String WORD_REGEX = "([a-zA-Z]{2,})";
    private static final double ACCEPTED_PROB = 0.90;

    abstract List<String> getTags();

    Map<String, Long> getFrequency(Subtitle subtitle) {
        return getFrequency(subtitle, Token::getLemma, null);
    }

    Map<String, Long> getFrequency(Subtitle subtitle, Predicate<String> customFilter) {
        return getFrequency(subtitle, Token::getLemma, customFilter);
    }

    Map<String, Long> getFrequency(Subtitle subtitle, Function<Token, String> customMapper,
                                   Predicate<String> customFilter) {
        Stream<String> wordsStream = subtitle.getSentences().parallelStream()
                .flatMap(s -> s.getTokens().stream())
                .filter(t -> t.getToken().matches(WORD_REGEX))
                .filter(t -> getTags().contains(t.getTag()))
                .filter(t -> !isUpperCase(t.getToken().charAt(0)))
                .filter(t -> t.getProb() >= ACCEPTED_PROB)
                .map(customMapper);

        if (customFilter != null) {
            wordsStream = wordsStream.filter(customFilter);
        }

        final Map<String, Long> words = wordsStream.collect(groupingBy(Function.identity(), counting()));

        return words.entrySet().parallelStream()
                .sorted(Map.Entry.<String, Long> comparingByValue().reversed())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v2, LinkedHashMap::new));
    }
}
