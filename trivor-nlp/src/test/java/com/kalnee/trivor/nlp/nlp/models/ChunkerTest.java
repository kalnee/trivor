package com.kalnee.trivor.nlp.nlp.models;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ChunkerTest {

    private Chunker chunker;

    @Before
    public void setUp() {
        chunker = new Chunker();
    }

    @Test
    public void testValidChunk() {
        final List<Chunk> chunks = chunker.chunk(
                Arrays.asList("beautiful", "car", "."),
                Arrays.asList("JJ", "NN", "."),
                Arrays.asList(0.93, 0.98, 0.99)
        );
        assertTrue(!chunks.isEmpty());
        assertTrue(chunks.get(0).getTokens().size() == 2);
    }

    @Test
    public void testInvalidChunk() {
        final List<Chunk> chunks = chunker.chunk(
                Arrays.asList("beautiful", "car", "."),
                Arrays.asList("JJ", "NN", "."),
                Arrays.asList(0.65, 0.97, 0.99)
        );
        assertTrue(chunks.isEmpty());
    }
}
