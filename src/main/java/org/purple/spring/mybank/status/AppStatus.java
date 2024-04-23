package org.purple.spring.mybank.status;

//public record AppStatus {
//	private long requestsCount;
//	private String status;
//}


public record AppStatus(long requestsCount, String status) {
}