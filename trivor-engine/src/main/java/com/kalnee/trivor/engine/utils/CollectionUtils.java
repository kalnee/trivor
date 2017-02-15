package com.kalnee.trivor.engine.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.kalnee.trivor.engine.utils.TagsEnum.*;
import static java.lang.String.format;

public final class CollectionUtils {

  private static final String WHOLE_WORD_REGEX = ".*%s(?![\\w]+).*";

  private CollectionUtils() {}

  public static boolean anyMatch(String input, Collection<String> items) {
    return items.stream().parallel().anyMatch(i -> input.matches(format(WHOLE_WORD_REGEX, i)));
  }

  public static boolean noneMatch(String input, Collection<String> items) {
    return items.stream().parallel().noneMatch(i -> input.matches(format(WHOLE_WORD_REGEX, i)));
  }

  public static boolean allMatch(String input, Collection<String> items) {
    return items.stream().allMatch(i -> input.matches(format(WHOLE_WORD_REGEX, i)));
  }
}
