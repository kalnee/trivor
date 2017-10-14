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

public class VerbTenses {

    private List<String> simplePresent;
    private List<String> simplePast;
    private List<String> simpleFuture;
    private List<String> presentProgressive;
    private List<String> pastProgressive;
    private List<String> futureProgressive;
    private List<String> presentPerfect;
    private List<String> pastPerfect;
    private List<String> futurePerfect;

    private List<String> mixedTenses;

    private List<String> nonSentences;

    public List<String> getSimplePresent() {
        return simplePresent;
    }

    public void setSimplePresent(List<String> simplePresent) {
        this.simplePresent = simplePresent;
    }

    public List<String> getSimplePast() {
        return simplePast;
    }

    public void setSimplePast(List<String> simplePast) {
        this.simplePast = simplePast;
    }

    public List<String> getSimpleFuture() {
        return simpleFuture;
    }

    public void setSimpleFuture(List<String> simpleFuture) {
        this.simpleFuture = simpleFuture;
    }

    public List<String> getPresentProgressive() {
        return presentProgressive;
    }

    public void setPresentProgressive(List<String> presentProgressive) {
        this.presentProgressive = presentProgressive;
    }

    public List<String> getPastProgressive() {
        return pastProgressive;
    }

    public void setPastProgressive(List<String> pastProgressive) {
        this.pastProgressive = pastProgressive;
    }

    public List<String> getFutureProgressive() {
        return futureProgressive;
    }

    public void setFutureProgressive(List<String> futureProgressive) {
        this.futureProgressive = futureProgressive;
    }

    public List<String> getPresentPerfect() {
        return presentPerfect;
    }

    public void setPresentPerfect(List<String> presentPerfect) {
        this.presentPerfect = presentPerfect;
    }

    public List<String> getPastPerfect() {
        return pastPerfect;
    }

    public void setPastPerfect(List<String> pastPerfect) {
        this.pastPerfect = pastPerfect;
    }

    public List<String> getFuturePerfect() {
        return futurePerfect;
    }

    public void setFuturePerfect(List<String> futurePerfect) {
        this.futurePerfect = futurePerfect;
    }

    public List<String> getMixedTenses() {
        return mixedTenses;
    }

    public void setMixedTenses(List<String> mixedTenses) {
        this.mixedTenses = mixedTenses;
    }

    public List<String> getNonSentences() {
        return nonSentences;
    }

    public void setNonSentences(List<String> nonSentences) {
        this.nonSentences = nonSentences;
    }
}

