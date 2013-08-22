package com.libraryclient.content.handlers;
import com.libraryclient.connection.*;
import com.libraryclient.content.*;

public class GeneralItemHandler extends AbstrItemHandler
{

	public int getItemCount()
	{
		return 1;
	}
	
	private Item mItem = null;

	public void onItemLoaded(Connector r, Item i)
	{
		mItem = i;
		System.out.println("[BasicItemHandler] onItemLoaded()");
	}

	public void onStartLoading(Connector r)
	{
		System.out.println("[BasicItemHandler] onStartLoading()");
	}

	public void onFinishLoading(Connector r)
	{
		System.out.println("[BasicItemHandler] onFinishLoading()");
		if (mItem == null)
			System.out.println("[BasicItemHandler] No item is loaded!");
		else System.out.println("Item : "+mItem);
	}
	
	public Item getLoadedItem(int index)
	{
		return mItem;
	}
}