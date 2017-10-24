package com.progresssoft.app.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Entity
public class ValidDeal implements Serializable {

	private static final long serialVersionUID = 1L;

	private static AtomicLong idGenerator = new AtomicLong(0);

	/**
	 * id
	 */
	@Id
	private Long id;

	/**
	 * Deal unique ID
	 */
	@NotNull(message = "Deal unique ID cannot be null")
	@Column(name = "deal_id", unique = true)
	private String dealId;

	/**
	 * From Currency ISO Code Ordering Currency
	 */
	@JoinColumn(name = "from_currency", referencedColumnName = "iso_code", nullable = false)
	@ManyToOne
	private Currency fromCurrency;

	/**
	 * To Currency ISO Code
	 */
	@JoinColumn(name = "to_currency", referencedColumnName = "iso_code", nullable = false)
	@ManyToOne
	private Currency toCurrency;

	/**
	 * Deal timestamp
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "deal_timestamp")
	@NotNull(message = "deal timestamp cannot be null")
	private Date timestamp;

	/**
	 * Deal Amount in ordering currency
	 */
	@Column(name = "deal_amount")
	@DecimalMin(value = "0", inclusive = false)
	@NotNull(message = "ValidDeal amount cannot be null")
	private BigDecimal amount;

	/**
	 * reference to source file name
	 */
	@JoinColumn(name = "filename", nullable = false)
	@ManyToOne
	private SourceFile sourceFile;
	// use in case of NumberFormatException | ParseException for time stamp type
	@Transient
	private String invalidTimestamp;

	// use in case of NumberFormatException for big decimal type
	@Transient
	private String invalidAmount;

	public ValidDeal() {
	}

	/**
	 * Constructor for setting the current max id value to the atomic long id
	 * generator
	 */
	public ValidDeal(long maxValue) {
		idGenerator = new AtomicLong(maxValue);
		this.id = idGenerator.incrementAndGet();
	}

	public ValidDeal(Long id, String dealId, Currency fromCurrency, Currency toCurrency, Date timestamp,
			BigDecimal amount) {
		this.id = id;
		this.dealId = dealId;
		this.fromCurrency = fromCurrency;
		this.toCurrency = toCurrency;
		this.timestamp = timestamp;
		this.amount = amount;
	}

	public ValidDeal(String dealId, Currency fromCurrency, Currency toCurrency, Date timestamp, BigDecimal amount) {
		this.id = idGenerator.incrementAndGet();
		this.dealId = dealId;
		this.fromCurrency = fromCurrency;
		this.toCurrency = toCurrency;
		this.timestamp = timestamp;
		this.amount = amount;
	}

	public ValidDeal(String dealId, Currency fromCurrency, Currency toCurrency, Date timestamp, BigDecimal amount,
			String invalidTimestamp, String invalidAmount) {
		this.id = idGenerator.incrementAndGet();
		this.dealId = dealId;
		this.fromCurrency = fromCurrency;
		this.toCurrency = toCurrency;
		this.timestamp = timestamp;
		this.amount = amount;
		this.invalidTimestamp = invalidTimestamp;
		this.invalidAmount = invalidAmount;
	}

	public ValidDeal(String dealId, Currency fromCurrency, Currency toCurrency, Date timestamp, BigDecimal amount,
			SourceFile sourceFile) {
		this.id = idGenerator.incrementAndGet();
		this.dealId = dealId;
		this.fromCurrency = fromCurrency;
		this.toCurrency = toCurrency;
		this.timestamp = timestamp;
		this.amount = amount;
		this.sourceFile = sourceFile;
	}

	public ValidDeal mapAddFileName(String filename) {
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

	public Currency getFromCurrency() {
		return fromCurrency;
	}

	public void setFromCurrency(Currency fromCurrency) {
		this.fromCurrency = fromCurrency;
	}

	public Currency getToCurrency() {
		return toCurrency;
	}

	public void setToCurrency(Currency toCurrency) {
		this.toCurrency = toCurrency;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public SourceFile getSourceFile() {
		return sourceFile;
	}

	public void setSourceFile(SourceFile sourceFile) {
		this.sourceFile = sourceFile;
	}

	public String getInvalidTimestamp() {
		return invalidTimestamp;
	}

	public void setInvalidTimestamp(String invalidTimestamp) {
		this.invalidTimestamp = invalidTimestamp;
	}

	public String getInvalidAmount() {
		return invalidAmount;
	}

	public void setInvalidAmount(String invalidAmount) {
		this.invalidAmount = invalidAmount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ValidDeal other = (ValidDeal) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
