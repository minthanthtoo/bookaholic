package com.libraryclient.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.libraryclient.connection.BasicRequest;
import com.libraryclient.content.DefaultContentHandler;
import com.libraryclient.content.Item;
import com.libraryclient.content.XmlHandler;
import com.libraryclient.content.XmlHandlerRawItem;

public class Test1 {

	@Test
	public void test() {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser;
		factory.setNamespaceAware(true); // DELETE:no need
		factory.setValidating(true);// DELETE:no need
		factory.setXIncludeAware(true);// DELETE:don't know
		try {
			factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);// DELETE:don't
																				// know
			// factory.setSchema(new Schema().getClass());
			parser = factory.newSAXParser();
			XmlHandler h = new XmlHandlerRawItem(new DefaultContentHandler(),
					new BasicRequest(1, ""), Item.TYPE_BASIC);
			parser.parse(new FileInputStream(new File("people.xml")), h);
		} catch (ParserConfigurationException ex) {
		} catch (SAXException ex) {
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
