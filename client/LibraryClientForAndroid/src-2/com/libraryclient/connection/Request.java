package com.libraryclient.connection;

import android.net.*;
import com.libraryclient.config.*;
import com.libraryclient.content.*;
import java.io.*;
import java.net.*;
import java.util.*;

import com.libraryclient.content.ContentHandler;
import java.net.Proxy;

public abstract class Request implements Connector
{

	private HttpURLConnection mConnection;
	private URI url;
	private int RequestCode;
	private String RequestData;
	private Map<String,String> mHeaders= new HashMap<String,String>();

	private boolean isRequestFinished = false;

	private FileOutputStream mLocalStream = null;
	private InputStream mRemoteStream = null;
	private ByteArrayOutputStream mResponseBytes= new ByteArrayOutputStream();
	private Thread mResultLoader = null;
	
	private ResponseCodeHandler mResponseCodeHandler= new DefaultResponseCodeHandler();;
	
	/**
	 * {@link InputStream } for {@link XMLHandler}
	 * 
	 */
	private InputStream mXmlStream = null;

	private Authentication mAuthentication;

	public Request(int requestCode, String requestData)
	{
		url = URI.create(Connection.CONNECTION_WEBSITE + "/?");
		this.RequestCode = requestCode;
		this.RequestData = requestData;
	}

	public URI getURI()
	{
		return this.url;
	}

	public int getConnectCode()
	{
		return RequestCode;
	}

	public String getConnectData()
	{
		return RequestData;
	}

	public boolean isConnected()
	{
		return isRequestFinished;
	}

	public void addAuthentication(Authentication auth)
	{
		mAuthentication = auth;
		auth.authenticate(this);
	}

	public void addHeader(String name, String value)
	{
		mHeaders.put(name, value);
	}
	public void request(Proxy p) throws IOException
	{
		// TODO need more exact implementation
		isRequestFinished = false;
		mConnection = (HttpURLConnection) ((p != null) ? url.toURL()
			.openConnection(p) : url.toURL().openConnection());
		mConnection.setDoInput(true);
		mConnection.setUseCaches(true);
		mConnection.setDoOutput(true);
		mConnection.setAllowUserInteraction(true);
		mConnection.setRequestMethod("POST");
		addAuthentication(new BasicAuthentication(Connection.USERNAME,Connection.PASSWORD));

		// set header fields
		Iterator it=mHeaders.entrySet().iterator();
		while (it.hasNext())
		{
			Map.Entry e=(Map.Entry) it.next();
			mConnection.addRequestProperty((String)e.getKey(), (String)e.getValue());
			System.out.println(e.getKey()+":"+mConnection.getRequestProperty((String)e.getKey()));
		}

		mConnection.connect();

		String post =
			Connection.REQUEST_CODE_HEADER + "=" + RequestCode + "&"
			+ Connection.REQUEST_DATA_HEADER + "=" + RequestData;
		Writer o = new OutputStreamWriter(mConnection.getOutputStream());
		o.write(post);
		o.close();
		onResponse(mConnection.getResponseCode());
		isRequestFinished = true;
	}

	public void connect() throws IOException
	{
		request(null);
	}

	public void onResponse(int responseCode)
	{
		mResponseCodeHandler.onResponse(RequestCode);
	}
	
	/**
	 * This method does:
	 * <ul>
	 * 1) Get {@link BufferedInputStream} from remote {@link URLConnection}
	 * </ul>
	 * <ul>
	 * 2) If {@link com.libraryclient.config.Connection.CACHE_DIR} vairable is
	 * set( or cache dir is chosen),a cache file of
	 * {@code "CACHE_DIR.RequestCode.SystemCurrentTimeMillis.request"} is
	 * created.
	 * </ul>
	 * <ul>
	 * 3) If cache file is created,{@link OutputStream} of that file is set to
	 * that of this class. If not,direct {@link InputStream} of remote
	 * {@link URLConnection} is set.
	 * 
	 * @param contentHandler
	 *            {@link ContentHandler} for this current loading of results
	 * 
	 */
	public void handleResponse(ContentHandler contentHandler)
	{

		// Remote connection is now opened
		try
		{
			mRemoteStream = new CloneInputStream(
				mConnection.getInputStream(),mResponseBytes, Connection.BUFFER_SIZE);
			System.out.println(	mConnection.getResponseMessage());
		}
		catch (IOException e1)
		{
			System.err
				.println("Error in creating InputStream from remote site");
			e1.printStackTrace();
		}

		// if caching is supported
		if (Connection.CACHE_DIR != null && Connection.CACHE_DIR != "")
		{
			// Create cache file
			File cacheFile = new File(Connection.CACHE_DIR + "/"
									  + this.RequestCode + "." + System.currentTimeMillis()
									  + ".request.html");
			if (!cacheFile.exists())
				try
				{
					cacheFile.createNewFile();
					mLocalStream = new FileOutputStream(cacheFile);
				}
				catch (IOException e)
				{
					System.err.println("Error in creating cache file.\n");
					e.printStackTrace();
				}
			// cacheFile.deleteOnExit(); // TODO comment out this

			// implements a thread for downloading results
			mResultLoader = new Thread(new Runnable() {

					@Override
					public void run()
					{
						try
						{
							byte[] in = new byte[Connection.BUFFER_SIZE];
							while ((mRemoteStream.read(in)) != -1)
								mLocalStream.write(in);
							mRemoteStream.close();
							mLocalStream.close();
						}
						catch (IOException e)
						{
							System.err.println("Error in downloading results!");
							e.printStackTrace();
						}
					}

				});
			// start downloading results
			mResultLoader.start();
			try
			{
				this.setContentStream(new FileInputStream(cacheFile));
			}
			catch (FileNotFoundException e)
			{
				System.err.println("Error in reading cache file");
				e.printStackTrace();
			}

		}
		else
		{// if caching is not supported
			this.setContentStream(mRemoteStream);
		}
		contentHandler.startHandling(this);

	}

	public void stopResponseHandling(ContentHandler h)
	{
		h.stopHandling(this);
		try
		{
			mRemoteStream.close();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setContentStream(InputStream is)
	{
		mXmlStream = is;
	}

	public String getStatusLine(){
		try
		{
			return mConnection.getResponseMessage();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	public InputStream getResponseStream()
	{
		return mRemoteStream;
	}
	public byte[] getResponseBytes(){
		return mResponseBytes.toByteArray();
	}

}
