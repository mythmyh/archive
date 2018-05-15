package com.unit.entities;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class Phrase {
	private String word;
	private String translate_in;
	private boolean singleWord = true;
	private Timestamp timestamp;


	public boolean isSingleWord() {
		return singleWord;
	}

	public void setSingleWord(boolean singleWord) {
		this.singleWord = singleWord;
	}

	private Set<Paragraph> paragraphs = new HashSet<>();

	public Phrase() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Phrase(String word, String translate_in, Timestamp timestamp) {
		super();
		this.word = word;
		this.translate_in = translate_in;
		this.timestamp = timestamp;
	}

	public Set<Paragraph> getParagraphs() {
		return paragraphs;
	}

	public void setParagraphs(Set<Paragraph> paragraphs) {
		this.paragraphs = paragraphs;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getTranslate_in() {
		return translate_in;
	}

	public void setTranslate_in(String translate_in) {
		this.translate_in = translate_in;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Phrase [word=" + word + ", translate_in=" + translate_in + ", isSingleword=" + singleWord
				+ ", timestamp=" + timestamp + ", paragraphs=" + paragraphs + "]";
	}


}
