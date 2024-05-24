package org.purple.spring.mybank;

import static org.assertj.core.api.Assertions.assertThat;
import static org.purple.spring.mybank.Constants.PASSWORD;
import static org.purple.spring.mybank.Constants.USER;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

@TestMethodOrder(MethodOrderer.MethodName.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserPermissionTest {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void checkRead() {
		ResponseEntity<String> response = restTemplate.withBasicAuth(USER, PASSWORD).getForEntity("/api/permission", String.class);
		assertThat(response.getBody()).isEqualTo("USER");
	}
}
