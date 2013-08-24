package com.libraryclient.content;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.libraryclient.connection.Connector;

public abstract class ContentHandler<E extends Item> {
	private List<XmlHandler> mXmlHandlers = new LinkedList<XmlHandler>();

	private List<Connector> mRequests = new LinkedList<Connector>();

	private Map<String, Thread> mHandles = new HashMap<String, Thread>();

	public abstract void onItemLoaded(Connector r, E i);

	public abstract void onLoading(Connector r, E i);

	public abstract void onReload(Connector r);

	public abstract void onStartLoading(Connector r);

	public abstract void onFinishLoading(Connector r);

	public abstract void onStopLoading(Connector r);

	public abstract void onResumeLoading(Connector r);

	/**
	 * @param request
	 *            {@link com.libraryclient.connection.Connector} to handle
	 */
	public void startHandling(final Connector request) {
		mRequests.add(request);
		final InputStream is = request.getResponseStream();
		final XmlHandler h = new XmlHandlerRawItem(this, request,
				Item.TYPE_BASIC);
		mXmlHandlers.add(h);
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					SAXParserFactory factory = SAXParserFactory.newInstance();
					SAXParser parser;
					parser = factory.newSAXParser();
					int start = (int) System.currentTimeMillis();
					System.out.println("xml parse Start:" + start);
					parser.parse(is, h);
					System.out.println("xml parse End:\nDuration"
							+ ((int) System.currentTimeMillis() - start));
					System.out.println(new String(request.getResponseBytes()));

					is.close();
				} catch (ParserConfigurationException ex) {
				} catch (SAXException ex) {
				} catch (InterruptedIOException ex) {
					ex.printStackTrace();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}

		});
		mHandles.put(request.toString(), t);
		t.start();
	}

	/**
	 * This will stop the handling action of the request and call the {@link
	 * request.setRequestFinished(false)} method.
	 * 
	 * @param request
	 *            The {@code} Request object you want to stop
	 */
	public void stopHandling(Connector request) {

		mHandles.remove(request.toString()).interrupt();
		// request.setRequestFinished(false);
	}

	public void reloadHandling(Connector request) {
		stopHandling(request);
		startHandling(request);
	}
}
