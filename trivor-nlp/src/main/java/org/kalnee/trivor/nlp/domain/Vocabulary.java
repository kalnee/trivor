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

import java.util.List;

public class Vocabulary {

    private List<WordUsage> adjectives;
    private List<WordUsage> adverbs;
    private List<WordUsage> comparatives;
    private List<WordUsage> modals;
    private List<WordUsage> nouns;
    private List<WordUsage> prepositions;
    private List<WordUsage> superlatives;
    private List<WordUsage> verbs;
    private List<WordUsage> whWords;

    public List<WordUsage> getAdjectives() {
        return adjectives;
    }

    public void setAdjectives(List<WordUsage> adjectives) {
        this.adjectives = adjectives;
    }

    public List<WordUsage> getAdverbs() {
        return adverbs;
    }

    public void setAdverbs(List<WordUsage> adverbs) {
        this.adverbs = adverbs;
    }

    public List<WordUsage> getComparatives() {
        return comparatives;
    }

    public void setComparatives(List<WordUsage> comparatives) {
        this.comparatives = comparatives;
    }

    public List<WordUsage> getModals() {
        return modals;
    }

    public void setModals(List<WordUsage> modals) {
        this.modals = modals;
    }

    public List<WordUsage> getNouns() {
        return nouns;
    }

    public void setNouns(List<WordUsage> nouns) {
        this.nouns = nouns;
    }

    public List<WordUsage> getPrepositions() {
        return prepositions;
    }

    public void setPrepositions(List<WordUsage> prepositions) {
        this.prepositions = prepositions;
    }

    public List<WordUsage> getSuperlatives() {
        return superlatives;
    }

    public void setSuperlatives(List<WordUsage> superlatives) {
        this.superlatives = superlatives;
    }

    public List<WordUsage> getVerbs() {
        return verbs;
    }

    public void setVerbs(List<WordUsage> verbs) {
        this.verbs = verbs;
    }

    public List<WordUsage> getWhWords() {
        return whWords;
    }

    public void setWhWords(List<WordUsage> whWords) {
        this.whWords = whWords;
    }
}

