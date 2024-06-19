package org.purple.spring.mybank.transactions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.purple.spring.mybank.account.Account;
import org.purple.spring.mybank.account.AccountRepository;
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

	public String validateTransaction(Transaction transaction) {
		if(!accountRepository.findById(transaction.getReceiverIban()).isPresent()) {
			return "ERROR";
		}
		Account account = accountRepository.findById(transaction.getReceiverIban()).get();
		Long newAmount = account.getAmount() + transaction.getDebitAmount() - transaction.getCreditAmount();
		if (newAmount < 0) {
			return "Error";
		}
		account.setAmount(newAmount);
		accountRepository.save(account);
		transactionAssignedRepository.save(new ATransaction(transaction));
		return "ASSIGNED";
	}

	public int processJSON(MultipartFile file) {
		try (InputStream inputStream = file.getInputStream()) {
			// read from inputStream
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
			StringBuilder sb = new StringBuilder();
			String s;
			while ((s = in.readLine()) != null) {
				sb.append(s);
				sb.append('\n');
			}
			in.close();

			// process JSON
			ObjectMapper objectMapper = new ObjectMapper();
			List<Transaction> transactionList = objectMapper.readValue(sb.toString(),
					new TypeReference<List<Transaction>>() {
					});
			int transactionNo = 0;
			for (Transaction transaction : transactionList) {
				transaction.setStatus("UNASSIGNED");
				logger.debug("Transaction number {} saved: {}", transactionNo, transaction);
				transactionHistoryRepository.save(transaction);
				final String status = validateTransaction(transaction);
				transactionHistoryRepository.findById(transaction.getReferenceNum()).map(updatedTransaction -> {
					updatedTransaction.setStatus(status);
					return transactionHistoryRepository.save(updatedTransaction);
				}).orElseThrow(() -> new EntityNotFoundException(transaction.getReferenceNum(), "transaction"));
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
					transactionHistoryRepository.save(transaction);
					logger.debug("Transaction number {} saved: {}", transactionNo, transaction);
					final String status = validateTransaction(transaction);
					final String reference = transaction.getReferenceNum();
					transactionHistoryRepository.findById(reference).map(updatedTransaction -> {
						updatedTransaction.setStatus(status);
						return transactionHistoryRepository.save(updatedTransaction);
					}).orElseThrow(() -> new EntityNotFoundException(reference, "transaction"));
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
