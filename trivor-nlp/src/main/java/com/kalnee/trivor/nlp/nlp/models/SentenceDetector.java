package com.kalnee.trivor.nlp.nlp.models;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class SentenceDetector {

    private static final Logger LOGGER = LoggerFactory.getLogger(SentenceDetector.class);
    private static final String MODEL = "/nlp/models/en-sent.bin";

    private SentenceDetectorME sentenceDetector;

    public SentenceDetector() {
        try (InputStream modelStream = SentenceDetector.class.getResourceAsStream(MODEL)) {
            SentenceModel sentenceModel = new SentenceModel(modelStream);
            sentenceDetector = new SentenceDetectorME(sentenceModel);
        } catch (IOException e) {
            LOGGER.error("an error occurred while detecting sentences", e);
            throw new IllegalStateException(e);
        }
    }

    public List<String> detect(String text) {
        return Arrays.asList(sentenceDetector.sentDetect(text));
    }
}
