package com.progresssoft.app.dao;

import java.util.List;

import com.progresssoft.app.model.InvalidDeal;

public interface InvalidDealRepositoryNative {
	
	/**
	 * save list of invalid Deals by create native query
	 * 
	 * @param invalidDeals
	 */
	void insertAll(List<InvalidDeal> invalidDeals);

}
