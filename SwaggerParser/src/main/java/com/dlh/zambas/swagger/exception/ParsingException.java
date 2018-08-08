package com.dlh.zambas.swagger.exception;

/**
 * exception occured while parsing json/yaml file
 * @author singhg
 *
 */
public class ParsingException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParsingException(String message){
		super(message);
	}
	
}
