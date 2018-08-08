package com.dlh.zambas.queue.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author singhg 
 * Request should consist of following keys
 *
 */
public class RequestPojo {
	@JsonProperty("PayLoad")
	private String payload = null;
	private String queueName = null;
	private String customer = null;
	@JsonProperty("RequestID")
	private String requestId = null;
	@JsonProperty("ApplicationID")
	private String applicationId = null;

	
	
	public RequestPojo() {
	}

	public RequestPojo(String payload, String queueName, String customer, String requestId, String applicationId) {
		this.payload = payload;
		this.queueName = queueName;
		this.customer = customer;
		this.requestId = requestId;
		this.applicationId = applicationId;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

}
