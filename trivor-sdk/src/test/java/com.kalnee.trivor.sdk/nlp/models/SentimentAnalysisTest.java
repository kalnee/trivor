package com.kalnee.trivor.sdk.nlp.models;

import com.kalnee.trivor.sdk.models.SentimentEnum;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

import static com.kalnee.trivor.sdk.models.SentimentEnum.NEGATIVE;
import static com.kalnee.trivor.sdk.models.SentimentEnum.POSITIVE;
import static org.junit.Assert.assertTrue;

public class SentimentAnalysisTest {

    private SentimentAnalysis sentimentAnalysis;

    @Before
    public void setUp() {
        sentimentAnalysis = new SentimentAnalysis();
    }

    @Test
    public void testPositiveSentences() {
        final Map<SentimentEnum, BigDecimal> simpleResult = sentimentAnalysis.categorize(
                Arrays.asList("When I stepped into the theater, I thought this was going to be a great movie. " +
                        "And I was not disappointed one bit. WOW! This movie is brilliant! The emotions felt through " +
                        "out the whole movie are extraordinary! Great acting by Madhavan and Simran. Beautiful music " +
                        "by A.R. Rahman. This might most probably be the best Tamil movie I've seen in ages. Mani Ratnam " +
                        "has yet again proved that he is the best in making meaningful and heartfelt movies. " +
                        "This movie is basically about a young girl (P.S. Keerthana) who is in search of her biological " +
                        "mother (Nandita Das) who abandoned her in a refugee camp to fight for her country just " +
                        "like her husband (J.D. Chakravathy). A young novelist (Madhavan) finds out about this young " +
                        "girl's story and decides to write a story about her. He and his wife (Simran) decide to adopt " +
                        "this young child but one day she finds out that they are not her real parents and decides to " +
                        "search for her biological mother. This movie really screams EXCELLENT. The way Mani Ratnam " +
                        "presented the movie is magnificent.")
        );
        assertTrue(simpleResult.get(POSITIVE).compareTo(simpleResult.get(NEGATIVE)) > 0);
    }

    @Test
    public void testNegativeSentences() {
        final Map<SentimentEnum, BigDecimal> simpleResult = sentimentAnalysis.categorize(
                Arrays.asList("I just finished watching this movie and am disappointed to say that I didn't enjoy it a bit. " +
                        "It is so slow Slow and uninteresting. This kid from Harry Potter plays a shy teenager " +
                        "with an rude mother, and then one day the rude mother tells the kid to find a " +
                        "job so that they could accommodate an old guy apparently having no place to live has " +
                        "started to live with his family and therefore the kid goes to work for a old lady. And this " +
                        "old lady who is living all alone teaches him about girls, driving car and life! I couldn't " +
                        "get how an 18 year old guy enjoy spending time with an awful lady in her 80s. Sorry if my " +
                        "comments on this movie has bothered people who might have enjoyed it, I could be wrong as " +
                        "I am not British and may not understand the social and their family structure and way of life. " +
                        "Mostly the movie is made for the British audience.")
        );
        assertTrue(simpleResult.get(NEGATIVE).compareTo(simpleResult.get(POSITIVE)) > 0);
    }
}
