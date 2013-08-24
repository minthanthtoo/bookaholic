package com.libraryclient.content;

import java.io.IOException;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XmlHandlerTest extends XmlHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#resolveEntity(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public InputSource resolveEntity(String publicId, String systemId)
			throws IOException, SAXException {
		System.out.print(publicId + ":" + systemId + ".....resolvedentity");
		return super.resolveEntity(publicId, systemId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#notationDecl(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void notationDecl(String name, String publicId, String systemId)
			throws SAXException {
		System.out.print(name + ":" + publicId + ":" + systemId
				+ ".........notationDecl");
		super.notationDecl(name, publicId, systemId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.xml.sax.helpers.DefaultHandler#unparsedEntityDecl(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void unparsedEntityDecl(String name, String publicId,
			String systemId, String notationName) throws SAXException {
		System.out.print(name + ":" + publicId + ":" + systemId + ":"
				+ notationName + ".....UnparsedentityDecl");
		super.unparsedEntityDecl(name, publicId, systemId, notationName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.xml.sax.helpers.DefaultHandler#setDocumentLocator(org.xml.sax.Locator
	 * )
	 */
	@Override
	public void setDocumentLocator(Locator locator) {
		System.out.print(locator + "........DocLocator");
		super.setDocumentLocator(locator);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#startDocument()
	 */
	@Override
	public void startDocument() throws SAXException {
		System.out.print("StART document");
		super.startDocument();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#endDocument()
	 */
	@Override
	public void endDocument() throws SAXException {
		System.out.print("End document");
		super.endDocument();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.xml.sax.helpers.DefaultHandler#startPrefixMapping(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
		// TODO Auto-generated method stub
		System.out.print(prefix + ":" + uri + ".....starPrefixMapping");
		super.startPrefixMapping(prefix, uri);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.xml.sax.helpers.DefaultHandler#endPrefixMapping(java.lang.String)
	 */
	@Override
	public void endPrefixMapping(String prefix) throws SAXException {
		System.out.print(prefix + ".........endPrefixMapping");
		super.endPrefixMapping(prefix);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
	 * java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		System.out.print(uri + ":" + localName + ";");
		int l = attributes.getLength() - 1;

		if (l >= 0)
			System.out.print("attribs");
		for (; l >= 0; l--)
			System.out.print(attributes.getValue(l) + ":"
					+ attributes.getQName(l));
		super.startElement(uri, localName, qName, attributes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		System.out.print(uri + ":" + localName);
		super.endElement(uri, localName, qName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		System.out.print(new String(ch, start, length) + "(" + ch.length + ")");
		super.characters(ch, start, length);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#ignorableWhitespace(char[], int,
	 * int)
	 */
	@Override
	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
		System.out.print(new String(ch, start, length) + "<" + ch.length + ">");
		super.ignorableWhitespace(ch, start, length);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.xml.sax.helpers.DefaultHandler#processingInstruction(java.lang.String
	 * , java.lang.String)
	 */
	@Override
	public void processingInstruction(String target, String data)
			throws SAXException {
		System.out.print(target + ":" + data + ".........processIntruct");
		super.processingInstruction(target, data);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#skippedEntity(java.lang.String)
	 */
	@Override
	public void skippedEntity(String name) throws SAXException {
		// TODO Auto-generated method stub
		System.out.print("Skipped entity" + name);
		super.skippedEntity(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.xml.sax.helpers.DefaultHandler#warning(org.xml.sax.SAXParseException)
	 */
	@Override
	public void warning(SAXParseException e) throws SAXException {
		e.printStackTrace(System.err);
		super.warning(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.xml.sax.helpers.DefaultHandler#error(org.xml.sax.SAXParseException)
	 */
	@Override
	public void error(SAXParseException e) throws SAXException {
		// e.printStackTrace(System.err);
		super.error(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.xml.sax.helpers.DefaultHandler#fatalError(org.xml.sax.SAXParseException
	 * )
	 */
	@Override
	public void fatalError(SAXParseException e) throws SAXException {
		e.printStackTrace(System.err);
		super.fatalError(e);
	}

}
