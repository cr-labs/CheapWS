package com.challengeandresponse.cheapws;

import javax.servlet.http.HttpServletRequest;

/**
 * Utilities for web services... tag modifications, cleanup and sanitizing inputs, and so on
 * 
 * @author jim
 *
 */
public class Utils {

	public static String disableAndTrimTags(String unescapedText) {
		if (unescapedText == null)
			return null;
		else {
			String s = unescapedText.trim().replaceAll(">", "&gt;").replaceAll("<", "&lt;");
			return s.replaceAll("\"", "&quot;").replaceAll("'", "&#39;");
		}
	}
	
	
	/**
	 * Get a String from a field on a form. The string will be trimmed and &gt; and &lt; will be escaped
	 * @param _request the request to draw the field value from
	 * @fieldName The field name on the form
	 * @dflt a default value to return, if the field was not found
	 * @return the string from the form field, or the value in 'dflt' if there was no such field
	 */
	public static String getStringParameter(HttpServletRequest  _request, String fieldName, String dflt) {
		String s = _request.getParameter(fieldName);
		if (s == null)
			return dflt;
		else
			return disableAndTrimTags(s);
	}

	/**
	 * Get an int from a field on a form.
	 * @param _request the request to draw the field value from
	 * @fieldName The field name on the form
	 * @dflt a default value to return, if the field was not found
	 * @return the int from the form field, or the value in 'dflt' if the field was not found or the field could not be parsed
	 */
	public static int getIntParameter(HttpServletRequest  _request, String fieldName, int dflt) {
		String s = _request.getParameter(fieldName);
		if (s == null)
			return dflt;
		try {
			return Integer.parseInt(s);
		}
		catch (NumberFormatException nfe) {
			return dflt;
		}
	}
	
	
	
	public static void main(String[] args) {
		System.out.println(disableAndTrimTags("hello><'\""));
	}


	
	

}
