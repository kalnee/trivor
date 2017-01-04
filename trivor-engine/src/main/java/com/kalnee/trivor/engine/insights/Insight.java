package com.kalnee.trivor.engine.insights;

public interface Insight<T> {
	String getDescription();

	String getCode();

  T getValue();

  void generate();
}
