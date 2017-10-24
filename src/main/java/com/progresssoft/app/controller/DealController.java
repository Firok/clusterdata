package com.progresssoft.app.controller;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.progresssoft.app.model.Currency;
import com.progresssoft.app.model.InvalidDeal;
import com.progresssoft.app.model.SourceFile;
import com.progresssoft.app.model.ValidDeal;
import com.progresssoft.app.service.CurrencyService;
import com.progresssoft.app.service.InvalidDealService;
import com.progresssoft.app.service.SourceFileService;
import com.progresssoft.app.service.ValidDealService;
import com.progresssoft.app.util.Config;
import com.progresssoft.app.util.Utilities;

@Controller
@RequestMapping(value = { "/", "/clusterdata" })
public class DealController {

	int i = 0;

	private final Logger log = Logger.getLogger(DealController.class.getSimpleName());

	@Autowired
	private ValidDealService validDealService;

	@Autowired
	private InvalidDealService invalidDealService;

	@Autowired
	private CurrencyService currencyService;

	@Autowired
	private SourceFileService sourceFileService;

	/**
	 * Map CSV line to deal structure
	 */
	private Function<String, ValidDeal> mapToDealFields = (line) -> {

		// convert String UTF8 format
		line = Utilities.convertStringUTF8(line);

		// split CSV line with semicolon
		String[] csvline = line.split(Config.CSV_SPLIT_BY);

		ValidDeal validDeal;

		try {
			validDeal = new ValidDeal(csvline[0], new Currency(csvline[1]), new Currency(csvline[2]),
					csvline[3].isEmpty() ? null : Config.DATE_FORMAT.parse(csvline[3]),
					csvline[4].isEmpty() ? null : new BigDecimal(csvline[4]));
		} catch (NumberFormatException | ParseException e) {
			// Date Format exception
			validDeal = new ValidDeal(csvline[0], new Currency(csvline[1]), new Currency(csvline[2]), null, null,
					csvline[3], csvline[4]);

			return validDeal;
		}

		return validDeal;
	};

	/**
	 * Filter for valid deal
	 */
	private Predicate<ValidDeal> filterValidDeal = new Predicate<ValidDeal>() {
		@Override
		public boolean test(ValidDeal t) {
			return (!t.getDealId().isEmpty() && !t.getFromCurrency().getIsoCode().isEmpty()
					&& !t.getToCurrency().getIsoCode().isEmpty()
					&& !t.getFromCurrency().getIsoCode().equals(t.getToCurrency().getIsoCode())
					&& t.getTimestamp() != null && t.getAmount() != null);
		}

	};

	/**
	 * Map deal to invalid deal structure
	 */
	private Function<ValidDeal, InvalidDeal> mapToInvaliDealFields = (deal) -> {

		InvalidDeal invaliDeal = new InvalidDeal(deal.getDealId(), deal.getFromCurrency().getIsoCode(),
				deal.getToCurrency().getIsoCode(), deal.getInvalidTimestamp(), deal.getInvalidAmount(),
				deal.getSourceFile());

		return invaliDeal;
	};

	/**
	 * get upload page
	 * 
	 * @param inquire
	 *            for search about result
	 * @param model
	 *            for set all the model attribute contents
	 * @param pageable
	 *            for pagination
	 * @param redirectAttributes
	 *            for redirection for attribute
	 * @return
	 */
	@GetMapping
	public String getUploadPage(@RequestParam(value = "inquire", required = false, defaultValue = "") String inquire,
			Model model, Pageable pageable, RedirectAttributes redirectAttributes) {

		if (inquire.isEmpty() || inquire == null) {
			model.addAttribute("inquire", inquire);
		} else {
			SourceFile sourceFile = sourceFileService.findByFileName(inquire);

			if (sourceFile == null) {
				redirectAttributes.addFlashAttribute("message",
						"This file name does not exists in the DB. Make sure to enter an existing file name!");
				return "redirect:/";
			}

			// Get valid deal page list
			Page<ValidDeal> validDealPage = validDealService.findBySourceFile(sourceFile, pageable);
			// Wrapper valid deal page list
			PageWrapper<ValidDeal> pageValidDeal = new PageWrapper<>(validDealPage, "/clusterdata?inquire=" + inquire);

			// Get invalid deal page list
			Page<InvalidDeal> invalidDealPage = invalidDealService.findBySourceFile(sourceFile, pageable);
			// Wrapper invalid deal page list
			PageWrapper<InvalidDeal> pageInvalidDeal = new PageWrapper<>(invalidDealPage,
					"/clusterdata?inquire=" + inquire);

			model.addAttribute("validDeals", pageValidDeal.getContent());
			model.addAttribute("page", pageValidDeal);
			model.addAttribute("invalidDeals", pageInvalidDeal.getContent());
			model.addAttribute("pageInvalid", pageInvalidDeal);
			model.addAttribute("inquire", inquire);
		}
		return "upload";
	}

	/**
	 * Handle CSV File
	 * 
	 * @param multipartFile
	 * @param redirectAttributes
	 * @return
	 */
	@PostMapping
	public String handleUploadFile(@RequestParam("multipartFile") MultipartFile multipartFile,
			RedirectAttributes redirectAttributes) {

		if (multipartFile.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "redirect:/";
		}

		// check if file already exists in archive as the system should not
		// import same file twice
		SourceFile sourceFile = sourceFileService.findByFileName(multipartFile.getOriginalFilename());
		if (Utilities.isFileExists(multipartFile) || sourceFile != null) {
			redirectAttributes.addFlashAttribute("message",
					"This File " + multipartFile.getOriginalFilename() + " was already uploaded");
			log.log(Level.INFO, "File {0} already imported before", multipartFile.getOriginalFilename());
		} else {
			// if not exists
			try {
				// get file
				File file = Utilities.convertMultipartFile(multipartFile);

				// before starting getting all deals in the file
				// let's initialize the max id value for valid deal table and invalid deal table
				// init id generator

				// for valid deal
				ValidDeal validDeal = new ValidDeal(validDealService.getMaxIdValue());
				log.log(Level.INFO, "Max id value for valid deal", validDeal.getId());

				// for invalid deal
				InvalidDeal invalidDeal = new InvalidDeal(invalidDealService.getMaxIdValue());
				log.log(Level.INFO, "Max id value for invalid deal", invalidDeal.getId());

				// skip the header of the file
				// get all deals from the file and add file name to all deal
				List<ValidDeal> allDeals = Files.lines(Paths.get(file.getPath())).skip(1).map(mapToDealFields)
						.map(deal -> deal.mapAddFileName(multipartFile.getOriginalFilename()))
						.collect(Collectors.toList());

				// skip the header of the file
				// get valid deals with no duplicate value as deal id is unique
				// in case any exists
				List<ValidDeal> validDeals = allDeals.stream().filter(filterValidDeal)
						.collect(Collectors.collectingAndThen(
								Collectors
										.toCollection(() -> new TreeSet<>(Comparator.comparing(ValidDeal::getDealId))),
								ArrayList<ValidDeal>::new));

				// remove valid deals from all deals
				validDeals.forEach(v -> allDeals.remove(v));
				// get invalid deals
				List<InvalidDeal> invalidDeals = allDeals.stream().map(mapToInvaliDealFields)
						.collect(Collectors.toList());

				// get list of from currency or Ordering Currency from valid
				// deals
				List<Currency> fromCurrencies = validDeals.stream().map(deal -> deal.getFromCurrency()).distinct()
						.collect(Collectors.toList());

				// get list of to currency from valid deals
				List<Currency> toCurrencies = validDeals.stream().map(deal -> deal.getToCurrency()).distinct()
						.collect(Collectors.toList());

				// add fromCurrencies to toCurrencies
				toCurrencies.addAll(fromCurrencies);
				// remove duplicate from all combined from and to currencies
				List<Currency> allCurrencies = toCurrencies.stream().distinct().collect(Collectors.toList());

				// start saving all data in database
				long startTime = System.currentTimeMillis();

				// save or update from and to currencies as currency has
				// manyToOne relationship with deal
				currencyService.insertAll(allCurrencies);

				// save source file name as SourceFile has manyToOne
				// relationship with deal and invalid deal
				sourceFileService.createOrUpdate(new SourceFile(multipartFile.getOriginalFilename(), new Date()));

				// save valid deals
				validDealService.insertAll(validDeals);

				// update counts of deals for ordering currency
				currencyService.updateAll(fromCurrencies);

				// save invalid deals
				invalidDealService.insertAll(invalidDeals);

				long endTime = System.currentTimeMillis();
				long totalTime = endTime - startTime;
				NumberFormat formatter = new DecimalFormat("#0.00000");

				// Move file to archive as System should not import same file
				// twice
				Utilities.moveFileToArchiveFolder(file, multipartFile.getOriginalFilename());

				redirectAttributes.addFlashAttribute("message",
						"You successfully uploaded " + multipartFile.getOriginalFilename() + " in "
								+ formatter.format((totalTime) / 1000d) + " seconds!");

				log.log(Level.INFO, "You successfully uploaded {0} in {1} seconds!",
						new Object[] { multipartFile.getOriginalFilename(), formatter.format((totalTime) / 1000d) });

			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("message", "Something went wrong!");
				log.severe(e.getMessage());
			}

		}

		return "redirect:/";
	}

	/**
	 * Inquire about results
	 * 
	 * @param inquire
	 * @param redirectAttributes
	 * @return
	 */
	@PostMapping("/inquire")
	public String inquireResults(@RequestParam("inquire") String inquire, RedirectAttributes redirectAttributes) {

		if (inquire.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please enter the file name");
			return "redirect:/";
		}

		return "redirect:/clusterdata?inquire=" + inquire;
	}

}
