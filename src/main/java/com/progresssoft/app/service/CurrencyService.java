package com.progresssoft.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.progresssoft.app.model.Currency;

@Service
public interface CurrencyService extends BaseService<Currency, String> {

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
