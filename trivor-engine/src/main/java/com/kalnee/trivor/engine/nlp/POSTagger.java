package com.kalnee.trivor.engine.nlp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

@Component
public class POSTagger {

	private static final Log LOGGER = LogFactory.getLog(POSTagger.class);

	private final Resource model;

	@Autowired
	public POSTagger(@Value("classpath:nlp/models/en-pos-perceptron.bin") Resource model) {
		this.model = model;
	}

	public List<String> tag(List<String> tokens) {
		try (InputStream modelStream = model.getInputStream()) {
			POSModel posModel = new POSModel(modelStream);
			POSTaggerME tagger = new POSTaggerME(posModel);

			String tags[] = tagger.tag(tokens.toArray(new String[tokens.size()]));

			System.out.println(Arrays.toString(tagger.probs()));

			return Arrays.asList(tags);
		} catch (IOException e) {
			LOGGER.error("an error occurred while tagging tokens", e);
			throw new IllegalStateException(e);
		}
	}
}
