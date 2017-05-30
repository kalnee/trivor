package com.kalnee.trivor.sdk.utils;

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
