package com.progresssoft.app.dao;

import java.util.List;

import com.progresssoft.app.model.ValidDeal;

public interface ValidDealRepositoryNative {
	
	/**
	 * save list of deals by create native query
	 * 
	 * @param validDeals
	 */
	void insertAll(List<ValidDeal> validDeals);

}
