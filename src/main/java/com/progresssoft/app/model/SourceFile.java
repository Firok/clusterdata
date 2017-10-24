package com.progresssoft.app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class SourceFile implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * source file name id
	 */
	@Id
	@Column(name = "filename")
	private String fileName;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@OneToMany(mappedBy = "sourceFile")
	private List<ValidDeal> validDeals = new ArrayList<>();

	@OneToMany(mappedBy = "sourceFile")
	private List<InvalidDeal> invalidDeals = new ArrayList<>();

	public SourceFile() {
	}

	public SourceFile(String fileName) {
		this.fileName = fileName;
	}

	public SourceFile(String fileName, Date createdDate) {
		this.fileName = fileName;
		this.createdDate = createdDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public List<ValidDeal> getDeals() {
		return validDeals;
	}

	public void setDeals(List<ValidDeal> validDeals) {
		this.validDeals = validDeals;
	}

	public List<InvalidDeal> getInvalidDeals() {
		return invalidDeals;
	}

	public void setInvalidDeals(List<InvalidDeal> invalidDeals) {
		this.invalidDeals = invalidDeals;
	}

}
