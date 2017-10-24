package com.progresssoft.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.progresssoft.app.model.Currency;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, String>, CurrencyRepositoryNative {

}
