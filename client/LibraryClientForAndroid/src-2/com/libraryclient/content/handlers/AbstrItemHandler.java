package com.libraryclient.content.handlers;
import com.libraryclient.connection.*;
import com.libraryclient.content.*;

abstract public class AbstrItemHandler<E extends Item> extends ContentHandler<E>
{
	public void onLoading(Connector r, E i)
	{
		// TODO: Implement this method
	}

	public void onReload(Connector r)
	{
		// TODO: Implement this method
	}

	public void onStopLoading(Connector r)
	{
		// TODO: Implement this method
	}

	public void onResumeLoading(Connector r)
	{
		// TODO: Implement this method
	}


	public int getItemCount()
	{
		return 1;
	}

	public abstract void onItemLoaded(Connector r, E i);

	public abstract void onStartLoading(Connector r);

	public abstract void onFinishLoading(Connector r);
}
