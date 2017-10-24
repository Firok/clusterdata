package com.progresssoft.app;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.progresssoft.app.dao.CurrencyRepository;
import com.progresssoft.app.model.Currency;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ClusterDataApplication.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CurrencyRepositoryTest {

	private final Logger log = Logger.getLogger(CurrencyRepositoryTest.class.getSimpleName());

	@Autowired
	private CurrencyRepository currencyRepository;

	@Test
	@Before
	public void insertAllCurrencyNativeQuery() {
		// given
		List<Currency> currencies = Arrays.asList(new Currency("XOF", 0), new Currency("AED", 0),
				new Currency("AUD", 0), new Currency("EUR", 0), new Currency("INR", 0), new Currency("ILS", 0));

		// insert all
		currencyRepository.insertAll(currencies);

		// when
		Currency found = currencyRepository.findOne(currencies.get(0).getIsoCode());

		assertThat(found.getIsoCode()).isEqualTo(currencies.get(0).getIsoCode());

	}

	@Test
	public void whenValidISOCodethenReturnCurrency() {

		Currency expectedCurrency = new Currency("AED");
		Currency found = new Currency(" ");
		try {
			// when
			found = currencyRepository.findOne(expectedCurrency.getIsoCode());
		} catch (Exception e) {
			log.severe(e.getMessage());
		}
		assertThat(found.getIsoCode()).isEqualTo(expectedCurrency.getIsoCode());
	}


}
