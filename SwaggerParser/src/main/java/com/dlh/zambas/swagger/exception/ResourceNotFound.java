package com.dlh.zambas.swagger.exception;

/**
 * exception thrown if resource is not found
 * @author singhg
 *
 */
public class ResourceNotFound extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNotFound(String message){
		super(message);
	}
}
