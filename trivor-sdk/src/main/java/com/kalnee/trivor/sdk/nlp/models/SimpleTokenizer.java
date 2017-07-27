package com.kalnee.trivor.sdk.nlp.models;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class SimpleTokenizer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTokenizer.class);
    private static final String MODEL = "/nlp/models/en-token.bin";

    private Tokenizer tokenizer;

    public SimpleTokenizer() {
        try (InputStream modelStream = SimpleTokenizer.class.getResourceAsStream(MODEL)) {
            TokenizerModel tokenizerModel = new TokenizerModel(modelStream);
            tokenizer = new TokenizerME(tokenizerModel);
        } catch (IOException e) {
            LOGGER.error("an error occurred while getting tokens", e);
            throw new IllegalStateException(e);
        }
    }

    public List<String> tokenize(String sentence) {
        return Arrays.asList(tokenizer.tokenize(sentence));
    }
}
