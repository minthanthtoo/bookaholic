package com.bookaholic.contenthandlers;

import com.libraryclient.connection.Connector;
import com.libraryclient.content.DefaultContentHandler;
import com.libraryclient.content.Item;

public class GeneralContentHandler extends DefaultContentHandler {

	@Override
	public void onFinishLoading(Connector c) {
		// TODO Auto-generated method stub
		super.onFinishLoading(c);
	}

	@Override
	public void onItemLoaded(Connector c, Item i) {
		// TODO Auto-generated method stub
		super.onItemLoaded(c, i);
	}

	@Override
	public void onLoading(Connector c, Item i) {
		// TODO Auto-generated method stub
		super.onLoading(c, i);
	}

	@Override
	public void onReload(Connector c) {
		// TODO Auto-generated method stub
		super.onReload(c);
	}

	@Override
	public void onResumeLoading(Connector c) {
		// TODO Auto-generated method stub
		super.onResumeLoading(c);
	}

	@Override
	public void onStartLoading(Connector c) {
		// TODO Auto-generated method stub
		super.onStartLoading(c);
	}

	@Override
	public void onStopLoading(Connector c) {
		// TODO Auto-generated method stub
		super.onStopLoading(c);
	}
}
