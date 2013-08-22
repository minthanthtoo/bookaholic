package com.libraryclient.content.handlers;

import com.libraryclient.connection.*;
import com.libraryclient.connection.posts.*;
import com.libraryclient.connection.requests.*;
import com.libraryclient.content.*;
import java.lang.reflect.*;
import java.util.*;

public class ConnectCodeSpecificItemHandler<E extends Item> extends AbstrItemHandler<E>
{
	ArrayList<Item> mItems = new ArrayList<Item>();

	OnItemLoadListener<E> mListener;

	private String TAG = ConnectCodeSpecificItemHandler.class.getSimpleName();

	private boolean DEBUG = true;

	public ConnectCodeSpecificItemHandler(OnItemLoadListener<E> mListener)
	{
		this.mListener = mListener;
	}

	public void onItemLoaded(final Connector c, E i)
	{
		if (DEBUG)
			System.out.println(TAG + ":\t\"" + c.getTargetResponseTag()
							   + "\" = \"" + i.getItemName() + "\"");
//		try
//		{
//			E item=new ClassCreator<E>().createInstance(i);
//			System.out.println(item);
//			item=new Foo<E>().instance;
//			item=i;
//			i=item;
//		}
//		catch (InstantiationException e)
//		{
//			e.printStackTrace();
//		}
//		catch (IllegalAccessException e)
//		{
//			e.printStackTrace();
//		}
//		i.mapValues();
		switch (c.getConnectCode())
		{
			case 1:
				if (c.getTargetResponseTag().equals(i.getItemName()))
				{
					mItems.add(i);
					mListener.onItemLoaded(c, i);
				}
				// break;
			default:
				if (c.getTargetResponseTag().equals(
						Requests.BasicRequest.RESPONSE_TAG)
					|| c.getTargetResponseTag().equals(
						Posts.BasicPost.RESPONSE_TAG))
				{
					mItems.add(i);
					mListener.onItemLoaded(c, i);
				}
		}
	}

	public void onStartLoading(Connector r)
	{
		mListener.onStartLoading(r);
	}

	public void onFinishLoading(Connector r)
	{
		mListener.onFinishLoading(r);
	}

	public Item getLoadedItem(int index)
	{
		return mItems.get(index);
	}

	public int getItemCount()
	{
		return mItems.size();
	}
	
	public final class ClassCreator<E>{
		public E createInstance(E c) throws InstantiationException, IllegalAccessException{
			return (E) c.getClass().newInstance();
		}
	}
	public class Foo<T>{
		T instance;
		public Foo() throws InstantiationException, IllegalAccessException{
			instance = (T) ((Class)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]).newInstance();
		}
	}
}
