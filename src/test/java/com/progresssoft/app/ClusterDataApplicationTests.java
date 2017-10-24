package com.progresssoft.app;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.progresssoft.app.model.Currency;
import com.progresssoft.app.model.ValidDeal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClusterDataApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void removeDuplicateDealUniqueId() {

		// init atomic long generic value
		ValidDeal val = new ValidDeal(0);

		assertThat(1L).isEqualTo(val.getId());

		// given all deals
		List<ValidDeal> allDeals = Arrays.asList(
				new ValidDeal("80", new Currency("XOF"), new Currency("USD"), null, new BigDecimal(50000)),
				new ValidDeal("81", new Currency("AED"), new Currency("USD"), null, new BigDecimal(51000)),
				new ValidDeal("82", new Currency("XOF"), new Currency("AED"), null, new BigDecimal(50000)),
				new ValidDeal("83", new Currency("AED"), new Currency("USD"), null, new BigDecimal(51000)),
				new ValidDeal("80", new Currency("XOF"), new Currency("USD"), null, new BigDecimal(50000)),
				new ValidDeal("85", new Currency("AED"), new Currency("USD"), null, new BigDecimal(51000)),
				new ValidDeal("86", new Currency("INR"), new Currency("AED"), null, new BigDecimal(50000)),
				new ValidDeal("81", new Currency("INR"), new Currency("XOF"), null, new BigDecimal(51000)),
				new ValidDeal("86", new Currency("EUR"), new Currency("AED"), null, new BigDecimal(50000)),
				new ValidDeal("82", new Currency("AED"), new Currency("USD"), null, new BigDecimal(51000)));

		// remove duplicate values for unique deal id
		List<ValidDeal> validDeals = allDeals.stream()
				.collect(Collectors.collectingAndThen(
						Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(ValidDeal::getDealId))),
						ArrayList<ValidDeal>::new));

		// given expected values
		List<ValidDeal> expectedValues = Arrays.asList(
				new ValidDeal(2L, "80", new Currency("XOF"), new Currency("USD"), null, new BigDecimal(50000)),
				new ValidDeal(3L, "81", new Currency("AED"), new Currency("USD"), null, new BigDecimal(51000)),
				new ValidDeal(4L, "82", new Currency("XOF"), new Currency("AED"), null, new BigDecimal(50000)),
				new ValidDeal(5L, "83", new Currency("AED"), new Currency("USD"), null, new BigDecimal(51000)),
				new ValidDeal(7L, "85", new Currency("AED"), new Currency("USD"), null, new BigDecimal(51000)),
				new ValidDeal(8L, "86", new Currency("INR"), new Currency("AED"), null, new BigDecimal(50000)));

		assertThat(expectedValues).isEqualTo(validDeals);

	}

}
