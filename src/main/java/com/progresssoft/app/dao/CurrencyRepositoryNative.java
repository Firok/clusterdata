package com.progresssoft.app.dao;

import java.util.List;

import com.progresssoft.app.model.Currency;

public interface CurrencyRepositoryNative {
	
	/**
	 * save list of Currency by create native query
	 * 
	 * @param currencies
	 */
	void insertAll(List<Currency> currencies);

	/**
	 * update count of deals for ordering Currency by create native query
	 * 
	 * @param orderingCurrencies
	 */
	void updateAll(List<Currency> orderingCurrencies);

}
