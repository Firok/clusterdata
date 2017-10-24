package com.progresssoft.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.progresssoft.app.model.SourceFile;
import com.progresssoft.app.model.ValidDeal;

@Service
public interface ValidDealService extends BaseService<ValidDeal, Long> {

	/**
	 * Method repository to find all valid deal by source file name
	 * 
	 * @param sourceFile
	 * @param pageable
	 * @return
	 */
	Page<ValidDeal> findBySourceFile(SourceFile sourceFile, Pageable pageable);

	/**
	 * save list of deals by create native query
	 * 
	 * @param validDeals
	 */
	void insertAll(List<ValidDeal> validDeals);
	
	long getMaxIdValue();
}
