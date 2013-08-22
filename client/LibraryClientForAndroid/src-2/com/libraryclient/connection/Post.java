package com.libraryclient.connection;

import com.libraryclient.config.*;
import com.libraryclient.content.*;
import java.io.*;
import java.net.*;
import java.util.*;
import org.apache.http.*;
import org.apache.http.entity.mime.content.*;
import org.apache.http.message.*;
import org.apache.http.util.*;

import com.libraryclient.content.ContentHandler;

public abstract class Post implements Connector
{
	private HttpURLConnection mConnection;
	private URI url;
	private int RequestCode;
	private String RequestData;
	private boolean isRequestFinished = false;
	private HeaderGroup mHeaders= new HeaderGroup();

	private FileOutputStream mLocalStream = null;
	private InputStream mRemoteStream = null;
	private Thread mResultLoader = null;
	
	private ResponseCodeHandler mResponseCodeHandler;
	/**
	 * {@link InputStream } for {@link XMLHandler}
	 * 
	 */
	private byte[] mResponseBytes = null;

	private Authentication mAuthentication;

	private HttpResponse response;

	public Post(int requestCode, String requestData)
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

	public void addAuthentication(Authentication auth){
		mAuthentication=auth;
		auth.authenticate(this);
	}
	public void addHeader(String name, String value)
	{
		mHeaders.addHeader(new BasicHeader(name, value));
	}

	public void connect() throws IOException
	{
		// TODO Auto-generated method stub
		response=ConnectionManager.post(this);
		// Examine the response status
		System.out.println(response.getStatusLine());
		onResponse(response.getStatusLine().getStatusCode());
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
	public void handleResponse(ContentHandler contentHandler) throws IOException
	{
		mResponseBytes = EntityUtils.toByteArray(response.getEntity());
//		System.out.println(new String(mResponseBytes,
//									  response.getEntity().
//									  getContentEncoding().
//									  getElements()[0].
//									  getValue()));
		response.getEntity().consumeContent();
		//
		// // Remote connection is now opened
		// try {
		// mRemoteStream = new BufferedInputStream(
		// mConnection.getInputStream(), Connection.BUFFER_SIZE);
		// } catch (IOException e1) {
		// System.err
		// .println("Error in creating InputStream from remote site");
		// e1.printStackTrace();
		// }
		//
		// // if caching is supported
		// if (Connection.CACHE_DIR != null && Connection.CACHE_DIR != "") {
		// // Create cache file
		// File cacheFile = new File(Connection.CACHE_DIR + "/"
		// + this.RequestCode + "." + System.currentTimeMillis()
		// + ".request.html");
		// if (!cacheFile.exists())
		// try {
		// cacheFile.createNewFile();
		// mLocalStream = new FileOutputStream(cacheFile);
		// } catch (IOException e) {
		// System.err.println("Error in creating cache file.\n");
		// e.printStackTrace();
		// }
		// // cacheFile.deleteOnExit(); // TODO comment out this
		//
		// // implements a thread for downloading results
		// mResultLoader = new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		// try {
		// byte[] in = new byte[Connection.BUFFER_SIZE];
		// while ((mRemoteStream.read(in)) != -1)
		// mLocalStream.write(in);
		// mRemoteStream.close();
		// mLocalStream.close();
		// } catch (IOException e) {
		// System.err.println("Error in downloading results!");
		// e.printStackTrace();
		// }
		// }
		//
		// });
		// // start downloading results
		// mResultLoader.start();
		// try {
		// this.setContentStream(new FileInputStream(cacheFile));
		// } catch (FileNotFoundException e) {
		// System.err.println("Error in reading cache file");
		// e.printStackTrace();
		// }
		//
		// } else {// if caching is not supported
		// this.setContentStream(mRemoteStream);
		// }
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

	public String getStatusLine(){
		return response.getStatusLine().toString();
	}
	public InputStream getResponseStream()
	{
		return new ByteArrayInputStream(mResponseBytes);
	}
	
	public byte[] getResponseBytes(){
		return mResponseBytes;
	}

	Map<String, ContentBody> mParts = new HashMap<String, ContentBody>();

	// @Override
	// public void post()
	// {
	// // TODO: Implement this method
	//
	// String result="";
	// HttpClient client = ConnectionManager.getHttpClient();
	// HttpPost post = new HttpPost();
	// try
	// {
	// FileBody bin = new FileBody(new File("file"));
	// StringBody comment = new StringBody("A binary file of some kind");
	//
	//
	// MultipartEntity reqEntity = new MultipartEntity();
	// reqEntity.addPart("userfile", bin);
	// reqEntity.addPart("rdata", comment);
	//
	// post.setEntity(reqEntity);
	//
	// result += "\nexecuting request " + post.getRequestLine();
	// HttpResponse response = client.execute(post);
	// HttpEntity resEntity = response.getEntity();
	//
	// result += "\n----------------------------------------";
	// result += "\n" + response.getStatusLine();
	// if (resEntity != null)
	// {
	// result += "Response content length: " + resEntity.getContentLength();
	// result += response.getEntity().getContent();
	// }
	// resEntity.consumeContent();
	// }
	// catch (IOException e){
	//
	// e.printStackTrace();
	// result +=e.getStackTrace();
	// }
	// finally
	// {
	// try
	// { client.getConnectionManager().shutdown(); }
	// catch (Exception ignore)
	// {
	// result += ignore.getMessage();
	// }
	// }
	// }

	public void addPostFile(String fileHandle, File file)
	{
		mParts.put(fileHandle, new FileBody(file));
	}

	public synchronized void addPostFile(File file)
	{
		mParts.put(Integer.toString(mParts.size() + 1), new FileBody(file));
	}

	public Iterator<Map.Entry<String, ContentBody>> partsIterator()
	{
		return mParts.entrySet().iterator();
	}
}
