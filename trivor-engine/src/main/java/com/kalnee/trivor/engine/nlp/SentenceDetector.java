package com.kalnee.trivor.engine.nlp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

@Component
public class SentenceDetector {

	private static final Log LOGGER = LogFactory.getLog(SentenceDetector.class);

	private final Resource model;

	@Autowired
	public SentenceDetector(@Value("classpath:nlp/models/en-sent.bin") Resource model) {
		this.model = model;
	}

	public List<String> detect(String text) throws IOException {
		try (InputStream modelStream = model.getInputStream()) {
			SentenceModel sentenceModel = new SentenceModel(modelStream);
			SentenceDetectorME sentenceDetector = new SentenceDetectorME(sentenceModel);

			String sentences[] = sentenceDetector.sentDetect(text);

			return Arrays.asList(sentences);
		} catch (IOException e) {
        LOGGER.error("an error occurred while detecting sentences", e);
        throw new IllegalStateException(e);
    }
	}
}
