package com.libraryclient.content.handlers;

import com.libraryclient.connection.Connector;
import com.libraryclient.content.Item;

public interface OnItemLoadListener<I extends Item> {

	public void onItemLoaded(Connector r, I i);

	public void onStartLoading(Connector r);

	public void onFinishLoading(Connector r);

}
