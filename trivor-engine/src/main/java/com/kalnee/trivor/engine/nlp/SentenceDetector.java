package com.kalnee.trivor.engine.nlp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

import javax.annotation.PostConstruct;

@Component
public class SentenceDetector {

	private static final Logger LOGGER = LoggerFactory.getLogger(SentenceDetector.class);

	private final Resource model;
	private SentenceDetectorME sentenceDetector;

	@Autowired
	public SentenceDetector(@Value("classpath:nlp/models/en-sent.bin") Resource model) {
		this.model = model;
	}

	@PostConstruct
	private void setupModel() {
		try (InputStream modelStream = model.getInputStream()) {
			SentenceModel sentenceModel = new SentenceModel(modelStream);
			sentenceDetector = new SentenceDetectorME(sentenceModel);
		} catch (IOException e) {
			LOGGER.error("an error occurred while detecting sentences", e);
			throw new IllegalStateException(e);
		}
	}

	public List<String> detect(String text) throws IOException {
		return Arrays.asList(sentenceDetector.sentDetect(text));
	}
}
