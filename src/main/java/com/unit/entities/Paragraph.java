package com.unit.entities;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Paragraph implements Comparable<Paragraph> {
	private Integer id;
	private Timestamp timestamp;
	private Content content;
	private String rawContent;
	private Integer index;

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getRawContent() {
		return rawContent;
	}

	public void setRawContent(String rawContent) {
		this.rawContent = rawContent;
	}

	private String translation;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTranslation() {
		return translation;
	}

	public void setTranslation(String translation) {
		this.translation = translation;
	}

	private Set<Phrase> set1 = new HashSet<>();

	public Set<Phrase> getSet1() {
		return set1;
	}

	public void setSet1(Set<Phrase> set1) {
		this.set1 = set1;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Paragraph() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Paragraph(Timestamp timestamp, Content content, Integer index) {
		super();
		this.timestamp = timestamp;
		this.content = content;
		this.index = index;

	}

	@Override
	public String toString() {
		return "Paragraph [ ]" + id;
	}

	@Override
	public int compareTo(Paragraph o) {
		// TODO Auto-generated method stub
		return this.index - o.index;
	}

}
