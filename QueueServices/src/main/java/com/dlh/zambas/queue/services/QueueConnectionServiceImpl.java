package com.dlh.zambas.queue.services;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dlh.zambas.queue.connection.IQueueConnectionFactory;
import com.dlh.zambas.queue.constants.QueueConstants;
import com.dlh.zambas.queue.model.RequestPojo;
import com.dlh.zambas.queue.model.ResponsePojo;

/**
 * Request from Controller is forwarded to this "Service" class
 * 
 * @author singhg
 *
 */
@Service
public class QueueConnectionServiceImpl implements IQueueConnectionService {
	@Autowired
	private IQueueConnectionFactory queueConnectionImpl;

	private static final Logger logger = LoggerFactory.getLogger(QueueConnectionServiceImpl.class);

	/**
	 * return factory and then push message to specified queue
	 */
	@Override
	public ResponseEntity pushMessage(RequestPojo requestPojo) {
		ResponsePojo response = new ResponsePojo();
		try {
			if (null != requestPojo.getCustomer() && !requestPojo.getCustomer().trim().isEmpty()
					&& null != requestPojo.getQueueName() && !requestPojo.getQueueName().trim().isEmpty()
					&& null != requestPojo.getPayload() && !requestPojo.getPayload().trim().isEmpty()
					&& null != requestPojo.getApplicationId() && !requestPojo.getApplicationId().trim().isEmpty()
					&& null != requestPojo.getRequestId() && !requestPojo.getRequestId().trim().isEmpty()) {

				// get Connection factory
				QueueConnectionFactory factory = queueConnectionImpl.returnFactory(requestPojo.getCustomer());
				// push message to a queue after establishing connection using
				// factory
				pushMessageToQueue(factory, requestPojo);
				response.setCode(HttpStatus.OK.value());
				response.setSuccess(requestPojo.getPayload() + " sent ");
				return new ResponseEntity(response, HttpStatus.OK);
			} else {
				response.setCode(HttpStatus.BAD_REQUEST.value());
				response.setFailure("Mandatory parameters are missing");
				return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.debug("Exception : ", e);
			response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setFailure(requestPojo.getPayload() + " could not be sent");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private void pushMessageToQueue(QueueConnectionFactory factory, RequestPojo requestPojo) throws Exception {
		// establish connection using factory
		try(QueueConnection connection = factory.createQueueConnection(queueConnectionImpl.getConnectionPojo().getUserName(),
					new String(queueConnectionImpl.getConnectionPojo().getPassword()))) {
			logger.debug("Start sending message ...");
			
			QueueSession session = connection.createQueueSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);

			// Use createQueue() to enable sending into dynamic queues.
			Queue senderQueue = session.createQueue(requestPojo.getQueueName());
			QueueSender sender = session.createSender(senderQueue);

			// send message
			TextMessage jmsMessage = session.createTextMessage();
			jmsMessage.setStringProperty(QueueConstants.CustomerID.toString(), requestPojo.getCustomer());
			jmsMessage.setStringProperty(QueueConstants.RequestID.toString(), requestPojo.getRequestId());
			jmsMessage.setStringProperty(QueueConstants.ApplicationID.toString(), requestPojo.getApplicationId());
			jmsMessage.setText(requestPojo.getPayload());
			sender.send(jmsMessage);
			logger.info("Sent message = " + jmsMessage.getText());

		} catch (JMSException e) {
			logger.error("Exception occured while sending message to ", e);
			throw new Exception("Exception occured while sending message to ", e);
		} 
	}

}
