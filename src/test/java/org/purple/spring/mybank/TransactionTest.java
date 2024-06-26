package org.purple.spring.mybank;

import static org.assertj.core.api.Assertions.assertThat;
import static org.purple.spring.mybank.Constants.ADMIN;
import static org.purple.spring.mybank.Constants.BASE_API;
import static org.purple.spring.mybank.Constants.PASSWORD;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.purple.spring.mybank.transactions.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@TestMethodOrder(MethodOrderer.MethodName.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TransactionTest {
	@Autowired
	TestRestTemplate restTemplate;
	String url = BASE_API + "/transactions";
	
	ResponseEntity<Integer> uploadTransactions(String filename){
		Resource file = new ClassPathResource(filename, this.getClass().getClassLoader());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		MultiValueMap<String, Object> body
		  = new LinkedMultiValueMap<>();
		body.add("file", file);
		HttpEntity<MultiValueMap<String, Object>> requestEntity
		 = new HttpEntity<>(body, headers);
		return restTemplate.withBasicAuth(ADMIN, PASSWORD).postForEntity(url, requestEntity, Integer.class);
	}
	
	@Test
	void jsonCreateTest() {
		ResponseEntity<Integer> response = uploadTransactions("transactions_test_files/transactions.json");
		System.out.println(response.getBody());
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getBody()).isEqualTo(5);
	}
	
	@Test 
	void csvCreateTest() {
		ResponseEntity<Integer> response = uploadTransactions("transactions_test_files/transactions.csv");
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getBody()).isEqualTo(5);
	}
	
	@Test
	void readTest() {
		ResponseEntity<Integer> uploadResponse = uploadTransactions("transactions_test_files/transactions.json");
		assertThat(uploadResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		ResponseEntity<Transaction[]> response = restTemplate.withBasicAuth(ADMIN, PASSWORD).getForEntity(url, Transaction[].class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
		Transaction[] transactions = response.getBody();
		assertThat(transactions[0].getReferenceNum()).isEqualTo("3");
		assertThat(transactions[1].getReferenceNum()).isEqualTo("1");
		assertThat(transactions[2].getReferenceNum()).isEqualTo("2");
		assertThat(transactions[1].getStatus()).isEqualTo("ERROR");
		assertThat(transactions[2].getStatus()).isEqualTo("Insufficient Funds");
		assertThat(transactions[0].getStatus()).isEqualTo("ASSIGNED");
	}
	
	@Test
	void paginationReadTest() {
		ResponseEntity<Integer> uploadResponse = uploadTransactions("transactions_test_files/transactions.json");
		assertThat(uploadResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		// /api/transactions/<itemsPerPage>/<page>
		ResponseEntity<Transaction[]> response = restTemplate.withBasicAuth(ADMIN, PASSWORD).getForEntity(url + "/1/2", Transaction[].class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
		Transaction[] transactions = response.getBody();
		assertThat(transactions[0].getReferenceNum()).isEqualTo("2");
	}
	
}
