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

import org.kalnee.trivor.nlp.domain.ChunkFrequency;
import org.kalnee.trivor.nlp.domain.SentenceFrequency;
import org.kalnee.trivor.nlp.domain.Subtitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.*;
import static org.kalnee.trivor.nlp.domain.InsightsEnum.FREQUENT_CHUNKS;

public class FrequentChunksGenerator implements Generator<List<ChunkFrequency>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FrequentChunksGenerator.class);

    @Override
    public String getCode() {
        return FREQUENT_CHUNKS.getCode();
    }

    @Override
    public List<ChunkFrequency> generate(Subtitle subtitle) {
        final Map<String, Long> chunks = subtitle.getSentences().parallelStream()
                .flatMap(s -> s.getChunks().stream())
                .map(chunk -> chunk.getTokens().stream().collect(joining(" ")))
                .filter(chunk -> !chunk.contains("..."))
                .collect(groupingBy(Function.identity(), counting()));

        final List<ChunkFrequency> sortedChunks = chunks.entrySet().parallelStream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .filter(e -> e.getValue() > 1)
                .map(e -> new ChunkFrequency(e.getKey(), e.getValue()))
                .collect(toList());

        LOGGER.info("{} - {}", getCode(), sortedChunks.stream().limit(2).collect(toList()));
        return sortedChunks;
    }
}
