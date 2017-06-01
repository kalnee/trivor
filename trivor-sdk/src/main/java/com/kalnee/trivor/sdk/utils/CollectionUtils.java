package com.kalnee.trivor.sdk.utils;

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
