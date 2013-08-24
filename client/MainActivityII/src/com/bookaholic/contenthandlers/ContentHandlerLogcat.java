package com.bookaholic.contenthandlers;

import android.util.Log;

import com.libraryclient.connection.Connector;
import com.libraryclient.content.ContentHandler;
import com.libraryclient.content.Item;

public class ContentHandlerLogcat extends ContentHandler {

	private final String TAG = "ContentHandlerLogcat";

	@Override
	public void onItemLoaded(Connector c, Item i) {
		Log.d(TAG, i.toString());

	}

	@Override
	public void onLoading(Connector c, Item i) {
	}

	@Override
	public void onReload(Connector c) {
		Log.d(TAG, "[Restart Content Handling]Request=" + c.toString());

	}

	@Override
	public void onStartLoading(Connector c) {
		Log.d(TAG, "[Start of Content Handling]Request=" + c.toString());
	}

	@Override
	public void onFinishLoading(Connector c) {

		Log.d(TAG, "[End of Content Handling]Request=" + c.toString());

	}

	@Override
	public void onStopLoading(Connector c) {
		Log.d(TAG, "[Stop Content Handling]Request=" + c.toString());
	}

	@Override
	public void onResumeLoading(Connector c) {
		// TODO Auto-generated method stub

	}
}
