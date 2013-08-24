package com.libraryclient.content.handlers;

import com.libraryclient.connection.Connector;
import com.libraryclient.content.ContentHandler;
import com.libraryclient.content.Item;

abstract public class AbstrItemHandler<E extends Item> extends
		ContentHandler<E> {
	@Override
	public void onLoading(Connector r, E i) {
		// TODO: Implement this method
	}

	@Override
	public void onReload(Connector r) {
		// TODO: Implement this method
	}

	@Override
	public void onStopLoading(Connector r) {
		// TODO: Implement this method
	}

	@Override
	public void onResumeLoading(Connector r) {
		// TODO: Implement this method
	}

	public int getItemCount() {
		return 1;
	}

	@Override
	public abstract void onItemLoaded(Connector r, E i);

	@Override
	public abstract void onStartLoading(Connector r);

	@Override
	public abstract void onFinishLoading(Connector r);
}
