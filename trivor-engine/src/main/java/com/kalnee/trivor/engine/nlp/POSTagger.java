package com.kalnee.trivor.engine.nlp;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

@Component
public class POSTagger {

	private static final Logger LOGGER = LoggerFactory.getLogger(POSTagger.class);

	private final Resource model;
	private POSTaggerME tagger;

	@Autowired
	public POSTagger(@Value("classpath:nlp/models/en-pos-perceptron.bin") Resource model) {
		this.model = model;
	}

	@PostConstruct
	private void setupModel() {
		try (InputStream modelStream = model.getInputStream()) {
			POSModel posModel = new POSModel(modelStream);
			tagger = new POSTaggerME(posModel);
		} catch (IOException e) {
			LOGGER.error("an error occurred while tagging tokens", e);
			throw new IllegalStateException(e);
		}
	}

	public List<String> tag(List<String> tokens) {
		String tags[] = tagger.tag(tokens.toArray(new String[tokens.size()]));
		return Arrays.asList(tags);
	}

	public String tag(String token) {
		String tags[] = tagger.tag(new String[] { token });
		return tags[0];
	}

	public List<Double> probs() {
		return DoubleStream.of(tagger.probs()).mapToObj(Double::valueOf).collect(toList());
	}
}
