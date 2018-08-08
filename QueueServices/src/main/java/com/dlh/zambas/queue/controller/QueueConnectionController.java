package com.dlh.zambas.queue.controller;

import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dlh.zambas.queue.model.RequestPojo;
import com.dlh.zambas.queue.services.IQueueConnectionService;

/**
 * Client needs to pass customerName,queueName and payload to push message to a
 * queue. Different section from property "lh.properties" gets loaded based on
 * customerName.
 * 
 * @author singhg
 *
 */
@RestController
public class QueueConnectionController {
	private static final Logger logger = LoggerFactory.getLogger(QueueConnectionController.class);

	static {
		try {
			SSLContext context = SSLContext.getInstance("TLSv1.2");
			context.init(null, null, null);
			SSLContext.setDefault(context);
		} catch (Exception e) {
			logger.error("Exception occured while initializing TLSv1.2 context ", e);
		}

	}

	@Autowired
	private IQueueConnectionService queueConnectionService;

	@PostMapping("/push")
	public ResponseEntity pushMessageToQueueWithRequestId(@RequestBody RequestPojo requestPojo) {
		logger.info("PayLoad : " + requestPojo.getPayload());
		logger.info("Queue : " + requestPojo.getQueueName());
		return queueConnectionService.pushMessage(requestPojo);
	}
	
	
	@GetMapping("/fetch")
	public Map<String,String> returnHello(HttpSession session) throws InterruptedException {
		Map<String,String> model = new HashMap<String,String>();
	    model.put("gaurav", "hello");
	    Thread.sleep(300000);
	    return model;
	}
	
}
