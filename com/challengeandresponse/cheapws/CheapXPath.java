package com.challengeandresponse.cheapws;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Import an XML document and support XPATH-like retrieval of its contents
 * 
 * @author jim youll
 */

public class CheapXPath {
	
	private DocumentBuilderFactory factory;
	private Document doc;
	
	public CheapXPath() {
        factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
	}

	
	
	/**
	 * A DocumentBuilder factory... synchronized so only one method is in here at a time
	 * ... and attempt to improve performance
	 * @return a DocumentBuilder
	 * @throws ParserConfigurationException
	 */
	private synchronized DocumentBuilder getDocumentBuilder()
	throws ParserConfigurationException {
		return factory.newDocumentBuilder();
	}
	
	
	/**
	 * Turn 'xmlSource' into a Document that can be parsed and traversed
	 * @param xmlSource The source XML to import and parse for traversal
	 * @throws Exception if document parsing failed
	 */
	public void loadDocument(String xmlSource) 
	throws CheapWSException {
		try {
			doc = getDocumentBuilder().parse(new InputSource(new StringReader(xmlSource)));
		}
		catch (IOException ioe) {
			throw new CheapWSException("IO Exception: "+ioe.getMessage());
		}
		catch (ParserConfigurationException pce) {
			throw new CheapWSException("Parser Configuration Exception: "+pce.getMessage());
		}
		catch (SAXException saxe) {
			throw new CheapWSException("SAX Exception: "+saxe.getMessage());
		}
	}
	
	
	/**
	 * Traverse a slash-delimited path into a Document, and return the Element that is the last node. This is case-sensitive
	 * @param path to the desired element, e.g. "/Response/Status/Code"
	 * @return The Element at the end of the path, or null if the element was not found
	 */
	// UNTESTED RECURSIVE VERSION OF digXML -- THIS WOULD BE BEST, REPLACING THE METHOD BELOW
//	public Element digXML(Element el, String path) {
//		String[] pathItems = path.split("/");
//		if (pathItems.length == 0)
//			return el;
//		NodeList nl = el.getElementsByTagName(pathItems[0]);
//		el = (Element) nl.item(0);
//		if (el == null)
//			return null;
//		else if (el instanceof Element) {
//			StringBuffer remainingPath = new StringBuffer();
//			for (int i = 1; i < pathItems.length; i++)
//				remainingPath.append("/").append(pathItems[i]);
//			return digXML(el,remainingPath.toString());
//		}
//		else 
//			return null;
//	}

	
	

	/**
	 * Traverse a slash-delimited path into a Document, and return the Element that is the last node. This is case-sensitive
	 * @param path to the desired element, e.g. "/Response/Status/Code"
	 * @return The Element at the end of the path, or null if the element was not found
	 */
	public Element digXML(String path) {
		if (path == null)
			return null;
		String[] nodes = path.split("/");
		Element el = doc.getDocumentElement();
		for (int i = 0; i < nodes.length; i++) {
			NodeList nl = el.getElementsByTagName(nodes[i]);
			el = (Element) nl.item(0);
			if (el == null)
				return null;
		}
		return el;
	}
	
	/**
	 * Traverse a slash-delimited path into a Document, and return the TEXT from the last node
	 * @param path to the desired element, e.g. "/Response/Status/Code". This is case-sensitive
	 * @return The text content of the Element at the end of the path
	 */
	public String digXMLText(String path) {
		Element el = digXML(path);
		if (el == null)
			return null;
		else
			return el.getTextContent();
	}

	/**
	 * Traverse a slash-delimited path into a Document, and return an attribute of the last node
	 * @param path to the desired element, e.g. "/Response/Status/Code".  This is case-sensitive
	 * @param attribute the attribute to return
	 * @return The value of the attribute 
	 */
	public String digXMLAttribute(String path, String attributeName) {
		Element el = digXML(path);
		if (el == null)
			return null;
		else
			return el.getAttribute(attributeName);
	}



	public Document getDocument() {
		return doc;
	}
	
	
	
	
}