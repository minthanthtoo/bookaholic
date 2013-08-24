package com.libraryclient.content.handlers;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

import com.libraryclient.connection.Connector;
import com.libraryclient.connection.posts.Posts;
import com.libraryclient.connection.requests.Requests;
import com.libraryclient.content.Item;

public class ConnectCodeSpecificItemHandler<TargetItem extends Item> extends
		AbstrItemHandler<TargetItem> {
	ArrayList<Item> mItems = new ArrayList<Item>();

	OnItemLoadListener<TargetItem> mListener;

	private String TAG = ConnectCodeSpecificItemHandler.class.getSimpleName();

	private boolean DEBUG = true;

	public ConnectCodeSpecificItemHandler(OnItemLoadListener<TargetItem> mListener) {
		this.mListener = mListener;
	}

	@Override
	public void onItemLoaded(final Connector c, TargetItem i) {
		if (DEBUG)
			System.out.println(TAG + ":\t\"" + c.getTargetResponseTag()
					+ "\" = \"" + i.getItemName() + "\"");
		// try
		// {
		// E item=new ClassCreator<E>().createInstance(i);
		// System.out.println(item);
		// item=new Foo<E>().instance;
		// item=i;
		// i=item;
		// }
		// catch (InstantiationException e)
		// {
		// e.printStackTrace();
		// }
		// catch (IllegalAccessException e)
		// {
		// e.printStackTrace();
		// }
		// i.mapValues();
		switch (c.getConnectCode()) {
		case 1:
			if (c.getTargetResponseTag().equals(i.getItemName())) {
				mItems.add(i);
				mListener.onItemLoaded(c, i);
			}
			// break;
		default:
			if (c.getTargetResponseTag().equals(
					Requests.BasicRequest.RESPONSE_TAG)
					|| c.getTargetResponseTag().equals(
							Posts.BasicPost.RESPONSE_TAG)) {
				mItems.add(i);
				mListener.onItemLoaded(c, i);
			}
		}
	}

	@Override
	public void onStartLoading(Connector r) {
		mListener.onStartLoading(r);
	}

	@Override
	public void onFinishLoading(Connector r) {
		mListener.onFinishLoading(r);
	}

	public Item getLoadedItem(int index) {
		return mItems.get(index);
	}

	@Override
	public int getItemCount() {
		return mItems.size();
	}

	public final class ClassCreator<E> {
		public E createInstance(E c) throws InstantiationException,
				IllegalAccessException {
			return (E) c.getClass().newInstance();
		}
	}

	public class Foo<T> {
		T instance;

		public Foo() throws InstantiationException, IllegalAccessException {
			instance = (T) ((Class) ((ParameterizedType) this.getClass()
					.getGenericSuperclass()).getActualTypeArguments()[0])
					.newInstance();
		}
	}
}
