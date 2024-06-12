package org.purple.spring.mybank.transactions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

	public static List<Transaction> processJSON(MultipartFile file) {
		try (InputStream inputStream = file.getInputStream()) {
			// read from inputStream
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
			StringBuilder sb = new StringBuilder();
			String s;
			while ((s = in.readLine()) != null) {
				sb.append(s + "\n");
			}
			in.close();

			// process JSON
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(sb.toString());
			JsonNode transactionListJsonNode = jsonNode.get("transactions");
			List<Transaction> transactionList = objectMapper.readValue(transactionListJsonNode.traverse(),
					new TypeReference<List<Transaction>>() {
					});
			return transactionList;

		} catch (IOException e) {
			throw new StorageException("Failed to read file", e);
		}
	}

}
