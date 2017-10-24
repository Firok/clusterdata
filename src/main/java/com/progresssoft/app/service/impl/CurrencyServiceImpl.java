package com.progresssoft.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.progresssoft.app.dao.CurrencyRepository;
import com.progresssoft.app.model.Currency;
import com.progresssoft.app.service.CurrencyService;

@Repository
@Transactional
public class CurrencyServiceImpl extends BaseServiceImpl<Currency, String> implements CurrencyService {

	@Autowired
	private CurrencyRepository currencyRepository;

	@Override
	protected JpaRepository<Currency, String> getRepository() {
		return currencyRepository;
	}

	@Override
	public void insertAll(List<Currency> currencies) {
		currencyRepository.insertAll(currencies);

	}

	@Override
	public void updateAll(List<Currency> orderingCurrencies) {
		currencyRepository.updateAll(orderingCurrencies);

	}

}
