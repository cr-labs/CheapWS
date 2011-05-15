package com.challengeandresponse.cheapws;


/**
 * Holds the value of a response to a SOAP query, and operations on the response
 * @author jim
 * @version 0.20 2007-01-18
 */
public class Response {
	public static final String COPYRIGHT="Copyright (c) 2007 Challenge/Response, LLC, Cambridge, MA";

	private String responseText;
	
	public Response(String responseText) {
		this.responseText = responseText;
	}
	
	public Response(StringBuffer responseText) {
		this.responseText = responseText.toString();
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}
	
	public void setResponseText(StringBuffer responseText) {
		this.responseText = responseText.toString();
	}
	
	
	/**
	 * Remove the SOAP envelope from around the response. The SOAP stuff is wrapped in
	 * &lt; .... &gt; markers, so it's easy to find and zot.
	 * This leaves ONLY the XML content of the response... after stripping the envelope,
	 * call unescape() to turn the escaped entities in the response back into 
	 * proper XML. At that point, the response will just be a lovely XML block...
	 */
	public void stripSOAPEnvelope() {
		responseText = responseText.replaceAll("<.*?>","");
	}

	/**
	 * un-escape the escaped contained sequences in the response (&gt, &lt, &quot, &apos).
	 * Much of the time you will want to call stripSOAPEnvelope() and then unescape() 
	 * in succession.
	 */
	public void unescape() {
		responseText = responseText.replaceAll("&gt;", ">");
		responseText = responseText.replaceAll("&lt;", "<");
		responseText = responseText.replaceAll("&quot;", "\"");
		responseText = responseText.replaceAll("&apos;", "'");
	}
	
	public String toString() {
		return responseText;
	}
	
}