package com.dlh.zambas.queue.controller.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.json.JSONObject;
import org.junit.Test;

import com.dlh.zambas.queue.connection.test.ConnectivityTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * @author singhg
 *
 */
public class QueueConnectionControllerTest extends ConnectivityTest {

	
	@Test
	public void pushMessageSuccessScenario() throws Exception {

		JSONObject json = new JSONObject();
		json.put("customer", "LH");
		json.put("PayLoad", "Dummy");
		json.put("queueName", "gaurav");
		json.put("RequestID", "1234");
		json.put("ApplicationID", "SMILE_APP");

		RequestSpecification request = RestAssured.given();

		request.body(json.toString());
		Response response = request.accept("application/json").contentType("application/json").post("/push")
				.thenReturn();
		assertThat(response.statusLine(), equalTo("HTTP/1.1 200 "));
		System.out.println(response.getStatusCode() + " *** " + response.getBody().asString());

	}
	
	
	@Test
	public void pushMessageInvalidScenarioWhenFieldIsMissing() throws Exception {
		JSONObject json = new JSONObject();
		json.put("customer", "LH");
		json.put("PayLoad", "Dummy");
		json.put("queueName", "gaurav");
		json.put("RequestID", "LH11");

		RequestSpecification request = RestAssured.given();

		request.body(json.toString());
		Response response = request.accept("application/json").contentType("application/json").post("/push")
				.thenReturn();
		assertThat(response.statusLine(), equalTo("HTTP/1.1 400 "));
		System.out.println(response.getStatusCode() + " *** " + response.getBody().asString());
	}
	
	

}
