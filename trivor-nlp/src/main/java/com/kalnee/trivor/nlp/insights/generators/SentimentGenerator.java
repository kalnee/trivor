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


import com.kalnee.trivor.nlp.nlp.models.Insight;
import com.kalnee.trivor.nlp.nlp.models.SentimentEnum;
import com.kalnee.trivor.nlp.nlp.models.Subtitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Map;

import static com.kalnee.trivor.nlp.nlp.models.InsightsEnum.SENTIMENT_ANALYSIS;

public class SentimentGenerator implements InsightGenerator<Map<SentimentEnum, BigDecimal>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SentimentGenerator.class);

	@Override
	public String getDescription() {
		return SENTIMENT_ANALYSIS.getDescription();
	}

	@Override
	public String getCode() {
		return SENTIMENT_ANALYSIS.getCode();
	}

	@Override
	public boolean shouldRun(Subtitle subtitle) {
		return subtitle.getSentiment() != null;
	}

	public Insight<Map<SentimentEnum, BigDecimal>> getInsight(Subtitle subtitle) {
		LOGGER.info("{}: {}", getCode(), subtitle.getSentiment());
		return new Insight<>(getCode(), subtitle.getSentiment());
	}
}
