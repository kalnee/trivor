package com.kalnee.trivor.sdk.insights.generators.sentences;

import com.kalnee.trivor.sdk.models.Sentence;
import com.kalnee.trivor.sdk.models.Subtitle;
import com.kalnee.trivor.sdk.models.Token;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.lang.Character.isUpperCase;

abstract class SentencesExampleGenerator {

    private static final String WORD_REGEX = "([a-zA-Z]{2,})";
    private static final double ACCEPTED_PROB = 0.90;

    abstract List<String> getTags();

    Map<String, Set<String>> getSentences(Subtitle subtitle) {
        return getSentences(subtitle, Token::getLemma, null);
    }

    Map<String, Set<String>> getSentences(Subtitle subtitle, Predicate<String> customFilter) {
        return getSentences(subtitle, Token::getLemma, customFilter);
    }

    Map<String, Set<String>> getSentences(Subtitle subtitle, Function<Token, String> customMapper,
                                          Predicate<String> customFilter) {
        final Map<String, Set<String>> words = new HashMap<>();
        for (Sentence sentence: subtitle.getSentences()) {
            Stream<String> wordsStream = sentence.getTokens().stream()
                    .filter(t -> t.getToken().matches(WORD_REGEX))
                    .filter(t -> getTags().contains(t.getTag()))
                    .filter(t -> !isUpperCase(t.getToken().charAt(0)))
                    .filter(t -> t.getProb() >= ACCEPTED_PROB)
                    .map(customMapper);

            if (customFilter != null) {
                wordsStream = wordsStream.filter(customFilter);
            }

            wordsStream.forEach(word -> {
                final Set<String> sentences = words.getOrDefault(word, new HashSet<>());
                sentences.add(sentence.getSentence());
                words.put(word, sentences);
            });
        }

        return words;
    }
}
