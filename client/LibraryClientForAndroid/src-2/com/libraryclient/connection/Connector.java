package com.libraryclient.connection;

import com.libraryclient.content.*;
import java.io.*;
import java.net.*;
import java.util.*;

import com.libraryclient.content.ContentHandler;

public abstract interface Connector extends ResponseCodeHandler
{
	URI getURI();

	int getConnectCode();

	String getConnectData();

	boolean isConnected();

	public void addAuthentication(Authentication auth);

	void addHeader(String name, String value);

	public void connect() throws IOException;

	public void handleResponse(ContentHandler contentHandler)
	throws IOException;

	public void stopResponseHandling(ContentHandler h);

	public String getStatusLine();
	
	InputStream getResponseStream();
	
	byte[] getResponseBytes();

	String getTargetResponseTag();
}
