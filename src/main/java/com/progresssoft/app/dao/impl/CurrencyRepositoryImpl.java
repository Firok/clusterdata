package com.progresssoft.app.dao.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.progresssoft.app.dao.CurrencyRepositoryNative;
import com.progresssoft.app.model.Currency;
import com.progresssoft.app.util.Config;

@Repository
public class CurrencyRepositoryImpl implements CurrencyRepositoryNative {

	private final Logger log = Logger.getLogger(CurrencyRepositoryImpl.class.getSimpleName());

	@PersistenceContext
	private EntityManager em;

	@Override
	public void insertAll(List<Currency> currencies) {
		Lists.partition(currencies, Config.PARTITION_SIZE).forEach(this::addAll);
	}

	private String mapToSqlFormat(Currency currency) {
		return "('" + currency.getIsoCode() + "','" + currency.getCountOfDeals() + "')";

	}

	private void addAll(List<Currency> currencies) {
		try {
			String values = currencies.stream().map(this::mapToSqlFormat).collect(Collectors.joining(","));
			em.createNativeQuery("INSERT INTO currency (iso_code, count_of_deals) VALUES " + values
					+ " ON DUPLICATE KEY UPDATE iso_code=VALUES(iso_code), count_of_deals=count_of_deals")
					.executeUpdate();
			em.flush();
			em.clear();
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
		}
	}

	private void updateCountOfDeals(List<Currency> orderingCurrencies) {
		try {
			String values = orderingCurrencies.stream().map(this::mapToSqlFormat).collect(Collectors.joining(","));
			em.createNativeQuery("INSERT INTO currency (iso_code, count_of_deals) VALUES " + values
					+ " ON DUPLICATE KEY UPDATE iso_code=VALUES(iso_code), count_of_deals=count_of_deals +1")
					.executeUpdate();
			em.flush();
			em.clear();
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
		}
	}

	@Override
	public void updateAll(List<Currency> orderingCurrencies) {
		Lists.partition(orderingCurrencies, Config.PARTITION_SIZE).forEach(this::updateCountOfDeals);
	}

}
