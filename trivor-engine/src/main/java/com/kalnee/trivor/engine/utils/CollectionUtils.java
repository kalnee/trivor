package com.kalnee.trivor.engine.utils;

import java.util.Arrays;
import java.util.Collection;

public final class CollectionUtils {

  private CollectionUtils() {}

  public static boolean anyMatch(String input, Collection<String> items) {
    return items.stream().parallel().anyMatch(input::contains);
  }

  public static boolean noneMatch(String input, Collection<String> items) {
    return items.stream().parallel().noneMatch(input::contains);
  }

  public static boolean allMatch(String input, Collection<String> items) {
    return items.stream().allMatch(input::contains);
  }
}
