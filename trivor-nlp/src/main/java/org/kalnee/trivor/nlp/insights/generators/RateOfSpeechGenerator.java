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

package org.kalnee.trivor.nlp.insights.generators;

import org.kalnee.trivor.nlp.domain.RateOfSpeech;
import org.kalnee.trivor.nlp.domain.RateOfSpeechEnum;
import org.kalnee.trivor.nlp.domain.Subtitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.kalnee.trivor.nlp.domain.InsightsEnum.RATE_OF_SPEECH;
import static org.kalnee.trivor.nlp.domain.RateOfSpeechEnum.*;

/**
 * Values based on clearly-speaking.com study found on clearly-speaking.com/what-is-the-ideal-rate-of-speech/.
 * In order to reflect those values in subtitles, the value is gradually decreased to minimize the time no
 * characters are speaking, which is very frequent in movies and TV shows.
 *
 * 140wpm - 70 = 70 SLOW
 * 160wpm - 100 = 60 MODERATE
 * 190wpm - 140 = 50 FAST
 * 200wpm - 160 = 40 SUPER_FAST
 *
 */
public class RateOfSpeechGenerator implements Generator<RateOfSpeech> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RateOfSpeechGenerator.class);

    @Override
    public String getCode() {
        return RATE_OF_SPEECH.getCode();
    }

    @Override
    public boolean shouldRun(Subtitle subtitle) {
        return subtitle.getDuration() != null && subtitle.getDuration() > 0;
    }

    @Override
    public RateOfSpeech generate(Subtitle subtitle) {
        RateOfSpeechEnum rateOfSpeech = NONE;

        if (!shouldRun(subtitle)) {
            return new RateOfSpeech(rateOfSpeech);
        }

        final Long words = subtitle.getSentences().parallelStream()
                .flatMap(s -> s.getTokens().stream())
                .filter(t -> t.getToken().length() > 1)
                .count();
        final Long wpm = words / subtitle.getDuration();

        if (wpm < 70) {
            rateOfSpeech = SLOW;
        } else if (wpm >= 70 && wpm <= 100) {
            rateOfSpeech = MODERATE;
        } else if (wpm > 100 && wpm <= 140) {
            rateOfSpeech = FAST;
        } else {
            rateOfSpeech = SUPER_FAST;
        }

        LOGGER.info("{}: {}w / {}m = {}wpm ({})", getCode(), words, subtitle.getDuration(), wpm, rateOfSpeech);
        return new RateOfSpeech(rateOfSpeech, wpm);
    }
}
