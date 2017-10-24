package com.progresssoft.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.progresssoft.app.model.InvalidDeal;
import com.progresssoft.app.model.SourceFile;

@Service
public interface InvalidDealService extends BaseService<InvalidDeal, Long> {

	/**
	 * save list of invalid Deals by create native query
	 * 
	 * @param invalidDeals
	 */
	void insertAll(List<InvalidDeal> invalidDeals);

	/**
	 * Method repository to find all invalid deal by source file name
	 * 
	 * @param sourceFile
	 * @param pageable
	 * @return
	 */
	Page<InvalidDeal> findBySourceFile(SourceFile sourceFile, Pageable pageable);
	

	/**
	 * get max id value 
	 * @return
	 */
	@Query("SELECT MAX(id) FROM InvalidDeal")
	long getMaxIdValue();

}
