package com.kalnee.trivor.engine.insights.processors;

import static java.math.BigInteger.ONE;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.kalnee.trivor.engine.models.Insights;
import com.kalnee.trivor.engine.models.Sentence;
import com.kalnee.trivor.engine.models.Token;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import com.kalnee.trivor.engine.dto.SubtitleDTO;
import com.kalnee.trivor.engine.models.Subtitle;
import com.kalnee.trivor.engine.nlp.POSTagger;
import com.kalnee.trivor.engine.nlp.SentenceDetector;
import com.kalnee.trivor.engine.nlp.SimpleTokenizer;
import com.kalnee.trivor.engine.repositories.InsightsRepository;
import com.kalnee.trivor.engine.repositories.SubtitleRepository;

public class InsightsProcessorTest {

	private InsightsRepository repository = mock(InsightsRepository.class);
	private InsightsProcessor insightsProcessor = new InsightsProcessor(repository);
	
	@Test
	public void testInsertion() throws URISyntaxException {
		final List<Sentence> sentences = Collections.singletonList(new Sentence(
				"I want to kill you.",
				Arrays.asList(new Token("I", "PRP", 0.99), new Token("want", "VB", 0.99),
						new Token("to", "TO", 0.99), new Token("kill", "VB", 0.99),
						new Token("you", "PR", 0.99), new Token(".", ".", 0.99)))
		);
		
		final Subtitle subtitle = new Subtitle(
			ONE, "tt0238784", "Gilmore Girls", 1, 1, 2006, 42, sentences
		);

		insightsProcessor.process(subtitle);

		verify(repository, times(1)).save(any(Insights.class));
	}
}
