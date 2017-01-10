package com.kalnee.trivor.engine.insights.processors;

import static java.util.regex.Pattern.MULTILINE;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.kalnee.trivor.engine.models.Sentence;
import com.kalnee.trivor.engine.models.Subtitle;
import com.kalnee.trivor.engine.models.Token;
import com.kalnee.trivor.engine.nlp.POSTagger;
import com.kalnee.trivor.engine.nlp.SentenceDetector;
import com.kalnee.trivor.engine.nlp.SimpleTokenizer;
import com.kalnee.trivor.engine.repositories.SubtitleRepository;
import com.kalnee.trivor.engine.utils.DateTimeUtils;

@Component
public class SubtitleProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(SubtitleProcessor.class);
	private static final String SUBTITLE_INDEX_REGEX = "^(\\d+)$";
	private static final String SUBTITLE_TIME_REGEX = "^((\\d+.*)\\s-->\\s(\\d+.*))$";
	private static final String SUBTITLE_DIALOG_REGEX = "^\\s*-\\s*";
	private static final String SUBTITLE_HTML_REGEX = "<(.*)>\\s*";
	private static final String SUBTITLE_CC_REGEX = "\\[.*\\]\\s*";
	private static final String SUBTITLE_URL_REGEX = ".*www\\..*\\.com.*";
	private static final String SUBTITLE_SONG_REGEX = "^♪.*$";

	private final Resource subtitle;
	private final SentenceDetector sentenceDetector;
	private final SimpleTokenizer tokenizer;
	private final POSTagger tagger;
	private final SubtitleRepository repository;
	private final InsightsProcessor insightsProcessor;
	private String content;
	private Integer duration;

	@Autowired
	public SubtitleProcessor(@Value("classpath:language/subtitle.srt") Resource subtitle,
			SentenceDetector sentenceDetector, SimpleTokenizer tokenizer, POSTagger tagger,
			SubtitleRepository repository, InsightsProcessor insightsProcessor) {
		this.subtitle = subtitle;
		this.sentenceDetector = sentenceDetector;
		this.tokenizer = tokenizer;
		this.tagger = tagger;
		this.repository = repository;
		this.insightsProcessor = insightsProcessor;
	}

	private void preProcess() throws IOException {
		try (InputStream stream = subtitle.getInputStream()) {
			final String lines = new BufferedReader(
				new InputStreamReader(stream)).lines().collect(joining(System.lineSeparator())
			);

			content = Stream.of(lines.split(System.lineSeparator()))
				.filter(line -> !line.matches(SUBTITLE_INDEX_REGEX))
				.filter(line -> !line.matches(SUBTITLE_TIME_REGEX))
				.filter(line -> !line.matches(SUBTITLE_SONG_REGEX))
				.filter(line -> !line.matches(SUBTITLE_URL_REGEX))
				.map(line -> line.replaceAll(SUBTITLE_DIALOG_REGEX, EMPTY))
				.map(line -> line.replaceAll(SUBTITLE_HTML_REGEX, EMPTY))
				.map(line -> line.replaceAll(SUBTITLE_CC_REGEX, EMPTY))
				.collect(joining(" "));

			final List<String> times = Stream.of(lines.split(System.lineSeparator()))
				.filter(line -> line.matches(SUBTITLE_TIME_REGEX))
				.map(this::findDuration)
				.collect(toList());
			final String lastTime = times.get(times.size() - 1);

			duration = DateTimeUtils.minutes(lastTime);
		}
	}

	private String findDuration(String content) {
		final Matcher matcher = Pattern.compile(SUBTITLE_TIME_REGEX, MULTILINE).matcher(content);
		String time = EMPTY;

		if (matcher.find()) {
			time = matcher.group(matcher.groupCount());
		}

		return time.substring(0, time.indexOf(","));
	}

	public void process() throws IOException {
		preProcess();

		final List<String> detectedSentences = sentenceDetector.detect(content);

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

		final Subtitle subtitle = repository.insert(
			new Subtitle("200e22a", "Gilmore Girls", 1, 1, 2006, duration, sentences)
		);

		LOGGER.info("Subtitle created successfully.");

		insightsProcessor.process(subtitle);
	}
}
