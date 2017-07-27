package com.kalnee.trivor.sdk.nlp.models;

import com.kalnee.trivor.sdk.models.SentimentEnum;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.kalnee.trivor.sdk.models.SentimentEnum.NEUTRAL;
import static java.util.stream.Collectors.toList;

public class SentimentAnalysis {

    private static final Logger LOGGER = LoggerFactory.getLogger(SentimentAnalysis.class);
    private static final String MODEL = "/nlp/models/en-sentiment.bin";
    private static final String NOT_IMPORTANT = ",|\\.{1,3}";
    private static final boolean SAFE_RESULT = true;
    private static final double THRESHOLD = 0.99;

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

    public SentimentEnum categorize(List<String> tokens) {
        tokens = tokens.stream().filter(t -> !t.matches(NOT_IMPORTANT)).map(String::toLowerCase).collect(toList());
        final double[] outcomes = categorizer.categorize(tokens.toArray(new String[tokens.size()]));
        final String category = categorizer.getBestCategory(outcomes);
        final double outcome = outcomes[categorizer.getIndex(category)];

        if (!SAFE_RESULT) {
            return SentimentEnum.valueOf(category);
        }

        if (outcome >= THRESHOLD) {
            LOGGER.info("\n {}", tokens);
            LOGGER.info("Category: {} - Score: {}", category, outcome);
            LOGGER.info("Result: {}", categorizer.getAllResults(outcomes));

            return SentimentEnum.valueOf(category);
        }

        return NEUTRAL;
    }
}
