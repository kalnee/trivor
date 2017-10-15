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
                Arrays.asList(-0.5, 0.97, 0.99)
        );
        assertTrue(chunks.isEmpty());
    }
}
