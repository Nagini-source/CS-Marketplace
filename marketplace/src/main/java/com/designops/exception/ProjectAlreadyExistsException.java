package com.designops.exception;

public class ProjectAlreadyExistsException extends RuntimeException {
	
	public ProjectAlreadyExistsException(String message)
	{
		super(message);
	}
	
	public ProjectAlreadyExistsException(String message,Throwable cause)
	{
		super(message,cause);
	}
	
	public ProjectAlreadyExistsException(Throwable cause)
	{
		super(cause);
	}

}