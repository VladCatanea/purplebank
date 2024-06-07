package org.purple.spring.mybank;

import static org.assertj.core.api.Assertions.assertThat;
import static org.purple.spring.mybank.Constants.PASSWORD;
import static org.purple.spring.mybank.Constants.USER;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.purple.spring.mybank.status.AppStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

@TestMethodOrder(MethodOrderer.MethodName.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class StatusTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void checkAStatus() throws Exception {
		AppStatus testStatus = restTemplate.withBasicAuth(USER, PASSWORD).getForObject("/api/status", AppStatus.class);
		assertThat(testStatus.requestsCount()).isEqualTo(1);
		assertThat(testStatus.status()).isEqualTo("yellow");
	}
	
	@Test
	void checkBIncrement() throws Exception {
		AppStatus testStatus = restTemplate.withBasicAuth(USER, PASSWORD).getForObject("/api/status", AppStatus.class);
		assertThat(testStatus.requestsCount()).isEqualTo(2);
		assertThat(testStatus.status()).isEqualTo("yellow");
	}
	

}
