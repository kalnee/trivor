package com.kalnee.trivor.engine.utils;

import java.io.IOException;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class TestUtils {

  private TestUtils() {}

  public static <T> T readSentences(String filename, Class<T> type) throws IOException {
    final String sentencesAsString =
        IOUtils.toString(TestUtils.class.getClassLoader().getResourceAsStream(filename));

    return new ObjectMapper().readValue(sentencesAsString, type);
  }

  public static <T> T readSentences(String filename, TypeReference<T> type) throws IOException {
    final String sentencesAsString =
        IOUtils.toString(TestUtils.class.getClassLoader().getResourceAsStream(filename));

    return new ObjectMapper().readValue(sentencesAsString, type);
  }
}
