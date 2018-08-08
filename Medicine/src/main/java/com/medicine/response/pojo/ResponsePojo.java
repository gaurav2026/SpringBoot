package com.medicine.response.pojo;

import javax.persistence.Column;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResponsePojo {

	private int code;
	@Column(nullable = true)
	private String success = null;
	@Column(nullable = true)
	private String failure = null;
	@Column(nullable = true)
	private String name = null;
	@Column(nullable = true)
	private String invoice;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getFailure() {
		return failure;
	}

	public void setFailure(String failure) {
		this.failure = failure;
	}
}