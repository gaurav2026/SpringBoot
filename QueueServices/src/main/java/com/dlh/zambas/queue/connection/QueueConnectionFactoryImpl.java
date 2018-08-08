package com.dlh.zambas.queue.connection;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.Set;

import javax.jms.QueueConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dlh.zambas.queue.constants.QueueConstants;
import com.dlh.zambas.queue.model.ConnectionPojo;
import com.dlh.zambas.queue.model.EMSServerDetailFilePojo;

/**
 * class where connectionfactory is returned after lookup in factory.conf file
 * of ems
 * 
 * @author singhg
 *
 */
@Component
public class QueueConnectionFactoryImpl implements IQueueConnectionFactory{

	private static final Logger logger = LoggerFactory.getLogger(QueueConnectionFactoryImpl.class);

	@Autowired
	private EMSServerDetailFilePojo emsServerDetailFilePojo;

	private Properties factoryProps = null;
	private QueueConnectionFactory qconFactory = null;
	private InitialContext context = null;
	private ConnectionPojo connectionPojo = null;
	
	@Override
	public ConnectionPojo getConnectionPojo() {
		return connectionPojo;
	}

	public void setConnectionPojo(ConnectionPojo connectionPojo) {
		this.connectionPojo = connectionPojo;
	}

	/**
	 * pass values to property and do lookup to return factory
	 * 
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@Override
	public QueueConnectionFactory returnFactory(String customer) throws Exception {
		connectionPojo = fetchServerDetails(customer);
		if (null != connectionPojo) {
			factoryProps = new Properties();
			factoryProps.put(Context.INITIAL_CONTEXT_FACTORY, QueueConstants.TIB_JMS_INITIAL_CONTEXT_FACTORY.value());
			factoryProps.put(Context.PROVIDER_URL,
					connectionPojo.getActiveEMSUrl() + ",:" + connectionPojo.getStandByEMSUrl());
			factoryProps.put(Context.SECURITY_PRINCIPAL, connectionPojo.getUserName());
			factoryProps.put(Context.SECURITY_CREDENTIALS, new String(connectionPojo.getPassword()));
			// props.put(TibjmsContext.SSL_TRUSTED_CERTIFICATES,"D:/zambas/Certificates/LH_CA.cer");

			// SSL JNDI Lookup
			factoryProps.put(com.tibco.tibjms.naming.TibjmsContext.SECURITY_PROTOCOL, QueueConstants.SSL.value());
			factoryProps.put(com.tibco.tibjms.naming.TibjmsContext.SSL_ENABLE_VERIFY_HOST,
					new Boolean(QueueConstants.FALSE.value()));
			factoryProps.put(com.tibco.tibjms.naming.TibjmsContext.SSL_VENDOR, QueueConstants.J2SE.value());
			factoryProps.put(com.tibco.tibjms.naming.TibjmsContext.SSL_CIPHER_SUITES,
					QueueConstants.TLS_RSA_WITH_AES_128_CBC_SHA256.value());
			context = new InitialContext(factoryProps);
			qconFactory = (QueueConnectionFactory) context.lookup(connectionPojo.getQueueConnectionFactory());
		}
		return qconFactory;

	}

	/**
	 * load values from lh.properties
	 * 
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	private ConnectionPojo fetchServerDetails(String customer) throws Exception {
		Properties prop = new Properties();
		File emsServerFileLocation = new File(emsServerDetailFilePojo.getEmsServerPropertyFileLocation());
		try (FileInputStream in = new FileInputStream(emsServerFileLocation)) {
			prop.load(in);
			connectionPojo = new ConnectionPojo();
			Set<Object> set = prop.keySet();
			for (Object enuKeys : set) {
				String key = (String) enuKeys;
				String customerName = customer.toLowerCase();
				if (key.startsWith(customerName)) {
					// check if ems.server.password is not null
					if (isNotNull(prop.getProperty(customerName + QueueConstants.EMS_SERVER_PASSWORD.value()))) {
						connectionPojo.setPassword(prop
								.getProperty(customerName + QueueConstants.EMS_SERVER_PASSWORD.value()).toCharArray());
					} else {
						connectionPojo
								.setPassword(isNotNull(prop.getProperty(customerName + QueueConstants.PASSWORD.value()))
										? prop.getProperty(customerName + QueueConstants.PASSWORD.value()).toCharArray()
										: "".toCharArray());
					}
					// check if username is not null in ems.propeties then load
					// that
					if (isNotNull(prop.getProperty(customerName + QueueConstants.EMS_SERVER_USERNAME.value()))) {
						connectionPojo.setUserName(
								prop.getProperty(customerName + QueueConstants.EMS_SERVER_USERNAME.value()));
					} else {
						connectionPojo
								.setUserName(isNotNull(prop.getProperty(customerName + QueueConstants.USERNAME.value()))
										? prop.getProperty(customerName + QueueConstants.USERNAME.value()) : "");
					}
					// set active server url
					connectionPojo.setActiveEMSUrl(
							isNotNull(prop.getProperty(customerName + QueueConstants.SSL_ACTIVE.value()))
									? prop.getProperty(customerName + QueueConstants.SSL_ACTIVE.value()) : null);
					// set standby server url
					connectionPojo.setStandByEMSUrl(
							isNotNull(prop.getProperty(customerName + QueueConstants.SSL_STANDBY.value()))
									? prop.getProperty(customerName + QueueConstants.SSL_STANDBY.value()) : null);
					// set queue connection factory
					connectionPojo.setQueueConnectionFactory(
							isNotNull(prop.getProperty(customerName + QueueConstants.SSLQueueConnectionFactory.value()))
									? prop.getProperty(customerName + QueueConstants.SSLQueueConnectionFactory.value())
									: null);
					// set topic connection factory
					connectionPojo.setTopicConnectionFactory(
							isNotNull(prop.getProperty(customerName + QueueConstants.SSLTopicConnectionFactory.value()))
									? prop.getProperty(customerName + QueueConstants.SSLTopicConnectionFactory.value())
									: null);
				}
			}
			return connectionPojo;
		} catch (Exception e) {
			logger.error("Exception occured while loading property file for " + customer, e);
			throw new Exception("Exception occured while loading property file for " + customer, e);
		}

	}

	/**
	 * check whether a property is null or not
	 * 
	 * @param value
	 * @return
	 */
	private boolean isNotNull(String value) {
		return null != value && !value.isEmpty() ? true : false;
	}
}
