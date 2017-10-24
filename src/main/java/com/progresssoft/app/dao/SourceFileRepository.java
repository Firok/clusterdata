package com.progresssoft.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.progresssoft.app.model.SourceFile;

@Repository
public interface SourceFileRepository extends JpaRepository<SourceFile, String> {

	/**
	 * Method repository to find SourceFile by File Name
	 * 
	 * @param FileName
	 * @return SourceFile or null if not found
	 */
	SourceFile findByFileName(String FileName);

}
