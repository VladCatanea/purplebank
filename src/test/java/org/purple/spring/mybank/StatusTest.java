package org.purple.spring.mybank;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.purple.spring.mybank.deposit.Deposit;
import org.purple.spring.mybank.status.AppStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@TestMethodOrder(MethodOrderer.MethodName.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class StatusTest {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void checkAStatus() throws Exception {
		AppStatus testStatus = restTemplate.getForObject("http://localhost:" + port + "/status", AppStatus.class);
		assertThat(testStatus.requestsCount()).isEqualTo(1);
		assertThat(testStatus.status()).isEqualTo("yellow");
	}
	
	@Test
	void checkBIncrement() throws Exception {
		AppStatus testStatus = restTemplate.getForObject("http://localhost:" + port + "/status", AppStatus.class);
		assertThat(testStatus.requestsCount()).isEqualTo(2);
		assertThat(testStatus.status()).isEqualTo("yellow");
	}
	
//	@Test
//	void checkCreate() throws Exception {
//		Deposit testDeposit = restTemplate.getForObject("http://localhost:" + port + "/deposits", Deposit.class);
//		assertThat(testDeposit.duration()).isEqualTo(365);
//	}
}
