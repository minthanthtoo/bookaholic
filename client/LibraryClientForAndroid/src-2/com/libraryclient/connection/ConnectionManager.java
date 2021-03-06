package com.libraryclient.connection;

import com.libraryclient.config.*;
import java.io.*;
import java.util.*;
import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.conn.*;
import org.apache.http.conn.params.*;
import org.apache.http.conn.scheme.*;
import org.apache.http.conn.ssl.*;
import org.apache.http.entity.mime.*;
import org.apache.http.entity.mime.content.*;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.tsccm.*;
import org.apache.http.params.*;
import org.apache.http.protocol.*;
import org.apache.http.util.EntityUtils;

public class ConnectionManager
{
	private static HttpClient mHttpConnectClient;

	private static boolean DEBUG = true;
	/** A private Constructor prevents instantiation */
	private ConnectionManager()
	{

	}
	public static synchronized HttpClient getHttpClient()
	{
		if (mHttpConnectClient == null)
		{
			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.DEFAULT_CONTENT_CHARSET);
//			HttpProtocolParams.setUseExpectContinue(params, true);
			HttpProtocolParams.setUserAgent(params, "Mozilla/5.0 (Linux; U; Android 2.2.1; en-us; Nexus One Build/FRG83) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
			ConnManagerParams.setTimeout(params, 1000);
			HttpConnectionParams.setConnectionTimeout(params, 5000);
			HttpConnectionParams.setSoTimeout(params, 10000);

			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

			ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);
			mHttpConnectClient = new DefaultHttpClient(conMgr, params);

		}
		return mHttpConnectClient;
	}
	
	public static HttpResponse post(Post post) throws IOException{

		MultipartEntity reqEntity = new MultipartEntity();
		HttpPost httpost = new HttpPost(post.getURI());
		
		//
		reqEntity.addPart(Connection.CONNECT_USER_HEADER ,new StringBody(Connection.USERNAME ));
		reqEntity.addPart(Connection.CONNECT_PASSWORD_HEADER ,new StringBody(Connection.PASSWORD ));

		reqEntity.addPart(Connection.POST_CODE_HEADER ,new StringBody(Integer.toString(post.getConnectCode())));
		reqEntity.addPart(Connection.POST_DATA_HEADER ,new StringBody(post.getConnectData()));
		
		Iterator<Map.Entry<String,ContentBody>> it = post.partsIterator();
		Map.Entry<String,ContentBody> i;
		while(it.hasNext()){
			i=it.next();
			reqEntity.addPart(i.getKey(),i.getValue());
			if(DEBUG)
			System.out.println(i.getKey()+i.getValue());
		}
		
		httpost.setEntity(reqEntity);
		
		return ConnectionManager.getHttpClient().execute(httpost);/*,new ResponseHandler<HttpResponse>() {

			@Override
			public HttpResponse handleResponse(HttpResponse response)
					throws ClientProtocolException, IOException {
		        StatusLine statusLine = response.getStatusLine();
		        HttpEntity entity = response.getEntity();
		        if (statusLine.getStatusCode() >= 300) {
		            entity.consumeContent();
		            throw new HttpResponseException(statusLine.getStatusCode(),
		                    statusLine.getReasonPhrase());
		        }
		        return entity == null ? null : response;//EntityUtils.toString(entity);
			}
			
		});*/
	}
	
	public Object clone() throws CloneNotSupportedException
	{
		throw new CloneNotSupportedException();
	}
}