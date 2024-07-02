package org.purple.spring.mybank.transactions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.purple.spring.mybank.account.ATransaction;
import org.purple.spring.mybank.account.Account;
import org.purple.spring.mybank.account.AccountRepository;
import org.purple.spring.mybank.account.TransactionAssignedRepository;
import org.purple.spring.mybank.errors.EntityNotFoundException;
import org.purple.spring.mybank.errors.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseLong;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class TransactionService {

	private static Logger logger = LoggerFactory.getLogger(TransactionService.class);
	@Autowired
	private TransactionHistoryRepository transactionHistoryRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private TransactionAssignedRepository transactionAssignedRepository;

	public void processTransaction(Transaction transaction, int transactionNo) {
		String reference = transaction.getReferenceNum();
		// don't reprocess duplicate transactions
		if(transactionHistoryRepository.findById(reference).isPresent()) {
			return;
		}
		transaction.setStatus("UNASSIGNED");
		transactionHistoryRepository.save(transaction);
		logger.debug("Transaction number {} saved: {}", transactionNo, transaction);
		final String status = validateTransaction(transaction);
		transactionHistoryRepository.findById(reference).map(updatedTransaction -> {
			updatedTransaction.setStatus(status);
			return transactionHistoryRepository.save(updatedTransaction);
		}).orElseThrow(() -> new EntityNotFoundException(reference, "transaction"));
	}
	
	public String validateTransaction(Transaction transaction) {
		if(!accountRepository.findById(transaction.getReceiverIban()).isPresent()) {
			return "ERROR";
		}
		Account account = accountRepository.findById(transaction.getReceiverIban()).get();
		Long newAmount = account.getAmount() - transaction.getDebitAmount() + transaction.getCreditAmount();
		if (newAmount < 0) {
			return "Insufficient Funds";
		}
		account.setAmount(newAmount);
		accountRepository.save(account);
		transactionAssignedRepository.save(new ATransaction(transaction));
		return "ASSIGNED";
	}

	public int processJSON(MultipartFile file) {
		try (InputStream inputStream = file.getInputStream()) {
			// process JSON
			ObjectMapper objectMapper = new ObjectMapper();
			List<Transaction> transactionList = objectMapper.readValue(inputStream,
					new TypeReference<List<Transaction>>() {
					});
			int transactionNo = 0;
			for (Transaction transaction : transactionList) {
				processTransaction(transaction, transactionNo);
				transactionNo++;
			}
			return transactionNo;

		} catch (IOException e) {
			throw new TransactionException("Failed to read file", e);
		}

	}

	public int processCSV(MultipartFile file) {

		// read file
		try (InputStream inputStream = file.getInputStream()) {
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
			try (ICsvBeanReader beanReader = new CsvBeanReader(in, CsvPreference.STANDARD_PREFERENCE)) {
				final String[] headers = beanReader.getHeader(true);
				final CellProcessor[] processors = getProcessors();
				Transaction transaction;
				int transactionNo = 0;
				while ((transaction = beanReader.read(Transaction.class, headers, processors)) != null) {
					processTransaction(transaction, transactionNo);
					transactionNo++;
				}
				return transactionNo;
			}
		} catch (IOException e) {
			throw new TransactionException("Failed to read file", e);
		}

	}

	private static CellProcessor[] getProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { new NotNull(), // referenceNum
				new NotNull(), // receiverIban
				new NotNull(), // senderIban
				new NotNull(), // transactionDate
				new Optional(), // authorizationCode
				new NotNull(new ParseLong()), // creditAmount
				new NotNull(new ParseLong()) // debitAmount
		};
		return processors;
	}
}
