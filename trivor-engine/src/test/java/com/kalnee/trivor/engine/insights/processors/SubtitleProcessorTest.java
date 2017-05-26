package com.kalnee.trivor.engine.insights.processors;

import com.kalnee.trivor.engine.dto.SubtitleDTO;
import com.kalnee.trivor.engine.models.Subtitle;
import com.kalnee.trivor.engine.nlp.POSTagger;
import com.kalnee.trivor.engine.nlp.SentenceDetector;
import com.kalnee.trivor.engine.nlp.SimpleTokenizer;
import com.kalnee.trivor.engine.repositories.SubtitleRepository;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import java.net.URI;
import java.net.URISyntaxException;

import static com.kalnee.trivor.engine.dto.TypeEnum.TV_SHOW;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class SubtitleProcessorTest {

	private SentenceDetector sentenceDetector = mock(SentenceDetector.class);
	private SimpleTokenizer tokenizer = mock(SimpleTokenizer.class);
	private POSTagger tagger = mock(POSTagger.class);
	private SubtitleRepository repository = mock(SubtitleRepository.class);
	private InsightsProcessor insightsProcessor = mock(InsightsProcessor.class);

	private SubtitleProcessor subtitleProcessor = new SubtitleProcessor(sentenceDetector,
			tokenizer, tagger, repository, insightsProcessor
  	);

	private SubtitleDTO subtitleDTO = new SubtitleDTO("tt0238784", "Gilmore Girls", 1, 1, 2006, 40, TV_SHOW);

	private URI getSubtitleURI(String subtitle) throws URISyntaxException {
		return getClass().getClassLoader().getResource(subtitle).toURI();
	}

	@Test
	public void testExistentContent() throws URISyntaxException {
		subtitleProcessor.process(getSubtitleURI("language/tt0238784-S01E01.srt"), subtitleDTO);

		String content = (String) Whitebox.getInternalState(subtitleProcessor, "content");

		assertTrue(content.startsWith("That's nice. Thank you.  Don't move, please."));
		assertTrue(content.contains("I'm sorry. I love the rodeo, the rodeo rules."));
		assertTrue("should've replaced simple quotes", content.contains("or even Hey, you depending on"));
		assertTrue(content.endsWith("Al's food does not stink, Al stinks."));
	}

	@Test
	public void testInsertion() throws URISyntaxException {
		subtitleProcessor.process(getSubtitleURI("language/tt0238784-S01E01.srt"), subtitleDTO);

		verify(repository, times(1)).save(any(Subtitle.class));
		verify(insightsProcessor, times(1)).process(any(Subtitle.class));
	}
}
