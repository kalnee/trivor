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

package org.kalnee.trivor.nlp.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class LanguageUtils {

	private LanguageUtils() {
	}

	public static final List<String> MOST_COMMON_WORDS = Collections.unmodifiableList(
		Arrays.asList("the", "be", "to", "of", "and", "a", "in", "that", "have", "i",
			"it", "for", "not", "on", "with", "he", "as", "you", "do", "at",
			"this", "but", "his", "by", "from", "they", "we", "say", "her", "she",
			"or", "an", "will", "my", "one", "all", "would", "there", "their",
			"what", "so", "up", "out", "if", "about", "who", "get", "which", "go",
			"me", "when", "make", "can", "like", "time", "no", "just", "him",
			"know", "take", "people", "into", "year", "your", "good", "some",
			"could", "them", "see", "other", "than", "then", "now", "look",
			"only", "come", "its", "over", "think", "also", "back", "after",
			"use", "two", "how", "our", "work", "first", "well", "way", "even",
			"new", "want", "because", "any", "these", "give", "day", "most",
			"us", "is", "are", "was", "got", "did", "had", "were", "am", "tell", "has")
	);

	public static final List<String> NOT_ADJECTIVES = Collections.unmodifiableList(
		Arrays.asList("nicky", "granddaughter", "daughter", "hightower")
	);
}
