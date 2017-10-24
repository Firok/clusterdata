package com.progresssoft.app.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.progresssoft.app.model.ValidDeal;
import com.progresssoft.app.model.SourceFile;

@Repository
public interface ValidDealRepository extends JpaRepository<ValidDeal, Long>, ValidDealRepositoryNative {

	/**
	 * Method repository to find all valid deal by source file name
	 * 
	 * @param sourceFile
	 * @param pageable
	 * @return
	 */
	Page<ValidDeal> findBySourceFile(SourceFile sourceFile, Pageable pageable);
	
	/**
	 * get max id value 
	 * @return
	 */
	@Query("SELECT MAX(id) FROM ValidDeal")
	long getMaxIdValue();
}
