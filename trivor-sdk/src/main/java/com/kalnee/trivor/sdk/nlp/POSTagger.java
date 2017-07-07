package com.kalnee.trivor.sdk.nlp;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;

import static java.util.stream.Collectors.toList;

public class POSTagger {

	private static final Logger LOGGER = LoggerFactory.getLogger(POSTagger.class);
	private static final String MODEL = "/nlp/models/en-pos-maxent.bin";

	private POSTaggerME tagger;

	public POSTagger() {
		try (InputStream modelStream = POSTagger.class.getResourceAsStream(MODEL)) {
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
		return DoubleStream.of(tagger.probs()).boxed().collect(toList());
	}
}
