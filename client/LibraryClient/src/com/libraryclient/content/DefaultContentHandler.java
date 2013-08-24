package com.libraryclient.content;

import com.libraryclient.connection.Connector;

public class DefaultContentHandler extends ContentHandler {

	@Override
	public void onItemLoaded(Connector r, Item i) {
		System.out.print(i.toString());

	}

	@Override
	public void onLoading(Connector r, Item i) {
	}

	@Override
	public void onReload(Connector r) {
		System.out.println("[Restart Content Handling]Request=" + r.toString());

	}

	@Override
	public void onStartLoading(Connector r) {
		System.out
				.println("[Start of Content Handling]Request=" + r.toString());
	}

	@Override
	public void onFinishLoading(Connector r) {
		System.out.println("[End of Content Handling]Request=" + r.toString());

	}

	@Override
	public void onStopLoading(Connector r) {
		System.out.println("[Stop Content Handling]Request=" + r.toString());
	}

	@Override
	public void onResumeLoading(Connector r) {
		// TODO Auto-generated method stub

	}

}