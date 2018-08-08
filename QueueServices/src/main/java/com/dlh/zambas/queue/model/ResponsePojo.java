package com.dlh.zambas.queue.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author singhg
 *
 */
@XmlRootElement
public class ResponsePojo {

	private int code;
	private String success = null;
	private String failure = null;

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
