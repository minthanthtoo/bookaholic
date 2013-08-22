package com.libraryclientforandroid;

import java.io.File;
import java.io.IOException;
import java.util.Stack;

import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;

import com.libraryclient.Client;
import com.libraryclient.config.Connection;
import com.libraryclient.connection.BasicPost;
import com.libraryclient.connection.BasicRequest;
import com.libraryclient.connection.Connector;
import com.libraryclient.connection.Post;
import com.libraryclient.connection.Request;
import com.libraryclient.content.ContentHandler;
import com.libraryclient.content.DefaultContentHandler;
import com.libraryclient.content.Item;
import com.libraryclient.content.handlers.ConnectCodeSpecificItemHandler;

/**
 * Entry Api Class for using LibraryClient Library
 * <p>
 * <h2>Usage:</h2>
 * <p>
 * 
 * <pre>
 * // prepare connection client
 * Connection.USERNAME = "username";
 * Connection.PASSWORD = "password";
 * Connection.CONNECTION_WEBSITE = "http://www.site.com/path/to/backend";
 * BaseApi api = new BaseApi();
 * 
 * // create content handler
 * ConnectCodeSpecificItemHandler h = new ConnectCodeSpecificItemHandler(
 * 	new com.libraryclient.content.handlers.OnItemLoadListener() {
 * 		public void onItemLoaded(Connector c, final Item i) {
 * 			// update UI when an item has been loaded
 * 		}
 * 
 * 		public void onStartLoading(Connector c) {
 * 			// triggers 'progress GUI objects'
 * 		}
 * 
 * 		public void onFinishLoading(Connector c) {
 * 			// finish 'progress GUI objects'
 * 		}
 * 
 * 	});
 * 	
 * // triggers connection
 * Connector c = new BasicPost(1 //connect code
 * , "0=value1&&1=value2"); // request data
 * 
 * if (c instanceof Request) {
 * 	api.request((Request) c, h);
 * } else if (c instanceof Post) {
 * 	((Post) c).addPostFile("0", new File("/path/to/file"));
 * 	api.post((Post) c, h);
 * }
 * </pre>
 * 
 * @author Min Thant Htoo
 * 
 */
public class BaseApi {

	// ***** START: request *****//
	public static void request(int requestCode, String requestdata) {
		request(requestCode, requestdata, new DefaultContentHandler());
	}

	public static void request(int requestCode, String requestdata, ContentHandler h) {
		Request r = new BasicRequest(requestCode, requestdata);
		ConnectClient c = new ConnectClient();
		ConnectClient.setContentHandler(r, h);
		c.execute(r);
	}

	public static void request(Request r, ContentHandler h) {
		ConnectClient c = new ConnectClient();
		ConnectClient.setContentHandler(r, h);
		c.execute(r);
	}

	// ***** END: request *****//

	// ***** START: post *****//
	public static void post(int postCode, String postData) {
		post(postCode, postData, new DefaultContentHandler());
	}

	public static void post(int postCode, String postData, ContentHandler h) {
		BasicPost p = new BasicPost(postCode, postData);
		ConnectClient c = new ConnectClient();
		ConnectClient.setContentHandler(p, h);
		c.execute(p);
	}

	public static void post(Post p, ContentHandler h) {
		ConnectClient c = new ConnectClient();
		ConnectClient.setContentHandler(p, h);
		c.execute(p);
	}

	// ***** END: post *****//

	public static Connector getCurrentWorkingRequest() {
		return ConnectClient.getCurrentWorkingRequest();
	}

	public static int getCurrentWorkingRequestsCount() {
		return ConnectClient.getCurrentWorkingRequestsCount();
	}

	static class ConnectClient extends AsyncTask<Connector, Integer, Connector> {
		static Stack<Connector> mRequestStack = new Stack<Connector>();

		private static Client cl = new Client();

		public static void setContentHandler(Connector r,
				ContentHandler mHandler) {
			cl.setContentHandler(r.getConnectCode(), mHandler);
		}

		protected Connector doInBackground(Connector[] requests) {
			mRequestStack.add(requests[0]);
			try {
				cl.request(requests[0]);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				cl.loadContent(requests[0]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			// TODO: Implement this method
			return requests[0];
		}

		protected void onPostExecute(Request result) {
			mRequestStack.pop();
		}

		public static Connector getCurrentWorkingRequest() {
			return mRequestStack.peek();
		}

		public static int getCurrentWorkingRequestsCount() {
			return mRequestStack.size();
		}
	}
}