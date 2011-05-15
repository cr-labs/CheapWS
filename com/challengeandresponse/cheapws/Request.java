package com.challengeandresponse.cheapws;

import java.util.Iterator;
import java.util.Vector;

/**
 * A CheapSOAP request... uses WSDL link, method name and a bunch of parameters to build a request
 * @author jim
 * @version 0.21 2007-01-19
 */
/*
 * REVISION HISTORY
 * 0.21 2007-01-26 Added Soapaction to constructor, along with the method name - these need to be automatically selected and paired off once the WSDL is being read
 */

public class Request {
	public static final String COPYRIGHT="Copyright (c) 2007 Challenge/Response, LLC, Cambridge, MA";
	
	public static final String VERB_POST = "POST";
	public static final String VERB_GET = "GET";
	

	private static String NEWLINE = System.getProperty("line.separator");

	private static String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	private static String ENVELOPE_HEADER =
		"<SOAP-ENV:Envelope"+NEWLINE+
		"xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\""+NEWLINE+
		"xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\""+NEWLINE+
		"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""+NEWLINE+
		"xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\""+NEWLINE+
		"SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">";
	private static String BODY_HEADER = "<SOAP-ENV:Body>";
	private static String BODY_FOOTER = "</SOAP-ENV:Body>";
	private static String ENVELOPE_FOOTER = "</SOAP-ENV:Envelope>";

	// the CheapSOAP instance that is controlling things here, central registry for common stuff
	// ultimately CheapSOAP class will know a lot from processing the related WSDL
	private CheapSOAP cs;

	private Vector <String> params = new Vector <String> ();
	private String methodName;
	private String soapAction;
	private String verb;
	

	
	/**
	 * 
	 * @param cs a CheapSOAP instance that's initialized and ready to go
	 * @param methodName name of the method to invoke
	 * @param soapAction the soapAction header that goes with this method
	 * @param verb either VERB_POST or VERB_GET
	 */
	public Request(CheapSOAP cs, String methodName, String soapAction, String verb)
	throws CheapWSException {
		this.cs = cs;
		this.methodName = methodName;
		this.soapAction = soapAction;
		if (! (verb.equals(VERB_POST) || verb.equals(VERB_GET)) )
			throw new CheapWSException("verb must be VERB_POST or VERB_GET");
		this.verb = verb;
	}
	
	public String getSoapAction() {
		return this.soapAction;
	}
	
	public String getVerb() {
		return this.verb;
	}
	
	
	public String getComposedRequest() {
		StringBuffer sb = new StringBuffer();
		//HEADER
		sb.append(XML_HEADER+NEWLINE);
		sb.append(ENVELOPE_HEADER+NEWLINE);
		sb.append(BODY_HEADER+NEWLINE);
		// METHOD
		sb.append("<m:"+methodName+" xmlns:m=\""+cs.getWSDLString()+"\">"+NEWLINE);
		// PARAMS
		Iterator <String> it = params.iterator();
		while (it.hasNext()) {
			sb.append(it.next());
		}
		sb.append("</m:"+methodName+">"+NEWLINE);
		sb.append(BODY_FOOTER+NEWLINE);
		sb.append(ENVELOPE_FOOTER+NEWLINE);

		return sb.toString();
	}
	

	
	
	
	/**
	 * Add a parameter, explicitly providing name, type and value.
	 * for example: "latitude", "decimal", "-71.0"
	 */
	public void addParam(String name, String type, String value) {
		params.add("<"+name+" xsi:type=\"xsd:"+type+"\">"+value+"</"+name+">");
	}
	
	/**
	 * Add an integer parameter, using "integer" as the xsd:type
	 * @param name the parameter name
	 * @param value the value
	 */
	public void addParam(String name, int value) {
		addParam(name,"integer",value+"");
	}

	/**
	 * Add a double parameter, using "decimal" as the xsd:type
	 * @param name the parameter name
	 * @param value the value
	 */
	public void addParam(String name, double value) {
		addParam(name,"decimal",value+"");
	}
	
	/**
	 * Add a float parameter, using "decimal" as the xsd:type
	 * @param name the parameter name
	 * @param value the value
	 */
	public void addParam(String name, float value) {
		addParam(name,"decimal",value+"");
	}

	/**
	 * Add a string parameter, using "string" as the xsd:type
	 * @param name the parameter name
	 * @param value the value
	 */
	public void addParam(String name, String value ){
		addParam(name,"string",value);
	}

	

	
}