package com.progresssoft.app.service;

import java.io.Serializable;

public interface BaseService<T, K extends Serializable> {

	/**
	 * find by id
	 * @param k id value 
	 * @return
	 */
	T find(K k);

	/**
	 * save or update a record
	 * @param t entity value
	 * @return
	 */
	T createOrUpdate(T t);

}
