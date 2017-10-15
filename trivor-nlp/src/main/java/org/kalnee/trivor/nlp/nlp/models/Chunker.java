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

package org.kalnee.trivor.nlp.nlp.models;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.util.Span;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Chunker {

    private static final Logger LOGGER = LoggerFactory.getLogger(Chunker.class);
    private static final String MODEL = "/nlp/models/en-chunker.bin";
    private static final double THRESHOLD = 0;

    private ChunkerME chunker;

    public Chunker() {
        try (InputStream modelStream = Chunker.class.getResourceAsStream(MODEL)) {
            ChunkerModel chunkerModel = new ChunkerModel(modelStream);
            chunker = new ChunkerME(chunkerModel);
        } catch (IOException e) {
            LOGGER.error("an error occurred while getting chunks", e);
            throw new IllegalStateException(e);
        }
    }

    public List<Chunk> chunk(List<String> tokens, List<String> tags, List<Double> probs) {
        final Span[] spans = chunker.chunkAsSpans(
                tokens.toArray(new String[tokens.size()]), tags.toArray(new String[tags.size()])
        );

        final List<Chunk> chunks = new ArrayList<>();

        for (final Span span : spans) {
            boolean valid = true;
            for (int j = span.getStart(); j < span.getEnd(); j++) {
                if (chunker.probs()[j] < THRESHOLD || probs.get(j) < THRESHOLD) {
                    valid = false;
                    break;
                }
            }

            if (valid && span.getEnd() - span.getStart() > 1) {
                chunks.add(new Chunk(
                        tokens.subList(span.getStart(), span.getEnd()),
                        tags.subList(span.getStart(), span.getEnd()))
                );
            }
        }

        return chunks;
    }
}
