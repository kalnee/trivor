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

package org.kalnee.trivor.nlp.insights.processors;

import org.kalnee.trivor.nlp.domain.*;
import org.kalnee.trivor.nlp.handlers.SubtitleHandler;
import org.kalnee.trivor.nlp.handlers.SubtitleHandlerFactory;
import org.kalnee.trivor.nlp.nlp.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.SPACE;

public class SubtitleProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubtitleProcessor.class);
    private static final String SPACES_REGEX = "\\s+";
    private static final String SUBTITLE_INDEX_REGEX = "^(\\d+)$";
    private static final String SUBTITLE_TIME_REGEX = "^((\\d+.*)\\s-->\\s(\\d+.*))$";
    private static final String SUBTITLE_DIALOG_REGEX = "^\\s*-|_\\s*";
    private static final String SUBTITLE_HTML_REGEX = "<[^>]*>";
    private static final String SUBTITLE_CC_REGEX = "\\[.*\\]\\s*";
    private static final String SUBTITLE_CC2_REGEX = "\\(.*\\)\\s*";
    private static final String SUBTITLE_CC_INITIAL_REGEX = "\\(.*";
    private static final String SUBTITLE_CC_FINAL_REGEX = ".*\\)";
    private static final String SUBTITLE_CHARACTER_REGEX = "^([A-Za-z]+\\s*[A-Za-z]+):\\s*";
    private static final String SUBTITLE_URL_REGEX = ".*www\\.[a-zA-Z]+.*";
    private static final String SUBTITLE_PREVIOUS_REGEX = "^Previously on.*$";
    private static final String SUBTITLE_SONG_REGEX = ".*♪.*";
    private static final String SUBTITLE_INITIAL_QUOTE_REGEX = "^'|\\s'";
    private static final String SUBTITLE_FINAL_QUOTE_REGEX = "'\\s";
    private static final String SUBTITLE_ADS_REGEX =
            ".*(Subtitle|subtitle|sync by|Sync by|Downloaded|VIP|Synchronized by|Created by).*";

    private final URI uri;
    private final SentenceDetector sentenceDetector;
    private final SimpleTokenizer tokenizer;
    private final POSTagger tagger;
    private final Lemmatizer lemmatizer;
    private final Chunker chunker;
    private final SentimentAnalyser sentimentAnalyser;
    private final InsightsProcessor insightsProcessor;
    private String content;
    private Subtitle subtitle;
    private Result result;
    private Integer duration;

    private SubtitleProcessor(URI uri, Integer duration) {
        this.uri = uri;
        this.duration = duration;
        this.sentenceDetector = new SentenceDetector();
        this.tokenizer = new SimpleTokenizer();
        this.tagger = new POSTagger();
        this.lemmatizer = new Lemmatizer();
        this.chunker = new Chunker();
        this.sentimentAnalyser = new SentimentAnalyser();
        this.insightsProcessor = new InsightsProcessor();
    }

    private void preProcess(URI uri) {
        final SubtitleHandler subtitleHandler = SubtitleHandlerFactory.create(uri).getHandler();
        final String lines = subtitleHandler.lines().collect(joining(lineSeparator()));

        content = Stream.of(lines.split(lineSeparator()))
                .filter(line -> !line.matches(SUBTITLE_INDEX_REGEX))
                .filter(line -> !line.matches(SUBTITLE_TIME_REGEX))
                .filter(line -> !line.matches(SUBTITLE_SONG_REGEX))
                .filter(line -> !line.matches(SUBTITLE_URL_REGEX))
                .filter(line -> !line.matches(SUBTITLE_PREVIOUS_REGEX))
                .filter(line -> !line.matches(SUBTITLE_ADS_REGEX))
                .map(line -> line.replaceAll(SUBTITLE_DIALOG_REGEX, EMPTY))
                .map(line -> line.replaceAll(SUBTITLE_HTML_REGEX, EMPTY))
                .map(line -> line.replaceAll(SUBTITLE_CC_REGEX, EMPTY))
                .map(line -> line.replaceAll(SUBTITLE_CC2_REGEX, EMPTY))
                .map(line -> line.replaceAll(SUBTITLE_CC_INITIAL_REGEX, EMPTY))
                .map(line -> line.replaceAll(SUBTITLE_CC_FINAL_REGEX, EMPTY))
                .map(line -> line.replaceAll(SUBTITLE_CHARACTER_REGEX, EMPTY))
                .map(line -> line.replaceAll(SUBTITLE_INITIAL_QUOTE_REGEX, SPACE))
                .map(line -> line.replaceAll(SUBTITLE_FINAL_QUOTE_REGEX, SPACE))
                .map(String::trim)
                .collect(joining(" "));
    }

    private void process() {
        preProcess(uri);

        final List<String> detectedSentences = sentenceDetector.detect(content)
                .stream()
                .map(s -> s.replaceAll(SPACES_REGEX, SPACE))
                .collect(toList());

        final List<Sentence> sentences = detectedSentences.stream().map(s -> {
            final List<String> rawTokens = tokenizer.tokenize(s);
            final List<String> tags = tagger.tag(rawTokens);
            final List<Double> probs = tagger.probs();
            final List<String> lemmas = lemmatizer.lemmatize(rawTokens, tags);
            final List<Chunk> chunks = chunker.chunk(rawTokens, tags, probs);

            final List<Token> tokens = new ArrayList<>();

            for (int i = 0; i < rawTokens.size(); i++) {
                tokens.add(new Token(rawTokens.get(i), tags.get(i), lemmas.get(i), probs.get(i)));
            }

            return new Sentence(s, tokens, chunks);
        }).collect(toList());
        final Map<SentimentEnum, BigDecimal> sentiment = sentimentAnalyser.categorize(
                sentences.stream().map(Sentence::getSentence).collect(toList())
        );
        subtitle = new Subtitle(duration, sentences, sentiment);

        LOGGER.info("Subtitle generated successfully");

        result = insightsProcessor.process(subtitle);
    }

    public Subtitle getSubtitle() {
        return subtitle;
    }

    public Result getResult() {
        return result;
    }

    public static class Builder {
        private final URI uri;
        private Integer duration;

        public Builder(URI uri) {
            this.uri = uri;
        }

        public Builder withDuration(Integer duration) {
            this.duration = duration;
            return this;
        }

        public SubtitleProcessor build() {
            final SubtitleProcessor subtitleProcessor = new SubtitleProcessor(uri, duration);
            subtitleProcessor.process();
            return subtitleProcessor;
        }
    }
}
