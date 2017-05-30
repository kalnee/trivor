package com.kalnee.trivor.sdk.nlp;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class SimpleTokenizer {

    private static final Logger LOGGER = LogManager.getLogger(SimpleTokenizer.class);
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
