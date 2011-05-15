package com.challengeandresponse.cheapws;

import java.io.*;
import java.net.*;

public class CheapREST {
	
	public static final String PRODUCT="Challenge/Response CheapREST";
	public static final String VERSION="0.12";
	public static final String COPYRIGHT="Copyright (c) 2007 Challenge/Response, LLC, Cambridge, MA";

	private static String NEWLINE = System.getProperty("line.separator");

	
	public CheapREST() {
		
	}
	
	
	public String getREST(URL encodedQueryURL)
	throws CheapWSException {
	 return getREST(encodedQueryURL,true);
	}
	
/**
	 * Get a REST query given a URL, and return the response
	 * @param encodedQueryURL the entire composed URL query, encoded (use URLEncoder.encode() on the query part)
	 * @param addLineEnding if true, the local line-ending character is added to every line as it's read
	 * @return the response to the REST query, with newlines between lines received, no changes
	 */
	public String getREST(URL encodedQueryURL, boolean addLineEnding)
	throws CheapWSException {
		HttpURLConnection conn = null;
		StringBuffer sb = null;
		String newURLs = null;
		try	{
			// the REST query
			conn = (HttpURLConnection) encodedQueryURL.openConnection(); 
			conn.setRequestMethod("GET");
			conn.setAllowUserInteraction(false);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-type", "text/xml");
			conn.connect();
			// get response
			sb = new StringBuffer();
			String line;
			BufferedReader rdr = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while ((line = rdr.readLine()) != null) {
				sb.append(line);
				if (addLineEnding)
					sb.append(NEWLINE);
			}
		}
		catch (MalformedURLException mue) {
			throw new CheapWSException("Malformed query URL:"+newURLs);
		}
		catch (IOException ioe) {
			throw new CheapWSException("IO Exception:"+ioe.getMessage());
		}
		return sb.toString();
	}
}
