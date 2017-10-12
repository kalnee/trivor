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

package org.kalnee.trivor.nlp.insights.generators.post;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kalnee.trivor.nlp.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.kalnee.trivor.nlp.domain.InsightsEnum.FREQUENCY_RATE;
import static org.kalnee.trivor.nlp.domain.InsightsEnum.VERBS_USAGE;

public class PhrasalVerbPostGenerator implements PostInsightGenerator<List<PhrasalVerbUsage>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhrasalVerbPostGenerator.class);

    @Override
    public String getDescription() {
        return FREQUENCY_RATE.getDescription();
    }

    @Override
    public String getCode() {
        return "phrasal-verbs-usage";
    }

    @Override
    public Insight<List<PhrasalVerbUsage>> getInsight(Subtitle subtitle) {
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Insight<List<PhrasalVerbUsage>> getInsight(Subtitle subtitle, List<Insight> insights) {
        try {
            final InputStream phrasalVerbsStream = getClass().getClassLoader()
                    .getResourceAsStream("language/en-phrasal-verbs.json");
            final List<String> phrasalVerbs = new ObjectMapper().readValue(
                    phrasalVerbsStream, new TypeReference<List<String>>(){}
            );

            final Map<String, Set<String>> map = new HashMap<>();

            insights.stream().filter(i -> i.getCode().equals(VERBS_USAGE.getCode()))
                    .flatMap(i -> ((List<WordUsage>) i.getValue()).stream())
                    .forEach(verb -> {
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
                    .map(e -> new PhrasalVerbUsage(e.getKey(), e.getValue()))
                    .collect(toList());
            LOGGER.info("{}: {}", getCode(), usage);
            return new Insight<>(getCode(), usage);
        } catch (IOException e) {
           LOGGER.error("an error occurred while reading the high frequency json file", e);
           return new Insight<>(getCode(), emptyList());
        }
    }

    public static void main(String[] args) {
        System.out.println("be out to".substring("be out to".indexOf(" ") + 1));
    }
}
