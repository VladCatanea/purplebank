package org.purple.spring.mybank.transactions;

import static org.purple.spring.mybank.Constants.BASE_API;

import java.util.List;

import org.purple.spring.mybank.errors.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(BASE_API + "/transactions")
public class TransactionHistoryRest {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private final TransactionHistoryRepository transactionHistoryRepository;
	private final TransactionService transactionService;

	@Autowired
	public TransactionHistoryRest(TransactionHistoryRepository transactionHistoryRepository, TransactionService transactionService) {
		this.transactionHistoryRepository = transactionHistoryRepository;
		this.transactionService = transactionService;
	}

	@Secured("ROLE_ADMIN")
	@PostMapping
	public ResponseEntity<Integer> handleFileUpload(@RequestParam("file") MultipartFile file) {
		logger.debug("Uploading file");
		String extension = Utils.getFileExtension(file.getOriginalFilename());
		Integer transactionsProcessed;
		if (extension.equals(".json")) {
			transactionsProcessed = transactionService.processJSON(file);
		} else {
			if (extension.equals(".csv")) {
				transactionsProcessed = transactionService.processCSV(file);
			} else {
				throw new TransactionException("File extension '" + extension + "' incorrect");
			}
		}

		return new ResponseEntity<>(transactionsProcessed, HttpStatus.CREATED);
	}

	@Secured("ROLE_ADMIN")
	@GetMapping
	public ResponseEntity<List<Transaction>> getTransationHistory() {
		List<Transaction> transactionList = transactionHistoryRepository
				.findAllByOrderByTransactionDateAsc(PageRequest.of(0, 2));
		logger.debug("Returning list of all transactions: {}", transactionList);
		return new ResponseEntity<>(transactionList, HttpStatus.FOUND);
	}

}
