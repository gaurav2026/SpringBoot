package com.dlh.zambas.swagger.controller.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.json.JSONObject;
import org.junit.Test;

import com.dlh.zambas.swagger.connection.test.ConnectivityTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * @author singhg
 *
 */
public class SwaggerControllerTest extends ConnectivityTest {

	@Test
	public void parseSwagger() throws Exception {

		JSONObject json = new JSONObject();
		json.put("fileURL", "http://localhost:9095/softconex/baggage_tracking_swagger.txt");

		RequestSpecification request = RestAssured.given();

		request.body(json.toString());
		Response response = request.accept("application/xml").contentType("application/json").post("/url").thenReturn();
		assertThat(response.statusLine(), equalTo("HTTP/1.1 200 "));
		System.out.println(response.getStatusCode() + " *** " + response.getBody().asString());

	}

}
