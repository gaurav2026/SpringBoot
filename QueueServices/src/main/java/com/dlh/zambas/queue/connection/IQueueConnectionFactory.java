package com.dlh.zambas.queue.connection;


import javax.jms.QueueConnectionFactory;

import com.dlh.zambas.queue.model.ConnectionPojo;

public interface IQueueConnectionFactory {

	 QueueConnectionFactory returnFactory(String customer) throws Exception;

	ConnectionPojo getConnectionPojo();
}
