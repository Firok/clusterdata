package com.progresssoft.app.service.impl;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.progresssoft.app.service.BaseService;

public abstract class BaseServiceImpl<T, K extends Serializable> implements BaseService<T, K> {

	protected abstract JpaRepository<T, K> getRepository();

	@Override
	public T find(K k) {
		return getRepository().findOne(k);
	}

	@Override
	public T createOrUpdate(T t) {
		return getRepository().save(t);
	}

}
