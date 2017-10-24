package com.progresssoft.app.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.progresssoft.app.model.InvalidDeal;
import com.progresssoft.app.model.SourceFile;

@Repository
public interface InvalidDealRepository extends JpaRepository<InvalidDeal, Long>, InvalidDealRepositoryNative {

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
