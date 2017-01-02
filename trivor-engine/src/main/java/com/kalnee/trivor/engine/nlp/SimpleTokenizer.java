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

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import javax.annotation.PostConstruct;

@Component
public class SimpleTokenizer {

	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTokenizer.class);

	private final Resource model;
	private Tokenizer tokenizer;

	@Autowired
	public SimpleTokenizer(@Value("classpath:nlp/models/en-token.bin") Resource model) {
		this.model = model;
	}
	
	@PostConstruct
	private void setupModel() {
		try (InputStream modelStream = model.getInputStream()) {
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
