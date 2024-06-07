package org.purple.spring.mybank.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.purple.spring.mybank.Constants.BASE_API;

@RestController
@RequestMapping(BASE_API + "/status")
public class StatusRest {

	private long requestsCount;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping
	public AppStatus getStatus() {
		requestsCount++;
		logger.info("Returning status (requestsCount = {} )", requestsCount);
		return new AppStatus(requestsCount, "yellow");
	}

}
