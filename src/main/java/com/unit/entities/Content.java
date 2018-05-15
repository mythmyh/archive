package com.unit.entities;

import java.sql.Timestamp;
import java.util.HashSet;

import java.util.Set;

public class Content {
	private Integer id;
	private String title;
	private String rawUrl;
	private Set<Paragraph> paragraphs = new HashSet<>();;

	private int totalParagraphs;

	public int getTotalParagraphs() {
		return totalParagraphs;
	}

	public void setTotalParagraphs(int totalParagraphs) {
		this.totalParagraphs = totalParagraphs;
	}

	private int totalPhrases;

	public int getTotalPhrases() {
		return totalPhrases;
	}

	public void setTotalPhrases(int totalPhrases) {
		this.totalPhrases = totalPhrases;
	}

	private Timestamp timestamp;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRawUrl() {
		return rawUrl;
	}

	public void setRawUrl(String rawUrl) {
		this.rawUrl = rawUrl;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Content() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Set<Paragraph> getParagraphs() {
		return paragraphs;
	}

	public void setParagraphs(Set<Paragraph> paragraphs) {
		this.paragraphs = paragraphs;
	}

	public Content(String title, String rawUrl, Timestamp timestamp) {
		super();
		this.title = title;
		this.rawUrl = rawUrl;
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "本文一共有 " + totalParagraphs + "个段落, " + totalPhrases + "个单词。";
	}

	

}
