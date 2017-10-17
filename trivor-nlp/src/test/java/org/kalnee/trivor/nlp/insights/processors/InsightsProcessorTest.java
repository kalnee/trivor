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

import org.junit.Assert;
import org.junit.Test;
import org.kalnee.trivor.nlp.domain.Result;
import org.kalnee.trivor.nlp.domain.Sentence;
import org.kalnee.trivor.nlp.domain.Subtitle;
import org.kalnee.trivor.nlp.domain.Token;
import org.kalnee.trivor.nlp.nlp.models.Chunk;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class InsightsProcessorTest {

    private InsightsProcessor insightsProcessor = new InsightsProcessor();

    @Test
    public void testInsertion() throws URISyntaxException {
        final List<Sentence> sentences = Collections.singletonList(new Sentence(
                "I want to kill you.",
                Arrays.asList(new Token("I", "PRP", "i", 0.99), new Token("want", "VB", "want", 0.99),
                        new Token("to", "TO", "to", 0.99), new Token("kill", "VB", "kill", 0.99),
                        new Token("you", "PR", "you", 0.99), new Token(".", ".", ".", 0.99)),
                Arrays.asList(new Chunk(Arrays.asList("want", "to"), Arrays.asList("VB", "TO"))))
        );

        final Subtitle subtitle = new Subtitle(sentences);

        final Result result = insightsProcessor.process(subtitle);

        Assert.assertTrue(result.getNumberOfSentences() == 1);
    }
}
