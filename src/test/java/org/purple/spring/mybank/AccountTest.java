package org.purple.spring.mybank;

import static org.assertj.core.api.Assertions.assertThat;
import static org.purple.spring.mybank.Constants.BASE_API;
import static org.purple.spring.mybank.Constants.PASSWORD;
import static org.purple.spring.mybank.Constants.USER;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.purple.spring.mybank.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@TestMethodOrder(MethodOrderer.MethodName.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AccountTest {
	@Autowired
	TestRestTemplate restTemplate;
	
	@Test
	void checkReadAll(){
		 ResponseEntity<Account[]> response = restTemplate.withBasicAuth(USER, PASSWORD).getForEntity(BASE_API + "/account", Account[].class);
		 assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
		 Account[] accountList = response.getBody();
		 assertThat(accountList[0].getIban()).isEqualTo("1");
	}
}
