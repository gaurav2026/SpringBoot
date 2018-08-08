package com.dlh.zambas.queue.model;

/**
 * 
 * @author singhg
 * params required to make connection to queue
 *
 */
public class ConnectionPojo {
	private String userName = null;
	private char[] password = null;
	private String activeEMSUrl = null;
	private String standByEMSUrl = null;
	private String emsUserName = null;
	private char[] emsPassword = null;
	private String queueConnectionFactory = null;
	private String topicConnectionFactory = null;

	public String getEmsUserName() {
		return emsUserName;
	}

	public void setEmsUserName(String emsUserName) {
		this.emsUserName = emsUserName;
	}

	public char[] getEmsPassword() {
		return emsPassword;
	}

	public void setEmsPassword(char[] emsPassword) {
		this.emsPassword = emsPassword;
	}

	public String getQueueConnectionFactory() {
		return queueConnectionFactory;
	}

	public void setQueueConnectionFactory(String queueConnectionFactory) {
		this.queueConnectionFactory = queueConnectionFactory;
	}

	public String getTopicConnectionFactory() {
		return topicConnectionFactory;
	}

	public void setTopicConnectionFactory(String topicConnectionFactory) {
		this.topicConnectionFactory = topicConnectionFactory;
	}

	public String getActiveEMSUrl() {
		return activeEMSUrl;
	}

	public void setActiveEMSUrl(String activeEMSUrl) {
		this.activeEMSUrl = activeEMSUrl;
	}

	public String getStandByEMSUrl() {
		return standByEMSUrl;
	}

	public void setStandByEMSUrl(String standByEMSUrl) {
		this.standByEMSUrl = standByEMSUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

}
