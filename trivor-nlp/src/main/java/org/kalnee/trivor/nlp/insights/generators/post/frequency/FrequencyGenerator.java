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

package org.kalnee.trivor.nlp.insights.generators.post.frequency;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Collections.emptyMap;
import static java.util.stream.Collectors.toMap;

/**
 * A frequency insight generator. Frequency insights will return
 * the number of times certain types of words will appear on the text.
 *
 * Subclasses must implement <tt>PostInsightGenerator</tt> interface.
 *
 * @see AdjectivesFrequencyGenerator
 * @see AdverbsFrequencyGenerator
 * @see ComparativesFrequencyGenerator
 * @see ModalsFrequencyGenerator
 * @see NounsFrequencyGenerator
 * @see PrepositionsFrequencyGenerator
 * @see SuperlativesFrequencyGenerator
 * @see VerbsFrequencyGenerator
 * @see WhWordsFrequencyGenerator
 *
 * @since 0.0.1
 */
abstract class FrequencyGenerator {

    abstract String getInsightCode();

    /**
     * Returns the frequencies for every word
     *
     * @param insights the map of insights
     * @return a map with the words and their frequencies
     */
    @SuppressWarnings("unchecked")
    Map<String, Integer> getFrequency(Map<String, Object> insights) {
        final Map<String, Set<String>> words = ((Map<String, Set<String>>) insights.get(getInsightCode()));
        return Optional.ofNullable(words).orElse(emptyMap()).entrySet().stream()
                .collect(toMap(Map.Entry::getKey, e -> e.getValue().size()))
                .entrySet().parallelStream()
                .sorted(Map.Entry.<String, Integer> comparingByValue().reversed())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v2, LinkedHashMap::new));
    }

    /**
     * Returns a list with up to 10 insights
     *
     * @param frequency the map of frequencies
     * @return a list with examples of the generated insights
     */
    Map<String, Integer> getExamples(Map<String, Integer> frequency) {
        return frequency.entrySet().stream().limit(10)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
