package com.challengeandresponse.cheapws;

import java.io.*;
import java.net.*;


/**
 * If you're lucky, you only need to instantiate this class
 * and then call methods on it
 * 
 * @author jim
 * @version 0.21 2007-01-19
 */
/*
 * REVISION HISTORY
 * 0.21 2007-01-19 Added Soapaction: header (action is stored in the Request along with the method name - these need to be automatically selected and paired off once the WSDL is being read
 */
public class CheapSOAP {
	public static final String PRODUCT="Challenge/Response CheapSOAP";
	public static final String VERSION="0.21";
	public static final String COPYRIGHT="Copyright (c) 2007 Challenge/Response, LLC, Cambridge, MA";

	private URL wsdl;
	private URL endpoint;
	
	private static String NEWLINE = System.getProperty("line.separator");

	
	public CheapSOAP(URL wsdlUrl, URL endpointUrl) {
		this.wsdl = wsdlUrl;
		this.endpoint = endpointUrl;
	}

	public CheapSOAP(String wsdlUrl, String endpointUrl)
	throws CheapWSException {
		try {
			this.wsdl = new URL(wsdlUrl);
			this.endpoint = new URL(endpointUrl);
		}
		catch (MalformedURLException mue) {
			throw new CheapWSException("Malformed URL:"+wsdlUrl);
		}
	}

	public String getWSDLString() {
		return wsdl.toString();
	}
	
	public URL getWSDLUrl() {
		return wsdl;
	}
	
	public URL getEndpointUrl() {
		return endpoint;
	}
	

	/**
	 * Post a string to an URL and get the reply as a string. Returns an empty
	 * string if things didn't work out.
 	 */
	public Response postXML(Request r)
	throws Exception {
		String req = r.getComposedRequest();
	            HttpURLConnection conn = (HttpURLConnection) getEndpointUrl().openConnection(); 
	            conn.setRequestMethod(r.getVerb());
	            conn.setAllowUserInteraction(false);
	            conn.setDoOutput(true);
	            conn.setRequestProperty("Soapaction",r.getSoapAction());
	            conn.setRequestProperty("Content-type", "text/xml");
	            conn.setRequestProperty("Content-length", req.length()+"");
	            
	            // send
	            PrintWriter pw = new PrintWriter(conn.getOutputStream());
	            pw.print(req);
	            pw.flush();
	            pw.close();

	            // get response
	            StringBuffer sb = new StringBuffer();
	            String line;
	            BufferedReader rdr = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            while ((line = rdr.readLine()) != null) {
	                sb.append(line);
	                sb.append(NEWLINE);
	            }
	            return new Response(sb);
	    }

	

}

