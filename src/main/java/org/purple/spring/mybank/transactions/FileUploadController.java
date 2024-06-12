package org.purple.spring.mybank.transactions;

import static org.purple.spring.mybank.Constants.BASE_API;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(BASE_API + "/transactions")
public class FileUploadController {
//	private final StorageService storageService;
	private Logger logger = LoggerFactory.getLogger(getClass());
	private final TransactionHistoryRepository transactionHistoryRepository;
	
	@Autowired
	public FileUploadController(StorageService storageService, TransactionHistoryRepository transactionHistoryRepository) {
//		this.storageService = storageService;
		this.transactionHistoryRepository = transactionHistoryRepository;
	}
	

	@Secured("ROLE_ADMIN")
	@PostMapping
	public ResponseEntity<Integer> handleFileUpload(@RequestParam("file") MultipartFile file) {
		logger.debug("Uploading file");
		List<Transaction> transactionList = Utils.processJSON(file);
		Integer transactionsProcessed = transactionList.size();
		logger.debug("Processed {} transactions: {}", transactionsProcessed, transactionList);
		transactionHistoryRepository.saveAll(transactionList);
		return new ResponseEntity<>(transactionsProcessed, HttpStatus.CREATED);
	}

}
