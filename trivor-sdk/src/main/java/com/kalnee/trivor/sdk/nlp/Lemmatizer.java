package com.kalnee.trivor.sdk.nlp;

import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class Lemmatizer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Lemmatizer.class);
    private static final String MODEL = "/nlp/models/en-lemmatizer.dict";
    private static final String NOT_IDENTIFIED = "O";

    private DictionaryLemmatizer lemmatizer;

    public Lemmatizer() {
        try (InputStream modelStream = Lemmatizer.class.getResourceAsStream(MODEL)) {
            lemmatizer = new DictionaryLemmatizer(modelStream);
        } catch (IOException e) {
            LOGGER.error("an error occurred while getting tokens", e);
            throw new IllegalStateException(e);
        }
    }

    public List<String> lemmatize(List<String> tokens, List<String> tags) {
        final String[] lemmas = lemmatizer.lemmatize(
                tokens.toArray(new String[tokens.size()]),
                tags.toArray(new String[tags.size()])
        );
        for (int i = 0; i < tokens.size(); i++) {
            if (NOT_IDENTIFIED.equals(lemmas[i])) {
                lemmas[i] = tokens.get(i).toLowerCase();
            }
        }
        return Arrays.asList(lemmas);
    }
}
