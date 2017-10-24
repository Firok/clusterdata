package com.progresssoft.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.progresssoft.app.dao.InvalidDealRepository;
import com.progresssoft.app.model.InvalidDeal;
import com.progresssoft.app.model.SourceFile;
import com.progresssoft.app.service.InvalidDealService;

@Repository
@Transactional
public class InvalidDealServiceImpl extends BaseServiceImpl<InvalidDeal, Long> implements InvalidDealService {

	@Autowired
	private InvalidDealRepository invalidDealRepository;

	@Override
	protected JpaRepository<InvalidDeal, Long> getRepository() {
		return invalidDealRepository;
	}

	@Override
	public void insertAll(List<InvalidDeal> invalidDeals) {
		invalidDealRepository.insertAll(invalidDeals);
	}

	@Override
	public Page<InvalidDeal> findBySourceFile(SourceFile sourceFile, Pageable pageable) {
		return invalidDealRepository.findBySourceFile(sourceFile, pageable);
	}
	
	@Override
	public long getMaxIdValue() {
		try {
			return invalidDealRepository.getMaxIdValue();
		} catch (Exception e) {
			return 0;
		}
	}

}
