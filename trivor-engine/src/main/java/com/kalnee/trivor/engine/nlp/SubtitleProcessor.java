package com.kalnee.trivor.engine.nlp;

import static java.util.stream.Collectors.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import com.kalnee.trivor.engine.models.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.kalnee.trivor.engine.models.Sentence;
import com.kalnee.trivor.engine.models.Subtitle;
import com.kalnee.trivor.engine.repositories.SubtitleRepository;

@Component
public class SubtitleProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(SubtitleProcessor.class);
	private static final String SUBTITLE_INDEX_REGEX = "^(\\d+)$";
	private static final String SUBTITLE_TIME_REGEX = "^((\\d+.*)\\s-->\\s(\\d+.*))$";
	private static final String SUBTITLE_DIALOG_REGEX = "^\\s*-\\s*";
	private static final String SUBTITLE_HTML_REGEX = "<(.*)>\\s*";
	private static final String SUBTITLE_CC_REGEX = "\\[.*\\]\\s*";

	private final Resource subtitle;
	private final SentenceDetector sentenceDetector;
	private final SimpleTokenizer tokenizer;
	private final POSTagger tagger;
	private final SubtitleRepository repository;
	private String content;

	@Autowired
	public SubtitleProcessor(@Value("classpath:language/subtitle2.srt") Resource subtitle,
			SentenceDetector sentenceDetector, SimpleTokenizer tokenizer, POSTagger tagger,
			SubtitleRepository repository) {
		this.subtitle = subtitle;
		this.sentenceDetector = sentenceDetector;
		this.tokenizer = tokenizer;
		this.tagger = tagger;
		this.repository = repository;
	}

	private void preProcess() throws IOException {
		LOGGER.info("############# PRE-PROCESS ###########");
		content = new BufferedReader(new InputStreamReader(subtitle.getInputStream()))
				.lines().filter(line -> !line.matches(SUBTITLE_INDEX_REGEX))
				.filter(line -> !line.matches(SUBTITLE_TIME_REGEX))
				.map(line -> line.replaceAll(SUBTITLE_DIALOG_REGEX, ""))
				.map(line -> line.replaceAll(SUBTITLE_HTML_REGEX, ""))
				.map(line -> line.replaceAll(SUBTITLE_CC_REGEX, ""))
				.collect(joining(" "));
		LOGGER.info("#############");
	}

	public void process() throws IOException {
		preProcess();

		LOGGER.info("############# PROCESS ###########");
		final List<String> detectedSentences = sentenceDetector.detect(content);
		LOGGER.info(String.format("%d sentences were found.", detectedSentences.size()));

		final List<Sentence> sentences = detectedSentences.stream().map(s -> {
			List<String> rawTokens = tokenizer.tokenize(s);
			List<String> tags = tagger.tag(rawTokens);
			List<Double> probs = tagger.probs();

			System.out.println("\n" + s);
			System.out.println(tags.stream().collect(joining(" ")));
			System.out.println(probs.stream().map(Object::toString).collect(joining(" ")));

			List<Token> tokens = new ArrayList<>();

			for (int i = 0; i < rawTokens.size(); i++) {
				tokens.add(new Token(rawTokens.get(i), tags.get(i), probs.get(i)));
			}

			return new Sentence(s, tokens);
		}).collect(toList());

		repository.insert(new Subtitle("200e22a", "Gilmore Girls", 1, 1, 2006, sentences));

		LOGGER.info("####### MOST FREQUENT WORDS ##########");

		Map<String, Long> words = detectedSentences.stream().flatMap(s -> Stream.of(s.split(" ")))
				.filter(w -> w.matches("([a-zA-Z]{2,})"))
				.map(String::toLowerCase)
				.collect(groupingBy(Function.identity(), counting()));

		words.entrySet().stream()
				.sorted(Map.Entry.<String, Long> comparingByValue().reversed())
				.forEach(System.err::println);
	}
}
