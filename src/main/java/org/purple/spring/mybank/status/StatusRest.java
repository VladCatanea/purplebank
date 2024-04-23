package org.purple.spring.mybank.status;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusRest {
	
	private long requestsCount;

	@GetMapping("/status")
	public AppStatus getStatus() {
		requestsCount++;
		return new AppStatus(requestsCount, "yellow");
	}
	

}

