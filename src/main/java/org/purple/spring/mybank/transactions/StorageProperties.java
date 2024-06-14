package org.purple.spring.mybank.transactions;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("storage")
@Configuration
public class StorageProperties {
	
	private String location = "resource-dir";

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
}
