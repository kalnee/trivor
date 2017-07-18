package com.kalnee.trivor.sdk.insights.processors;

import com.kalnee.trivor.sdk.models.Sentence;
import com.kalnee.trivor.sdk.models.Subtitle;
import com.kalnee.trivor.sdk.models.Token;
import org.junit.Assert;
import org.junit.Test;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class InsightsProcessorTest {

	private InsightsProcessor insightsProcessor = new InsightsProcessor();
	
	@Test
	public void testInsertion() throws URISyntaxException {
		final List<Sentence> sentences = Collections.singletonList(new Sentence(
				"I want to kill you.",
				Arrays.asList(new Token("I", "PRP", "i", 0.99), new Token("want", "VB", "want", 0.99),
						new Token("to", "TO", "to", 0.99), new Token("kill", "VB", "kill", 0.99),
						new Token("you", "PR", "you", 0.99), new Token(".", ".", ".", 0.99)))
		);
		
		final Subtitle subtitle = new Subtitle(sentences);

		final Map<String, Object> insights = insightsProcessor.process(subtitle);

		Assert.assertTrue(!insights.isEmpty());
	}
}
