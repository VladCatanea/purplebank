package org.purple.spring.mybank.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusRest {

	private long requestsCount;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping("/status")
	public AppStatus getStatus() {
		logger.info("Returning status (requestsCount = " + requestsCount + ")");
		requestsCount++;
		return new AppStatus(requestsCount, "yellow");
	}

}
