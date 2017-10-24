package com.progresssoft.app.dao.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.progresssoft.app.dao.InvalidDealRepositoryNative;
import com.progresssoft.app.model.InvalidDeal;
import com.progresssoft.app.util.Config;

@Repository
public class InvalidDealRepositoryImpl implements InvalidDealRepositoryNative {

	private final Logger log = Logger.getLogger(InvalidDealRepositoryImpl.class.getSimpleName());

	@PersistenceContext
	private EntityManager em;

	@Override
	public void insertAll(List<InvalidDeal> invalidDeals) {
		Lists.partition(invalidDeals, Config.PARTITION_SIZE).forEach(this::addAll);
	}

	private String mapToSqlFormat(InvalidDeal invalidDeal) {
		return "('"+ invalidDeal.getId() +"','" + invalidDeal.getDealId() + "','" + invalidDeal.getFromCurrency() + "','"
				+ invalidDeal.getToCurrency() + "','" + invalidDeal.getTimeStamp() + "','" + invalidDeal.getAmount()
				+ "','" + invalidDeal.getSourceFile().getFileName() + "')";

	}

	private void addAll(List<InvalidDeal> invalidDeals) {
		try {
			String values = invalidDeals.stream().map(this::mapToSqlFormat).collect(Collectors.joining(","));
			em.createNativeQuery(
					"INSERT INTO invalid_deal (id, deal_id, from_currency, to_currency, time_stamp, amount,filename) VALUES "
							+ values
							+ " ON DUPLICATE KEY UPDATE id=VALUES(id), deal_id=VALUES(deal_id), from_currency=VALUES(from_currency), to_currency=VALUES(to_currency), time_stamp=VALUES(time_stamp), amount=VALUES(amount)")
					.executeUpdate();
			em.flush();
			em.clear();
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
		}
	}

}
