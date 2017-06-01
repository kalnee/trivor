package com.kalnee.trivor.sdk.insights.processors;

import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertTrue;

public class SubtitleProcessorTest {

	private URI getSubtitleURI(String subtitle) throws URISyntaxException {
		return getClass().getClassLoader().getResource(subtitle).toURI();
	}

	@Test
	public void testExistentContent() throws URISyntaxException {
		SubtitleProcessor subtitleProcessor = new SubtitleProcessor.Builder(getSubtitleURI("language/tt0238784-S01E01.srt")).build();

		String content = (String) Whitebox.getInternalState(subtitleProcessor, "content");

		assertTrue(content.startsWith("That's nice. Thank you.  Don't move, please."));
		assertTrue(content.contains("I'm sorry. I love the rodeo, the rodeo rules."));
		assertTrue("should've replaced simple quotes", content.contains("or even Hey, you depending on"));
		assertTrue(content.endsWith("Al's food does not stink, Al stinks."));
	}
}
