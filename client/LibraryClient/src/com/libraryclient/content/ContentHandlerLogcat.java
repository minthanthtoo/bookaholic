package com.libraryclient.content;

import android.util.Log;

import com.libraryclient.connection.Connector;

public class ContentHandlerLogcat extends ContentHandler {

	private final String TAG = "ContentHandlerLogcat";

	@Override
	public void onItemLoaded(Connector r, Item i) {
		Log.d(TAG, i.toString());

	}

	@Override
	public void onLoading(Connector r, Item i) {
	}

	@Override
	public void onReload(Connector r) {
		Log.d(TAG, "[Restart Content Handling]Request=" + r.toString());

	}

	@Override
	public void onStartLoading(Connector r) {
		Log.d(TAG, "[Start of Content Handling]Request=" + r.toString());
	}

	@Override
	public void onFinishLoading(Connector r) {
		Log.d(TAG, "[End of Content Handling]Request=" + r.toString());

	}

	@Override
	public void onStopLoading(Connector r) {
		Log.d(TAG, "[Stop Content Handling]Request=" + r.toString());
	}

	@Override
	public void onResumeLoading(Connector r) {
		// TODO Auto-generated method stub

	}
}
