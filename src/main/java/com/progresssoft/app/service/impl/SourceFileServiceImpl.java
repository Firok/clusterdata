package com.progresssoft.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.progresssoft.app.dao.SourceFileRepository;
import com.progresssoft.app.model.SourceFile;
import com.progresssoft.app.service.SourceFileService;

@Repository
@Transactional
public class SourceFileServiceImpl extends BaseServiceImpl<SourceFile, String> implements SourceFileService {

	@Autowired
	private SourceFileRepository sourceFileRepository;

	@Override
	protected JpaRepository<SourceFile, String> getRepository() {
		return sourceFileRepository;
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public SourceFile findByFileName(String FileName) {
		return sourceFileRepository.findByFileName(FileName);
	}

}
