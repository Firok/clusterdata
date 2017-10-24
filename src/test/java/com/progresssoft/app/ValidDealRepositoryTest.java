package com.progresssoft.app;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.progresssoft.app.dao.CurrencyRepository;
import com.progresssoft.app.dao.SourceFileRepository;
import com.progresssoft.app.dao.ValidDealRepository;
import com.progresssoft.app.model.Currency;
import com.progresssoft.app.model.SourceFile;
import com.progresssoft.app.model.ValidDeal;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ClusterDataApplication.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional
public class ValidDealRepositoryTest {

	private final Logger log = Logger.getLogger(ValidDealRepositoryTest.class.getSimpleName());

	@Autowired
	private ValidDealRepository validDealRepository;

	@Autowired
	private CurrencyRepository currencyRepository;

	@Autowired
	private SourceFileRepository sourceFileRepository;

	@Test
	@Before
	@Rollback(false)
	public void insertAllValidDealNativeQuery() {
		boolean status = false;

		try {
			// given currencies
			List<Currency> currencies = Arrays.asList(new Currency("XOF", 0), new Currency("AED", 0),
					new Currency("AUD", 0), new Currency("USD", 0), new Currency("EUR", 0), new Currency("INR", 0),
					new Currency("ILS", 0));

			// given file name
			SourceFile sourceFile = new SourceFile("myDeal.csv", new Date());
			sourceFileRepository.save(sourceFile);

			ValidDeal validDeal = null;
			// init id generator
			long max = 0;
			try {
				validDeal = new ValidDeal(validDealRepository.getMaxIdValue());
				max = validDeal.getId();
			} catch (Exception e) {
				log.log(Level.INFO, "Empty table!");
			}

			if (max == 0)
				validDeal = new ValidDeal(max);

			log.log(Level.INFO, "Max id value for valid deal = {0}", validDeal.getId());

			// given valid deals
			List<ValidDeal> validDeals = Arrays.asList(
					new ValidDeal("80", new Currency("XOF"), new Currency("USD"), new Date(), new BigDecimal(50000),
							sourceFile),
					new ValidDeal("81", new Currency("AED"), new Currency("USD"), new Date(), new BigDecimal(51000),
							sourceFile),
					new ValidDeal("82", new Currency("XOF"), new Currency("AED"), new Date(), new BigDecimal(50000),
							sourceFile),
					new ValidDeal("83", new Currency("AED"), new Currency("USD"), new Date(), new BigDecimal(51000),
							sourceFile),
					new ValidDeal("84", new Currency("XOF"), new Currency("USD"), new Date(), new BigDecimal(50000),
							sourceFile),
					new ValidDeal("85", new Currency("AED"), new Currency("USD"), new Date(), new BigDecimal(51000),
							sourceFile),
					new ValidDeal("86", new Currency("INR"), new Currency("AED"), new Date(), new BigDecimal(50000),
							sourceFile),
					new ValidDeal("87", new Currency("INR"), new Currency("XOF"), new Date(), new BigDecimal(51000),
							sourceFile),
					new ValidDeal("89", new Currency("EUR"), new Currency("AED"), new Date(), new BigDecimal(50000),
							sourceFile),
					new ValidDeal("90", new Currency("AED"), new Currency("USD"), new Date(), new BigDecimal(51000),
							sourceFile));

			// given ordering currencies
			List<Currency> orderingCurrencies = Arrays.asList(new Currency("XOF"), new Currency("AED"),
					new Currency("XOF"), new Currency("AED"), new Currency("XOF"), new Currency("AED"),
					new Currency("INR"), new Currency("INR"), new Currency("EUR"), new Currency("AED"));

			// start insertion
			// insert all currencies
			currencyRepository.insertAll(currencies);

			// insert all valid deals
			validDealRepository.insertAll(validDeals);

			// update orderingCurrencies
			currencyRepository.updateAll(orderingCurrencies);

			// update status
			status = true;
		} catch (Exception e) {
			log.severe(e.getMessage());
		}

		assertThat(true).isEqualTo(status);

	}

	@Test
	public void whenInsertionPassReturnCountOfDeal() {

		// expected count of deals of AED
		long expectedCountOfDeals = 4;

		long outputCountOfDeals = 0;
		try {
			// when
			Currency found = currencyRepository.findOne("AED");

			outputCountOfDeals = found.getCountOfDeals();
			
			log.log(Level.INFO, "expectedCountOfDeals {0}",outputCountOfDeals);

		} catch (Exception e) {
			log.severe(e.getMessage());
		}

		assertThat(expectedCountOfDeals).isEqualTo(outputCountOfDeals);
	}

}
