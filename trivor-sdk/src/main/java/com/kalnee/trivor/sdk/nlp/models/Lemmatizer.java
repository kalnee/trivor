package com.kalnee.trivor.sdk.nlp.models;

import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lemmatizer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Lemmatizer.class);
    private static final String MODEL = "/nlp/models/en-lemmatizer.dict";
    private static final String NOT_IDENTIFIED = "O";
    private static final Map<String, String> CONTRACTIONS = new HashMap<>();

    private DictionaryLemmatizer lemmatizer;

    static {
        CONTRACTIONS.put("ca", "can");
        CONTRACTIONS.put("wo", "will");
        CONTRACTIONS.put("wouldn", "will");
    }

    public Lemmatizer() {
        try (InputStream modelStream = Lemmatizer.class.getResourceAsStream(MODEL)) {
            lemmatizer = new DictionaryLemmatizer(modelStream);
        } catch (IOException e) {
            LOGGER.error("an error occurred while getting lemmas", e);
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
            lemmas[i] = CONTRACTIONS.getOrDefault(lemmas[i], lemmas[i]);
        }
        return Arrays.asList(lemmas);
    }
}
