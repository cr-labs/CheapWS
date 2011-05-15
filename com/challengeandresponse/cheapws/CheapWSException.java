package com.challengeandresponse.cheapws;

/**
 * This package throws only CheapSOAPExceptions
 * @author jim
 * @version 0.20 2007-01-18
 *
 */
public class CheapWSException extends Exception {
	private static final long serialVersionUID = 1;
	public static final String COPYRIGHT="Copyright (c) 2007 Challenge/Response, LLC, Cambridge, MA";

	
	public CheapWSException() {
		super();
	}

	public CheapWSException(String arg0) {
		super(arg0);
	}
	
	
}