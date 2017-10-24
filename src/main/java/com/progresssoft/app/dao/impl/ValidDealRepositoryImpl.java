package com.progresssoft.app.dao.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.progresssoft.app.dao.ValidDealRepositoryNative;
import com.progresssoft.app.model.ValidDeal;
import com.progresssoft.app.util.Config;

@Repository
public class ValidDealRepositoryImpl implements ValidDealRepositoryNative{
	
	private final Logger log = Logger.getLogger(ValidDealRepositoryImpl.class.getSimpleName());
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public void insertAll(List<ValidDeal> validDeals) {
		Lists.partition(validDeals, Config.PARTITION_SIZE).forEach(this::addAll);
	}
	
	private String mapToSqlFormat(ValidDeal validDeal){
		return "("+ validDeal.getId()+ ",'"+ validDeal.getDealId() + "','"+ validDeal.getFromCurrency().getIsoCode() +"','" + validDeal.getToCurrency().getIsoCode() + "','"+ Config.DATE_FORMAT.format(validDeal.getTimestamp()) +"'," + validDeal.getAmount() +" ,'"+ validDeal.getSourceFile().getFileName() +"')";
		
	}
	
	private void addAll(List<ValidDeal> validDeals){
		try{
			String values = validDeals.stream().map(this::mapToSqlFormat).collect(Collectors.joining(","));
			em.createNativeQuery("INSERT INTO valid_deal (id, deal_id, from_currency, to_currency, deal_timestamp, deal_amount, filename) VALUES " + values + " ON DUPLICATE KEY UPDATE id=VALUES(id), deal_id=VALUES(deal_id)").executeUpdate();
			em.flush();
			em.clear();
		}
		catch(Exception e){
			log.log(Level.SEVERE,e.getMessage());
		}
	}

}
