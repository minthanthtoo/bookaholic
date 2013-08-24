package com.libraryclient.content.handlers;

import com.libraryclient.connection.Connector;
import com.libraryclient.content.Item;

public class GeneralItemHandler extends AbstrItemHandler {

	@Override
	public int getItemCount() {
		return 1;
	}

	private Item mItem = null;

	@Override
	public void onItemLoaded(Connector r, Item i) {
		mItem = i;
		System.out.println("[BasicItemHandler] onItemLoaded()");
	}

	@Override
	public void onStartLoading(Connector r) {
		System.out.println("[BasicItemHandler] onStartLoading()");
	}

	@Override
	public void onFinishLoading(Connector r) {
		System.out.println("[BasicItemHandler] onFinishLoading()");
		if (mItem == null)
			System.out.println("[BasicItemHandler] No item is loaded!");
		else
			System.out.println("Item : " + mItem);
	}

	public Item getLoadedItem(int index) {
		return mItem;
	}
}