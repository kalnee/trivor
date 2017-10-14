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

package org.kalnee.trivor.nlp.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Subtitle {

    private Integer duration;
    private List<Sentence> sentences;
    private Map<SentimentEnum, BigDecimal> sentiment;

    public Subtitle() {
    }

    public Subtitle(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    public Subtitle(Integer duration, List<Sentence> sentences) {
        this(sentences);
        this.duration = duration;
    }

    public Subtitle(Integer duration, List<Sentence> sentences, Map<SentimentEnum, BigDecimal> sentiment) {
        this(duration, sentences);
        this.sentiment = sentiment;
    }

    public Integer getDuration() {
        return duration;
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    public Map<SentimentEnum, BigDecimal> getSentiment() {
        return sentiment;
    }
}
