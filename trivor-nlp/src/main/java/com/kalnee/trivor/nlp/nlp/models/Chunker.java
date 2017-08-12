package com.kalnee.trivor.nlp.nlp.models;

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
    private static final double THRESHOLD = 0.9;

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
