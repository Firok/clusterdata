package com.progresssoft.app.model;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class InvalidDeal implements Serializable {

	private static final long serialVersionUID = 1L;

	private static AtomicLong idGenerator = new AtomicLong(0);

	/**
	 * id
	 */
	@Id
	private Long id;

	private String dealId;

	private String fromCurrency;

	private String toCurrency;

	private String timeStamp;

	private String amount;

	/**
	 * reference to source file name
	 */
	@JoinColumn(name = "filename", nullable = false)
	@ManyToOne
	private SourceFile sourceFile;

	public InvalidDeal() {
	}

	public InvalidDeal(long maxValue) {
		idGenerator = new AtomicLong(maxValue);
		this.id = idGenerator.incrementAndGet();
	}

	public InvalidDeal(String dealId, String fromCurrency, String toCurrency, String timeStamp, String amount) {
		this.id = idGenerator.incrementAndGet();
		this.dealId = dealId;
		this.fromCurrency = fromCurrency;
		this.toCurrency = toCurrency;
		this.timeStamp = timeStamp;
		this.amount = amount;
	}

	public InvalidDeal(String dealId, String fromCurrency, String toCurrency, String timeStamp, String amount,
			SourceFile sourceFile) {
		this.id = idGenerator.incrementAndGet();
		this.dealId = dealId;
		this.fromCurrency = fromCurrency;
		this.toCurrency = toCurrency;
		this.timeStamp = timeStamp;
		this.amount = amount;
		this.sourceFile = sourceFile;
	}

	public InvalidDeal mapAddFileName(String filename) {
		this.sourceFile = new SourceFile(filename);
		return this;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDealId() {
		return dealId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	public String getFromCurrency() {
		return fromCurrency;
	}

	public void setFromCurrency(String fromCurrency) {
		this.fromCurrency = fromCurrency;
	}

	public String getToCurrency() {
		return toCurrency;
	}

	public void setToCurrency(String toCurrency) {
		this.toCurrency = toCurrency;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public SourceFile getSourceFile() {
		return sourceFile;
	}

	public void setSourceFile(SourceFile sourceFile) {
		this.sourceFile = sourceFile;
	}

}
