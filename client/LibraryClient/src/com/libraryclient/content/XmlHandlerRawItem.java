package com.libraryclient.content;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.libraryclient.connection.Connector;

public class XmlHandlerRawItem extends XmlHandler {

	Map<String, Item> elements = new HashMap<String, Item>();
	/**
	 * Serves as a stack representing the current brunch of XML tree.The last
	 * element is the innermost parent or the last child of XML tree for the
	 * current depth.The first element is the primary parent of the whole
	 * tree.They are {@code String} names coded as
	 * {@code "uri + ":" + localName + ":" + qName"}
	 */
	Stack<String> mParentElements = new Stack<String>();
	Stack<Integer> mParentLevels = new Stack<Integer>();
	/**
	 * A map showing the maximum level or id of an element.
	 * <ul>
	 * {@code String} = Element name
	 * </ul>
	 * <ul>
	 * {@code Integer} = id or maximum level Whenever a new element is found,the
	 * map is looked up to found if the current element contains.If contains,an
	 * Integer +1 is appended to the String which is the key to Item of
	 * Name-Item map so that similar Names are avoided.
	 */
	private Map<String, Integer> mElementLevels = new HashMap<String, Integer>();

	private ContentHandler mContentHandler;

	/**
	 * Every time {@link onCharacters(char[] ch, int start, int length)} is
	 * called,this variable is incremented.The initial value is "zero".This can
	 * be reset by {@link resetItemCount()} and get by {@link getItemCount()}
	 * .This variable may count the parsed Tags or Elements,not guaranteed to be
	 * parsed till the end of the last Tag or Element and it does not show the
	 * depth of the tree,also counting inclusively the parent Tags or Elements.
	 * This only shows the count of the currently loading Tag or Element.
	 */
	private int mItemCounter = 0;

	private Connector mConnector = null;

	private ItemMultiplier mItemFactory;

	public XmlHandlerRawItem(ContentHandler h, Connector c, int itemTypeConst) {
		mContentHandler = h;
		mConnector = c;
		mItemFactory = new ItemMultiplier(itemTypeConst);
	}

	/*
	 * No need for this program but works
	 */
	@Override
	public void setDocumentLocator(Locator locator) {
		// System.out.println(locator + "........DocLocator");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#startDocument()
	 */
	@Override
	public void startDocument() throws SAXException {
		mContentHandler.onStartLoading(mConnector);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#endDocument()
	 */
	@Override
	public void endDocument() throws SAXException {
		mContentHandler.onFinishLoading(mConnector);
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
		// check and update current depth and level through mParentElements and
		// mParentLevels
		String eleName = uri + ":" + localName + ":" + qName;
		if (!mParentElements.isEmpty()) {
			int currentLevel = mElementLevels.containsKey(eleName) ? mElementLevels
					.get(eleName) + 1 : 0;
			mParentLevels.push(currentLevel);
			mElementLevels.put(eleName, currentLevel);
		} else {
			mParentLevels.add(0);
		}

		// create new item
		Item i = mItemFactory.newItem();// new Item(uri + ":" + localName + ":"
										// + qName);
		int l = attributes.getLength();

		// put attributes name-value pairs into the created item
		for (int j = 0; j < l; j++)
			i.addAttribute(attributes.getQName(j), attributes.getValue(j));

		// put the item 'i' into the map of elements
		elements.put(
				uri + ":" + localName + ":" + qName + ":"
						+ mParentLevels.peek(), i);

		// put the item 'i' into the parent item as a sub-item
		if (!mParentElements.isEmpty()) {
			Item parent = elements.get(mParentElements.peek() + ":"
					+ mParentLevels.toArray()[mParentLevels.size() - 2]);
			parent.addSubItem(i);
		}

		// put the name of the item 'i' into the element stack of XML tree
		mParentElements.add(uri + ":" + localName + ":" + qName);
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
		mContentHandler.onItemLoaded(
				mConnector,
				elements.get(mParentElements.pop() + ":"
						+ mParentLevels.pop()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO here will capture text data ,e.g. for
		// comments,review,descriptions
		// System.out.println(new String(ch, start,
		// length)+"<"+start+","+length);
		if (elements.isEmpty())
			return;
		Item item = elements.get(mParentElements.peek() + ":"
				+ mParentLevels.peek());
		item.concatItemValue(new String(ch, start, length));

		mContentHandler.onLoading(mConnector, item);
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
		// TODO needs more testing to find if this function is important
		// System.out.println(new String(ch, start, length));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.xml.sax.helpers.DefaultHandler#warning(org.xml.sax.SAXParseException)
	 */
	@Override
	public void warning(SAXParseException e) throws SAXException {
		e.printStackTrace(System.out);
		super.warning(e);
	}

	/*
	 * Errors will be shown every time, concerning with <!DOCTYPE ...>
	 */
	@Override
	public void error(SAXParseException e) throws SAXException {
		// TODO needs to print out correct error without those about <!DOCTYPE
		// ...>
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
