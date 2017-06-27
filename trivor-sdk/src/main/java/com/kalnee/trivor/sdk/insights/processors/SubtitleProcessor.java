package com.kalnee.trivor.sdk.insights.processors;

import com.kalnee.trivor.sdk.handlers.SubtitleHandler;
import com.kalnee.trivor.sdk.handlers.SubtitleHandlerFactory;
import com.kalnee.trivor.sdk.models.Insight;
import com.kalnee.trivor.sdk.models.Sentence;
import com.kalnee.trivor.sdk.models.Subtitle;
import com.kalnee.trivor.sdk.models.Token;
import com.kalnee.trivor.sdk.nlp.POSTagger;
import com.kalnee.trivor.sdk.nlp.SentenceDetector;
import com.kalnee.trivor.sdk.nlp.SimpleTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
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
    private static final String SUBTITLE_HTML_REGEX = "<(.*)>\\s*";
    private static final String SUBTITLE_CC_REGEX = "\\[.*\\]\\s*";
    private static final String SUBTITLE_URL_REGEX = ".*[a-zA-Z]+\\.[a-zA-Z]+";
    private static final String SUBTITLE_SONG_REGEX = "^♪.*$";
    private static final String SUBTITLE_CONTINUATION_REGEX = "\\.{3}";
    private static final String SUBTITLE_INITIAL_QUOTE_REGEX = "^'|\\s'";
    private static final String SUBTITLE_FINAL_QUOTE_REGEX = "'\\s";

    private final URI uri;
    private final SentenceDetector sentenceDetector;
    private final SimpleTokenizer tokenizer;
    private final POSTagger tagger;
    private final InsightsProcessor insightsProcessor;
    private String content;
    private Subtitle subtitle;
    private List<Insight> insights;
    private Integer duration;

    private SubtitleProcessor(URI uri, Integer duration) {
        this.uri = uri;
        this.duration = duration;
        this.sentenceDetector = new SentenceDetector();
        this.tokenizer = new SimpleTokenizer();
        this.tagger = new POSTagger();
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
                //.map(line -> line.replaceAll(SUBTITLE_CONTINUATION_REGEX, EMPTY))
                .map(line -> line.replaceAll(SUBTITLE_DIALOG_REGEX, EMPTY))
                .map(line -> line.replaceAll(SUBTITLE_HTML_REGEX, EMPTY))
                .map(line -> line.replaceAll(SUBTITLE_CC_REGEX, EMPTY))
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

            final List<Token> tokens = new ArrayList<>();

            for (int i = 0; i < rawTokens.size(); i++) {
                tokens.add(new Token(rawTokens.get(i), tags.get(i), probs.get(i)));
            }

            return new Sentence(s, tokens);
        }).collect(toList());

        subtitle = new Subtitle(duration, sentences);

        LOGGER.info("Subtitle generated successfully.");

        insights = insightsProcessor.process(subtitle);
    }

    public Subtitle getSubtitle() {
        return subtitle;
    }

    public List<Insight> getInsights() {
        return insights;
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
