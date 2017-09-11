package org.kalnee.trivor.insights.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.Map;

@Document(collection = "insights")
public class Insights {

	@Id
	private BigInteger id;

	private String imdbId;

	private BigInteger subtitleId;

	private Map<String, Object> insights;

	public Insights(String imdbId, BigInteger subtitleId, Map<String, Object> insights) {
		this.imdbId = imdbId;
		this.subtitleId = subtitleId;
		this.insights = insights;
	}

	Insights() {
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getImdbId() {
		return imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public BigInteger getSubtitleId() {
		return subtitleId;
	}

	public void setSubtitleId(BigInteger subtitleId) {
		this.subtitleId = subtitleId;
	}

	public Map<String, Object> getInsights() {
		return insights;
	}

	public void setInsights(Map<String, Object> insights) {
		this.insights = insights;
	}

	@Override
	public String toString() {
		return "Insights{" + "id=" + id + ", imdbId='" + imdbId + '\'' + ", subtitleId="
				+ subtitleId + ", insights=" + insights + '}';
	}
}
