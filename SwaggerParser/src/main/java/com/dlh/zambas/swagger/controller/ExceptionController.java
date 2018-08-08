package com.dlh.zambas.swagger.controller;

import java.io.FileNotFoundException;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import com.dlh.zambas.swagger.exception.ExceptionResponse;
import com.dlh.zambas.swagger.exception.ParsingException;
import com.dlh.zambas.swagger.exception.ResourceNotFound;

/**
 * exception controller gets called whenever an exception is occured
 */

@ControllerAdvice
@RestController
public class ExceptionController extends DefaultHandlerExceptionResolver {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

	/**
	 * exception thrown while parsing json/yaml
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(ParsingException.class)
	public ResponseEntity<ExceptionResponse> serverException(ParsingException exception) {
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorMessage(exception.getMessage());
		response.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR);
		logger.error("Parsing exception thrown ", exception);
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * exception thrown if mandatory parameters are missing
	 * @param exception
	 * @return
	 */

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ExceptionResponse> missingParamException(HttpMessageNotReadableException exception) {
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorMessage("Body cannot be null");
		response.setErrorCode(HttpStatus.BAD_REQUEST);
		logger.error("Body missing in request : ", exception);
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * exception thrown if mandatory parameters are missing
	 * @param exception
	 * @return
	 */

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ExceptionResponse> missingParameterException(
			MissingServletRequestParameterException exception) {
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorMessage("Body cannot be null");
		response.setErrorCode(HttpStatus.BAD_REQUEST);
		logger.error("Body missing in request : ", exception);
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * exception thrown if json/yaml file is not present at specified location
	 * @param exception
	 * @return
	 */
	@ExceptionHandler({ NoSuchFileException.class, FileNotFoundException.class, InvalidPathException.class })
	public ResponseEntity<ExceptionResponse> fileNotFound(Exception exception) {
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorMessage("File not present or location couldn't be reached at " + exception.getMessage());
		response.setErrorCode(HttpStatus.NOT_FOUND);
		logger.error("File URL not reachable: ", exception);
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.NOT_FOUND);
	}

	/**
	 * exception thrown when resource is missing
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(ResourceNotFound.class)
	public ResponseEntity<ExceptionResponse> resourceNotFound(ResourceNotFound exception) {
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorMessage(exception.getMessage());
		response.setErrorCode(HttpStatus.NOT_FOUND);
		logger.error("Resource not found :  ", exception);
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.NOT_FOUND);
	}

	/**
	 * generic exception
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> serverException(Exception exception) {
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorMessage(exception.getMessage());
		response.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR);
		logger.error("Exception thrown while converting swagger to WADL ", exception);
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
