package com.kalnee.trivor.engine.nlp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

@Component
public class SimpleTokenizer {

	private static final Log LOGGER = LogFactory.getLog(SimpleTokenizer.class);

	private final Resource model;

	@Autowired
	public SimpleTokenizer(@Value("classpath:nlp/models/en-token.bin") Resource model) {
		this.model = model;
	}

	public List<String> tokenize(String sentence) {
		try (InputStream modelStream = model.getInputStream()) {
			TokenizerModel tokenizerModel = new TokenizerModel(modelStream);
			Tokenizer tokenizer = new TokenizerME(tokenizerModel);

			String tokens[] = tokenizer.tokenize(sentence);

			return Arrays.asList(tokens);
		} catch (IOException e) {
			LOGGER.error("an error occurred while getting tokens", e);
			throw new IllegalStateException(e);
		}
	}
}
