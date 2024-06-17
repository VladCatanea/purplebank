package org.purple.spring.mybank.transactions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.purple.spring.mybank.errors.TransactionException;
import org.slf4j.Logger;
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

public class Utils {

	public static String getFileExtension(String filename) {
		if(! filename.contains(".")) {
			return "";
		}
		return filename.substring(filename.lastIndexOf("."));
	}

	public static List<Transaction> processJSON(MultipartFile file) {
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
			return transactionList;

		} catch (IOException e) {
			throw new TransactionException("Failed to read file", e);
		}

	}

	public static int processCSV(MultipartFile file, TransactionHistoryRepository repository, Logger logger) {
		
		
		// read file
		try (InputStream inputStream = file.getInputStream()) {
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
			try (ICsvBeanReader beanReader = new CsvBeanReader(in, CsvPreference.STANDARD_PREFERENCE)){
				final String[] headers = beanReader.getHeader(true);
				final CellProcessor[] processors = getProcessors();
				Transaction transaction;
				int transactionNo = 0;
				while ((transaction = beanReader.read(Transaction.class, headers, processors)) != null) {
					repository.save(transaction);
					logger.debug("Transaction number {} saved: {}", transactionNo, transaction);
					transactionNo ++;
				}
				return transactionNo;
			}
		} catch (IOException e) {
			throw new TransactionException("Failed to read file", e);
		}

	}
	private static CellProcessor[] getProcessors() {
		final CellProcessor[] processors = new CellProcessor[] {
				new NotNull(), // referenceNum
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
