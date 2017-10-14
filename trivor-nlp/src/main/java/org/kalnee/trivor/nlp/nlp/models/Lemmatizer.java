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

import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lemmatizer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Lemmatizer.class);
    private static final String MODEL = "/nlp/models/en-lemmatizer.dict";
    private static final String NOT_IDENTIFIED = "O";
    private static final Map<String, String> CONTRACTIONS = new HashMap<>();

    private DictionaryLemmatizer lemmatizer;

    static {
        CONTRACTIONS.put("ca", "can");
        CONTRACTIONS.put("wo", "will");
        CONTRACTIONS.put("wouldn", "will");
        CONTRACTIONS.put("y", "you");
    }

    public Lemmatizer() {
        try (InputStream modelStream = Lemmatizer.class.getResourceAsStream(MODEL)) {
            lemmatizer = new DictionaryLemmatizer(modelStream);
        } catch (IOException e) {
            LOGGER.error("an error occurred while getting lemmas", e);
            throw new IllegalStateException(e);
        }
    }

    public List<String> lemmatize(List<String> tokens, List<String> tags) {
        final String[] lemmas = lemmatizer.lemmatize(
                tokens.toArray(new String[tokens.size()]),
                tags.toArray(new String[tags.size()])
        );
        for (int i = 0; i < tokens.size(); i++) {
            if (NOT_IDENTIFIED.equals(lemmas[i])) {
                lemmas[i] = tokens.get(i).toLowerCase();
            }
            lemmas[i] = CONTRACTIONS.getOrDefault(lemmas[i], lemmas[i]);
        }
        return Arrays.asList(lemmas);
    }
}
