package com.dlh.zambas.queue.constants;

/**
 * 
 * @author singhg
 *
 */
public enum QueueConstants {

	CustomerID,
	RequestID,
	ApplicationID,
	TIB_JMS_INITIAL_CONTEXT_FACTORY("com.tibco.tibjms.naming.TibjmsInitialContextFactory"),
	SSL("ssl"),
	FALSE("false"),
	J2SE("j2se"),
	TLS_RSA_WITH_AES_128_CBC_SHA256("TLS_RSA_WITH_AES_128_CBC_SHA256"),
	CONNECTION_PROPERTY_FILE("connection.properties"),
	USERNAME(".userName"),
	PASSWORD(".password"),
	SSL_ACTIVE(".ssl_active_ems_url"),
	SSL_STANDBY(".ssl_standby_ems_url"),
	EMS_SERVER_USERNAME(".ems.server.userName"),
	EMS_SERVER_PASSWORD(".ems.server.password"),
	SSLTopicConnectionFactory(".ssltopicconnectionfactory"),
	SSLQueueConnectionFactory(".sslqueueconnectionfactory");
	
	private String value = null;

	QueueConstants(String value) {
		this.value = value;
	}

	private QueueConstants() {
	}

	public String value() {
		return value;
	}
}
