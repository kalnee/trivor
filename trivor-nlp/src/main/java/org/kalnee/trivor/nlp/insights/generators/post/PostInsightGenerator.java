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

package org.kalnee.trivor.nlp.insights.generators.post;


import org.kalnee.trivor.nlp.nlp.models.Insight;
import org.kalnee.trivor.nlp.nlp.models.Subtitle;

import java.util.Map;

/**
 * Generates a insight that should run after all other insights
 * have been processed for a subtitle.
 *
 * @since 0.0.1
 */

public interface PostInsightGenerator<T> {

    /**
     * Retuns a description for the insight
     *
     * @return a string with the description of the insight
     */
    default String getDescription() {
        return getCode();
    }

    /**
     * Returns the unique code of the insight
     *
     * @return a string with the code of the insight
     */
    String getCode();

    /**
     * Returns the generated insight
     *
     * @param subtitle the subtitle used to generate all insights
     * @param insights the insights already generated for a subtitle
     * @return an insight
     */
    Insight<T> getInsight(Subtitle subtitle, Map<String, Object> insights);

    /**
     * Returns if this insight should run or not
     *
     * @param subtitle the subtitle used to generate all insights
     * @param insights the insights already generated for a subtitle
     * @return true if the insight should run, false otherwise
     */
    default boolean shouldRun(Subtitle subtitle, Map<String, Object> insights) {
        return true;
    }
}
