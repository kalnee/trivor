/* Copyright (c) 2016 Kalnee
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.kalnee.trivor.nlp.nlp.models;

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
