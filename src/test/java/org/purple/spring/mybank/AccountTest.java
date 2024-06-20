package org.purple.spring.mybank;

import static org.assertj.core.api.Assertions.assertThat;
import static org.purple.spring.mybank.Constants.ADMIN;
import static org.purple.spring.mybank.Constants.BASE_API;
import static org.purple.spring.mybank.Constants.PASSWORD;
import static org.purple.spring.mybank.Constants.USER;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.purple.spring.mybank.account.ATransaction;
import org.purple.spring.mybank.account.Account;
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
public class AccountTest {
	@Autowired
	TestRestTemplate restTemplate;
	
	ResponseEntity<Integer> uploadTransactions(String filename){
		Resource file = new ClassPathResource(filename, this.getClass().getClassLoader());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		MultiValueMap<String, Object> body
		  = new LinkedMultiValueMap<>();
		body.add("file", file);
		HttpEntity<MultiValueMap<String, Object>> requestEntity
		 = new HttpEntity<>(body, headers);
		return restTemplate.withBasicAuth(ADMIN, PASSWORD).postForEntity(BASE_API + "/transactions", requestEntity, Integer.class);
	}
	
	@Test
	void checkReadAll(){
		 ResponseEntity<Account[]> response = restTemplate.withBasicAuth(USER, PASSWORD).getForEntity(BASE_API + "/account", Account[].class);
		 assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
		 Account[] accountList = response.getBody();
		 assertThat(accountList[0].getIban()).isEqualTo("RO66BACX0000001234567890");
	}
	
	@Test 
	void validateTransactionsTest() {
		ResponseEntity<Integer> uploadResponse = uploadTransactions("transactions_test_files/transactions.json");
		assertThat(uploadResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		ResponseEntity<ATransaction[]> response = restTemplate.withBasicAuth(USER, PASSWORD).getForEntity(BASE_API + "/accountTransaction/RO66BACX0000001234567890", ATransaction[].class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
		ResponseEntity<Account> accountResponse = restTemplate.withBasicAuth(USER, PASSWORD).getForEntity(BASE_API + "/account/RO66BACX0000001234567890", Account.class);
		assertThat(accountResponse.getStatusCode()).isEqualTo(HttpStatus.FOUND);
		assertThat(accountResponse.getBody().getAmount()).isEqualTo(220L);
	}
}
