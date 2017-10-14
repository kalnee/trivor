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

package org.kalnee.trivor.nlp.insights.generators;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kalnee.trivor.nlp.domain.PhrasalVerbUsage;
import org.kalnee.trivor.nlp.domain.Subtitle;
import org.kalnee.trivor.nlp.domain.Token;
import org.kalnee.trivor.nlp.domain.WordUsage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;

public class PhrasalVerbsGenerator implements Generator<List<PhrasalVerbUsage>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhrasalVerbsGenerator.class);

    private final List<WordUsage> verbs;

    public PhrasalVerbsGenerator(List<WordUsage> verbs) {
        this.verbs = verbs;
    }

    @Override
    public String getCode() {
        return "phrasal-verbs-usage";
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<PhrasalVerbUsage> generate(Subtitle subtitle) {
        try {
            final InputStream phrasalVerbsStream = getClass().getClassLoader()
                    .getResourceAsStream("language/en-phrasal-verbs.json");
            final List<String> phrasalVerbs = new ObjectMapper().readValue(
                    phrasalVerbsStream, new TypeReference<List<String>>(){}
            );

            final Map<String, Set<String>> map = new HashMap<>();

            verbs.forEach(verb -> {
                        final List<String> possiblePhrasalVerbs = phrasalVerbs.stream()
                                .filter(w -> w.startsWith(verb.getWord() + " "))
                                .map(w -> w.substring(w.indexOf(" ") + 1))
                                .collect(toList());
                        if (!possiblePhrasalVerbs.isEmpty()) {
                            verb.getSentences().forEach(sentence -> {
                                String token = subtitle.getSentences().stream()
                                        .filter(s -> s.getSentence().equals(sentence))
                                        .distinct()
                                        .flatMap(s -> s.getTokens().stream())
                                        .filter(t -> t.getLemma().equals(verb.getWord()))
                                        .map(Token::getToken)
                                        .findFirst().get();
                                possiblePhrasalVerbs.forEach(ppv -> {
                                    final String phrasalVerb = format("%s %s", verb.getWord(), ppv);
                                    if (sentence.matches(".*\\b" + token + "\\s?[\\w]*\\s" + ppv + "\\b.*")) {
                                        final Set<String> sentences = map.getOrDefault(phrasalVerb, new HashSet<>());
                                        sentences.add(sentence);
                                        map.put(phrasalVerb, sentences);
                                    }
                                });
                            });
                        }
                    });

            final List<PhrasalVerbUsage> usage = map.entrySet().stream()
                    .sorted(reverseOrder(comparingInt(e -> e.getValue().size())))
                    .map(e -> new PhrasalVerbUsage(e.getKey(), e.getValue()))
                    .collect(toList());
            LOGGER.info("{}: {}", getCode(), usage.stream().limit(2).collect(toList()));
            return usage;
        } catch (IOException e) {
           LOGGER.error("an error occurred while reading the high frequency json file", e);
           return emptyList();
        }
    }
}
