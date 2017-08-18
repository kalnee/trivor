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

import java.util.Collection;

import static java.lang.String.format;

public final class CollectionUtils {

  private static final String WHOLE_WORD_REGEX = ".*%s(?![\\w]+).*";

  private CollectionUtils() {}

  public static boolean anyMatch(String input, Collection<String> items) {
    return items.stream().anyMatch(i -> input.matches(format(WHOLE_WORD_REGEX, i)));
  }

  public static boolean noneMatch(String input, Collection<String> items) {
    return items.stream().noneMatch(i -> input.matches(format(WHOLE_WORD_REGEX, i)));
  }

  public static boolean allMatch(String input, Collection<String> items) {
    return items.stream().allMatch(i -> input.matches(format(WHOLE_WORD_REGEX, i)));
  }

  public static boolean singleMatch(String input, Collection<String> items) {
    return items.stream().filter(i -> input.matches(format(WHOLE_WORD_REGEX, i))).count() == 1;
  }
}
