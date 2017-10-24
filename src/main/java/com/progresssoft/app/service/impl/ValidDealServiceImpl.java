package com.progresssoft.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.progresssoft.app.dao.ValidDealRepository;
import com.progresssoft.app.model.SourceFile;
import com.progresssoft.app.model.ValidDeal;
import com.progresssoft.app.service.ValidDealService;

@Repository
@Transactional
public class ValidDealServiceImpl extends BaseServiceImpl<ValidDeal, Long> implements ValidDealService {

	@Autowired
	private ValidDealRepository validDealRepository;

	@Override
	protected JpaRepository<ValidDeal, Long> getRepository() {
		return validDealRepository;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page<ValidDeal> findBySourceFile(SourceFile sourceFile, Pageable pageable) {
		return validDealRepository.findBySourceFile(sourceFile, pageable);
	}

	@Override
	public void insertAll(List<ValidDeal> validDeals) {
		validDealRepository.insertAll(validDeals);
	}

	@Override
	public long getMaxIdValue() {
		try {
			return validDealRepository.getMaxIdValue();
		} catch (Exception e) {
			return 0;
		}
	}

}
