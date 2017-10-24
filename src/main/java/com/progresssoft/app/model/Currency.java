package com.progresssoft.app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

@Entity
public class Currency implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="iso_code")
	private String isoCode;
	
	@Column(name="countOfDeals")
	private long countOfDeals;
	
	@OneToMany(mappedBy="fromCurrency")
	private List<ValidDeal> dealsOrderingCurrency = new ArrayList<>();
	
	@OneToMany(mappedBy="toCurrency")
	private List<ValidDeal> dealsToCurrency = new ArrayList<>();

	public Currency(){
		
	}
	
	@PrePersist
	public void prePersist(){
		countOfDeals = 0;
	}
	
	public Currency(String isoCode, long countOfDeals) {
		this.isoCode = isoCode;
		this.countOfDeals = countOfDeals;
	}

	public Currency(String isoCode) {
		this.isoCode = isoCode;
	}



	public String getIsoCode() {
		return isoCode;
	}

	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	public long getCountOfDeals() {
		return countOfDeals;
	}

	public void setCountOfDeals(long countOfDeals) {
		this.countOfDeals = countOfDeals;
	}

	public List<ValidDeal> getDealsOrderingCurrency() {
		return dealsOrderingCurrency;
	}

	public void setDealsOrderingCurrency(List<ValidDeal> dealsOrderingCurrency) {
		this.dealsOrderingCurrency = dealsOrderingCurrency;
	}

	public List<ValidDeal> getDealsToCurrency() {
		return dealsToCurrency;
	}

	public void setDealsToCurrency(List<ValidDeal> dealsToCurrency) {
		this.dealsToCurrency = dealsToCurrency;
	}

	@Override
	public String toString() {
		return isoCode;
	}
	
	
	
	

}
