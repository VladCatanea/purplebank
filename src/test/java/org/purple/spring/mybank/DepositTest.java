package org.purple.spring.mybank;

import static org.assertj.core.api.Assertions.assertThat;
import static org.purple.spring.mybank.Constants.ADMIN;
import static org.purple.spring.mybank.Constants.PASSWORD;
import static org.purple.spring.mybank.Constants.USER;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.purple.spring.mybank.deposit.Deposit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@TestMethodOrder(MethodOrderer.MethodName.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DepositTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void checkReadAll() throws Exception {
		ResponseEntity<Deposit[]> responseEntity = restTemplate.withBasicAuth(USER, PASSWORD).getForEntity("/api/deposits", Deposit[].class);
		assertThat(responseEntity.getBody()).isNotEmpty();
		Deposit[] depositArray = responseEntity.getBody();
		assertThat(depositArray[0].getDuration()).isEqualTo(365L);
	}

	@Test
	void checkReadOne() throws Exception {
		ResponseEntity<Deposit> responseEntity = restTemplate.withBasicAuth(USER, PASSWORD).getForEntity("/api/deposits/1", Deposit.class);
		Deposit deposit = responseEntity.getBody();
		assertThat(deposit.getDuration()).isEqualTo(365L);
	}

	@Test
	void checkCreate() throws Exception {
		Deposit deposit = new Deposit(100L, "RON");
		ResponseEntity<Long> responseEntityConfirm = restTemplate.withBasicAuth(ADMIN, PASSWORD).postForEntity("/api/deposits", deposit, Long.class);
		Long id = responseEntityConfirm.getBody();
		deposit.setId(id);
		ResponseEntity<Deposit> responseEntity = restTemplate.withBasicAuth(USER, PASSWORD).getForEntity("/api/deposits/" + id, Deposit.class);
		assertThat(responseEntityConfirm.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		Deposit newDeposit = responseEntity.getBody();
		assertThat(newDeposit).isEqualTo(deposit);
	}

	@Test
	void checkUpdate() throws Exception {
		Deposit deposit = new Deposit(200L, "RON");
		ResponseEntity<Long> responseEntityConfirm = restTemplate.withBasicAuth(ADMIN, PASSWORD).postForEntity("/api/deposits", deposit, Long.class);
		Long id = responseEntityConfirm.getBody();
		deposit.setCurrency("GBP");
		deposit.setId(id);
		restTemplate.withBasicAuth(ADMIN, PASSWORD).put("/api/deposits/" + id, deposit);

		ResponseEntity<Deposit> responseEntity = restTemplate.withBasicAuth(USER, PASSWORD).getForEntity("/api/deposits/" + id, Deposit.class);
		Deposit newDeposit = responseEntity.getBody();

		assertThat(newDeposit.getCurrency()).isEqualTo("GBP");
	}

	@Test
	void checkDelete() throws Exception {
		Deposit deposit = new Deposit(300L, "RON");
		ResponseEntity<Long> responseEntityConfirm = restTemplate.withBasicAuth(ADMIN, PASSWORD).postForEntity("/api/deposits", deposit, Long.class);
		Long id = responseEntityConfirm.getBody();

		restTemplate.withBasicAuth(ADMIN, PASSWORD).delete("/api/deposits/" + id);

		ResponseEntity<Deposit> responseEntity = restTemplate.withBasicAuth(USER, PASSWORD).getForEntity("/api/deposits/" + id, Deposit.class);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

}
