package com.kalnee.trivor.engine.models;

import java.util.List;

public class Sentence {

	private String sentence;
	private List<Token> tokens;

	public Sentence() {
	}

	public Sentence(String sentence, List<Token> tokens) {
		this.sentence = sentence;
		this.tokens = tokens;
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public List<Token> getTokens() {
		return tokens;
	}

	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}

	@Override
	public String toString() {
		return "Sentence{" + "sentence='" + sentence + '\'' + ", tokens=" + tokens + '}';
	}
}
