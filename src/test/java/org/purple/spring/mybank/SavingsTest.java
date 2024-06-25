package org.purple.spring.mybank;

import static org.assertj.core.api.Assertions.assertThat;
import static org.purple.spring.mybank.Constants.ADMIN;
import static org.purple.spring.mybank.Constants.PASSWORD;
import static org.purple.spring.mybank.Constants.USER;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.purple.spring.mybank.savings.Savings;
import org.purple.spring.mybank.savings.SavingsDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@TestMethodOrder(MethodOrderer.MethodName.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SavingsTest {
	@Autowired
	private TestRestTemplate restTemplate;
	
	private String iban = "RO66BACX0000005274537285"; // iban of one USER account for testing 
	
	private Long createSavings(Savings savings) {
		ResponseEntity<Long> responseEntityConfirm = restTemplate.withBasicAuth(USER, PASSWORD)
				.postForEntity("/api/savings?iban=" + iban, savings, Long.class);
		assertThat(responseEntityConfirm.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		Long id = responseEntityConfirm.getBody();
		return id;
	}
	
	@Test
	void checkReadAllDetails() throws Exception {
		Savings defaultSavings = new Savings(1L, 20L, USER, null);
		defaultSavings.setId(1L);
		// try with USER
		ResponseEntity<SavingsDetails[]> responseEntity = restTemplate.withBasicAuth(USER, PASSWORD)
				.getForEntity("/api/savings/details", SavingsDetails[].class);
		assertThat(responseEntity.getBody()).isNotEmpty();
		assertThat(responseEntity.getBody()[0].getId()).isEqualTo(1L);
		assertThat(responseEntity.getBody()[0].getCurrency()).isEqualTo("RON");
		// try with ADMIN
		responseEntity = restTemplate.withBasicAuth(ADMIN, PASSWORD).getForEntity("/api/savings/details", SavingsDetails[].class);
		assertThat(responseEntity.getBody()).isEmpty();
	}
	
	@Test
	void checkReadAll() throws Exception {
		Savings defaultSavings = new Savings(1L, 20L, USER, null);
		defaultSavings.setId(1L);
		// try with USER
		ResponseEntity<Savings[]> responseEntity = restTemplate.withBasicAuth(USER, PASSWORD)
				.getForEntity("/api/savings", Savings[].class);
		assertThat(responseEntity.getBody()).isNotEmpty();
		assertThat(responseEntity.getBody()[0].getDepositId()).isEqualTo(1L);
		// try with ADMIN
		responseEntity = restTemplate.withBasicAuth(ADMIN, PASSWORD).getForEntity("/api/savings", Savings[].class);
		assertThat(responseEntity.getBody()).isEmpty();
	}

	@Test
	void checkCreate() throws Exception {
		Savings savings = new Savings(1L, 10L, ADMIN, null);
		ResponseEntity<Long> responseEntityConfirm = restTemplate.withBasicAuth(USER, PASSWORD)
				.postForEntity("/api/savings?iban=" + iban, savings, Long.class);
		assertThat(responseEntityConfirm.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		Long id = responseEntityConfirm.getBody();
		savings.setId(id);
		savings.setOwner(USER);
		ResponseEntity<Savings> responseEntity = restTemplate.withBasicAuth(USER, PASSWORD)
				.getForEntity("/api/savings/" + id, Savings.class);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
		assertThat(responseEntity.getBody().getAmount()).isEqualTo(10L);
	}
	
	@Test
	void checkEdit() throws Exception {
		Savings savings = new Savings(1L, 20L, USER, null);
		Long id = createSavings(savings);
		savings.setId(id);
		savings.setAmount(100000L);
		restTemplate.withBasicAuth(USER, PASSWORD).put("/api/savings/" + id, savings);
		ResponseEntity<Savings> responseEntity = restTemplate.withBasicAuth(USER, PASSWORD)
				.getForEntity("/api/savings/" + id, Savings.class);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
		assertThat(responseEntity.getBody()).isEqualTo(savings);
	}

	@Test
	void checkDelete() throws Exception {
		Savings savings = new Savings(1L, 15L, USER, null);
		Long id = createSavings(savings);
		savings.setId(id);
		restTemplate.withBasicAuth(USER, PASSWORD).delete("/api/savings/" + id);
		ResponseEntity<Savings[]> responseEntity = restTemplate.withBasicAuth(USER, PASSWORD)
				.getForEntity("/api/savings", Savings[].class);
		assertThat(responseEntity.getBody()).doesNotContain(savings);
	}

}
