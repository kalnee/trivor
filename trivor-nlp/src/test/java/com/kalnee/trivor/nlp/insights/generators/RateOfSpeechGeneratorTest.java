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

package com.kalnee.trivor.nlp.insights.generators;

import com.kalnee.trivor.nlp.insights.processors.SubtitleProcessor;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.kalnee.trivor.nlp.nlp.models.InsightsEnum.RATE_OF_SPEECH;
import static com.kalnee.trivor.nlp.nlp.models.RateOfSpeechEnum.FAST;
import static org.junit.Assert.assertTrue;

public class RateOfSpeechGeneratorTest {

    @Test
    public void testSelectCorrectRate() throws IOException, URISyntaxException {
        SubtitleProcessor sp = new SubtitleProcessor
                .Builder(RateOfSpeechGeneratorTest.class.getResource("/language/tt0238784-S01E01.srt").toURI())
                .withDuration(42)
                .build();

        assertTrue(FAST.name().equals(sp.getInsights().get(RATE_OF_SPEECH.getCode())));
    }
}
