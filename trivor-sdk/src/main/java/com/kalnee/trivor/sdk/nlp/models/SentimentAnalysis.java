package com.kalnee.trivor.sdk.nlp.models;

import com.kalnee.trivor.sdk.models.SentimentEnum;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class SentimentAnalysis {

    private static final Logger LOGGER = LoggerFactory.getLogger(SentimentAnalysis.class);
    private static final String MODEL = "/nlp/models/en-sentiment.bin";
    private static final String NOT_IMPORTANT = ",|\\.{1,3}";

    private DocumentCategorizerME categorizer;

    public SentimentAnalysis() {
        try (InputStream modelStream = SentimentAnalysis.class.getResourceAsStream(MODEL)) {
            DoccatModel tokenizerModel = new DoccatModel(modelStream);
            categorizer = new DocumentCategorizerME(tokenizerModel);
        } catch (IOException e) {
            LOGGER.error("an error occurred while getting categories", e);
            throw new IllegalStateException(e);
        }
    }

    public Map<SentimentEnum, BigDecimal> categorize(List<String> sentences) {
        final List<String> tokens = sentences.stream()
                .flatMap(s -> Stream.of(s.split(" ")))
                .filter(t -> !t.matches(NOT_IMPORTANT))
                .map(s -> s.replace("...", ""))
                .collect(toList());

        final Map<String, Double> scoreMap = categorizer.scoreMap(tokens.toArray(new String[tokens.size()]));
        return scoreMap.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .collect(toMap(e -> SentimentEnum.valueOf(e.getKey()), e -> BigDecimal.valueOf(e.getValue())));
    }
}
