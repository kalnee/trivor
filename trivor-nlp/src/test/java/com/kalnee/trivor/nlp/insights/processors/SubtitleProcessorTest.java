package com.kalnee.trivor.nlp.insights.processors;

import com.kalnee.trivor.nlp.insights.generators.InsightGenerator;
import com.kalnee.trivor.nlp.insights.generators.post.PostInsightGenerator;
import com.kalnee.trivor.nlp.nlp.models.Insight;
import com.kalnee.trivor.nlp.nlp.models.Subtitle;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import static com.kalnee.trivor.nlp.nlp.models.InsightsEnum.ADJECTIVES_FREQUENCY;
import static com.kalnee.trivor.nlp.nlp.models.InsightsEnum.ADJECTIVES_SENTENCES;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SubtitleProcessorTest {

    private URI getSubtitleURI(String subtitle) throws URISyntaxException {
        return getClass().getClassLoader().getResource(subtitle).toURI();
    }

    @Test
    public void testExistentContent() throws URISyntaxException {
        SubtitleProcessor subtitleProcessor = new SubtitleProcessor.Builder(
                getSubtitleURI("language/tt0238784-S01E01.srt")
        ).build();

        String content = (String) Whitebox.getInternalState(subtitleProcessor, "content");

        assertTrue(content.startsWith("That's nice. Thank you.  Don't move, please."));
        assertTrue(content.contains("I'm sorry. I love the rodeo, the rodeo rules."));
        assertTrue("should've replaced simple quotes", content.contains("or even Hey, you depending on"));
        assertTrue(content.endsWith("Al's food does not stink, Al stinks."));
    }

    @Test
    public void testCustomInsightAndPostInsights() throws URISyntaxException {
        SubtitleProcessor subtitleProcessor = new SubtitleProcessor
                .Builder(getSubtitleURI("language/tt0238784-S01E01.srt"))
                .addCustomInsights(new CustomInsight())
                .addCustomPostInsights(new CustomPostInsight())
                .build();

        assertEquals("custom", subtitleProcessor.getInsights().get("custom-insight"));
        assertEquals("custom post", subtitleProcessor.getInsights().get("custom-post-insight"));
    }

    class CustomInsight implements InsightGenerator<String> {

        @Override
        public String getCode() {
            return "custom-insight";
        }

        @Override
        public Insight<String> getInsight(Subtitle subtitle) {
            return new Insight<>(getCode(), "custom");
        }
    }

    class CustomPostInsight implements PostInsightGenerator<String> {

        @Override
        public String getCode() {
            return "custom-post-insight";
        }

        @Override
        public Insight<String> getInsight(Subtitle subtitle, Map<String, Object> insights) {
            return new Insight<>(getCode(), "custom post");
        }
    }

    @Test
    public void testInsightsAndPostInsightsToSKip() throws URISyntaxException {
        SubtitleProcessor subtitleProcessor = new SubtitleProcessor
                .Builder(getSubtitleURI("language/tt0238784-S01E01.srt"))
                .skipInsights(ADJECTIVES_SENTENCES.getCode())
                .skipPostInsights(ADJECTIVES_FREQUENCY.getCode())
                .build();

        assertEquals(null, subtitleProcessor.getInsights().get(ADJECTIVES_SENTENCES.getCode()));
        assertEquals(null, subtitleProcessor.getInsights().get(ADJECTIVES_FREQUENCY.getCode()));
    }

}