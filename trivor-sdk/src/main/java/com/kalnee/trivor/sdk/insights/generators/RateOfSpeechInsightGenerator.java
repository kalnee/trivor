package com.kalnee.trivor.sdk.insights.generators;

import com.kalnee.trivor.sdk.models.Insight;
import com.kalnee.trivor.sdk.models.RateOfSpeechEnum;
import com.kalnee.trivor.sdk.models.Subtitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.kalnee.trivor.sdk.models.InsightsEnum.RATE_OF_SPEECH;
import static com.kalnee.trivor.sdk.models.RateOfSpeechEnum.*;

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
public class RateOfSpeechInsightGenerator implements InsightGenerator<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RateOfSpeechInsightGenerator.class);

    @Override
    public String getDescription() {
        return RATE_OF_SPEECH.getDescription();
    }

    @Override
    public String getCode() {
        return RATE_OF_SPEECH.getCode();
    }

    @Override
    public boolean shouldRun(Subtitle subtitle) {
        return subtitle.getDuration() != null;
    }

    @Override
    public Insight<String> getInsight(Subtitle subtitle) {
        RateOfSpeechEnum rateOfSpeech = NONE;

        if (subtitle.getDuration() == null || subtitle.getDuration() == 0) {
            return new Insight<>(getCode(), rateOfSpeech.toString());
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

        LOGGER.info("{}: {}w / {}m = {}wpm", getCode(), words, subtitle.getDuration(), rateOfSpeech);

        return new Insight<>(getCode(), rateOfSpeech.toString());
    }
}
