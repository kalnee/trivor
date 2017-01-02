package com.kalnee.trivor.engine.models;

public class Token {
	private String token;
	private String tag;
	private Double prob;

	public Token() {
	}

	public Token(String token, String tag, Double prob) {
		this.token = token;
		this.tag = tag;
		this.prob = prob;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Double getProb() {
		return prob;
	}

	public void setProb(Double prob) {
		this.prob = prob;
	}
}
