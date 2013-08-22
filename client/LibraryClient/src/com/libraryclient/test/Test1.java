package com.libraryclient.test;

import com.libraryclient.connection.*;
import com.libraryclient.content.*;
import java.io.*;
import javax.xml.*;
import javax.xml.parsers.*;
import org.junit.*;
import org.xml.sax.*;

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
			XmlHandler h = new XmlHandlerRawItem(new DefaultContentHandler(),new BasicRequest(1,""),Item.TYPE_BASIC);
			parser.parse(new FileInputStream(new File("people.xml")), h);
		} catch (ParserConfigurationException ex) {
		} catch (SAXException ex) {
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
