package com.kalnee.trivor.sdk.insights.generators;

import com.kalnee.trivor.sdk.models.Insight;
import com.kalnee.trivor.sdk.models.PaceEnum;
import com.kalnee.trivor.sdk.models.Subtitle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.kalnee.trivor.sdk.models.PaceEnum.*;

public class PaceInsightGenerator implements InsightGenerator<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaceInsightGenerator.class);

    @Override
    public String getDescription() {
        return "Speaking Pace";
    }

    @Override
    public String getCode() {
        return "pace";
    }

    @Override
    public boolean shouldRun(Subtitle subtitle) {
        return subtitle.getDuration() != null;
    }

    @Override
    public Insight<String> getInsight(Subtitle subtitle) {
        PaceEnum pace = NONE;

        if (subtitle.getDuration() == null || subtitle.getDuration() == 0) {
            return new Insight<>(getCode(), pace.toString());
        }

        final Integer frequency = subtitle.getSentences().size() / subtitle.getDuration();

        if (frequency <= 8) {
            pace = SLOW;
        } else if (frequency > 8 && frequency <= 15) {
            pace = MODERATE;
        } else if (frequency > 15 && frequency <= 20) {
            pace = FAST;
        } else {
            pace = SUPER_FAST;
        }

        LOGGER.info("{}: {}", getCode(), pace);

        return new Insight<>(getCode(), pace.toString());
    }
}
