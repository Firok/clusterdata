package com.progresssoft.app.service;

import org.springframework.stereotype.Service;

import com.progresssoft.app.model.SourceFile;

@Service
public interface SourceFileService extends BaseService<SourceFile, String>{
	
	/**
	 * Method repository to find SourceFile by File Name
	 * 
	 * @param FileName
	 * @return SourceFile or null if not found
	 */
	SourceFile findByFileName(String FileName);

}
