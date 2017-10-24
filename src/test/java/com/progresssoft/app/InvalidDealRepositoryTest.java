package com.progresssoft.app;

import static org.assertj.core.api.Assertions.assertThat;

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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.progresssoft.app.dao.InvalidDealRepository;
import com.progresssoft.app.dao.SourceFileRepository;
import com.progresssoft.app.model.InvalidDeal;
import com.progresssoft.app.model.SourceFile;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ClusterDataApplication.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional
public class InvalidDealRepositoryTest {

	private final Logger log = Logger.getLogger(InvalidDealRepositoryTest.class.getSimpleName());

	@Autowired
	private InvalidDealRepository invalidDealRepository;

	@Autowired
	private SourceFileRepository sourceFileRepository;

	@Test
	@Before
	public void insertAllInvalidDealNativeQuery() {
		boolean status = false;

		try {
			// given file name
			SourceFile sourceFile = new SourceFile("deal_of_deal.csv", new Date());
			sourceFileRepository.save(sourceFile);

			InvalidDeal invalidDeal = null;

			// init id generator
			long max = 0;
			try {
				invalidDeal = new InvalidDeal(invalidDealRepository.getMaxIdValue());
				max = invalidDeal.getId();
			} catch (Exception e) {
				log.log(Level.INFO, "Empty table!");
			}

			if (max == 0)
				invalidDeal = new InvalidDeal(max);

			log.log(Level.INFO, "Max id value for valid deal = {0}", invalidDeal.getId());

			// given valid deals
			List<InvalidDeal> invalidDeals = Arrays.asList(new InvalidDeal("80", "", "", "", null, sourceFile),
					new InvalidDeal("", "", "", "", null, sourceFile),
					new InvalidDeal("80", "AED", "AED", "25-10-200 15:30", "204540", sourceFile),
					new InvalidDeal("94", "", "EUR", "", null, sourceFile),
					new InvalidDeal("", "", "GPD", "", null, sourceFile),
					new InvalidDeal("", "USD", "", "", "", sourceFile),
					new InvalidDeal("45", "", "XOF", "", null, sourceFile),
					new InvalidDeal("", "", "", "25-10-200 15:30", "", sourceFile),
					new InvalidDeal("", "", "", "", null, sourceFile),
					new InvalidDeal("80", "AED", "AED", "25-10-200 15:30", "204540", sourceFile));

			// insert all invalid deals
			invalidDealRepository.insertAll(invalidDeals);

			// update status
			status = true;
		} catch (Exception e) {
			log.severe(e.getMessage());
		}

		assertThat(true).isEqualTo(status);

	}
}
