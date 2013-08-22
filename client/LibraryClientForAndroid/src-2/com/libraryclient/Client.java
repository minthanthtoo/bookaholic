package com.libraryclient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.libraryclient.config.Connection;
import com.libraryclient.connection.Connector;
import com.libraryclient.content.ContentHandler;

public class Client
{

	private boolean mConnected = false;
	private Map<Integer, ContentHandler> mRequestHandlerMap = new HashMap<Integer, ContentHandler>();
	private List<Connector> mRequests = new LinkedList<Connector>();

	public boolean connect(String username, String password, Proxy proxy)
	{
		Connection.USERNAME = username;
		Connection.PASSWORD = password;
		Connection.PROXY = proxy;
		return connect();
	}

	public boolean connect(String username, String password)
	{
		Connection.USERNAME = username;
		Connection.PASSWORD = password;
		// TODO proxy here;
		if (Connection.PROXY == null)
		// Connection.PROXY=new Proxy()
			;
		return connect();
	}

	private boolean connect()
	{
		// TODO username,password,proxy here
		if (Connection.USERNAME == null)
			;
		if (Connection.PASSWORD == null)
			;
		if (Connection.PROXY == null || Connection.PROXY == Proxy.NO_PROXY)
			;
		else
		{
			System.setProperty("http.proxyHost",
							   ((InetSocketAddress) Connection.PROXY.address())
							   .getHostName());

			System.setProperty("http.proxyPort",
							   ((InetSocketAddress) Connection.PROXY.address()).getPort()
							   + "");
		}

		// TODO connect now
		try
		{
			new URL(Connection.CONNECTION_WEBSITE)
				.openConnection(Connection.PROXY);
			mConnected = true;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return mConnected;
	}

	public boolean setProxy(Proxy p)
	{
		if (mConnected = false)
			Connection.PROXY = p;
		return !mConnected;
	}

	public boolean isConnected()
	{
		return mConnected;
	}

	public void post(Connector r)
	{

		// TODO files ,post upload(HTTP PUT)
	}

	public void request(Connector r) throws IOException
	{
		r.connect();

		// add the request to request list
		if (!mRequests.contains(r))
			mRequests.add(r);
	}

	/**
	 * This method must be called after {@link request()},or {@link post()}
	 * method.
	 * 
	 */
	public void loadContent(Connector r) throws IOException
	{

		r.handleResponse(this.mRequestHandlerMap.get(r.getConnectCode()));
		//mRequestHandlerMap.get(new Integer(r.RequestCode)).onStartLoading(r);
	}

	public void stopLoading(Connector r)
	{

		r.stopResponseHandling(this.mRequestHandlerMap.get(r.getConnectCode()));
		//mRequestHandlerMap.get(new Integer(r.RequestCode)).onStopLoading(r);
	}

	public void resumeLoading(Connector r)
	{

		// TODO for later implementation
		//mRequestHandlerMap.get(new Integer(r.RequestCode)).onResumeLoading(r);
	}

	public void reloadResult(Connector r) throws IOException
	{

		r.stopResponseHandling(this.mRequestHandlerMap.get(r.getConnectCode()));
		r.handleResponse(this.mRequestHandlerMap.get(r.getConnectCode()));
		//mRequestHandlerMap.get(new Integer(r.RequestCode)).onReload(r);
	}

	/**
	 * You will needs to access this method before calling {@link
	 * Client.connect()} method.
	 * 
	 * @param requestCode
	 *            Integer code number that will communicate with PHP server.The
	 *            server will return XML content corresponding with this code.
	 * @param h
	 *            Content Handler that is capable of handling XML data with the
	 *            predefined behavior
	 */
	public void setContentHandler(int requestCode, ContentHandler h)
	{
		mRequestHandlerMap.put(requestCode, h);
	}
}