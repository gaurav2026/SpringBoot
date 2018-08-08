package com.dlh.zambas.queue.services;

import org.springframework.http.ResponseEntity;

import com.dlh.zambas.queue.model.RequestPojo;

public interface IQueueConnectionService {

	 ResponseEntity pushMessage(RequestPojo requestPojo);
}
