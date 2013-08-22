package com.testbuild.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.fima.cardsui.views.CardUI;
import com.libraryclient.connection.ConnectDataBuilder;
import com.libraryclient.connection.Connector;
import com.libraryclient.connection.Request;
import com.libraryclient.connection.requests.BookListNew;
import com.libraryclient.content.ContentHandler;
import com.libraryclient.content.handlers.ConnectCodeSpecificItemHandler;
import com.libraryclientforandroid.BaseApi;
import com.testbuild.R;
import com.testbuild.cards.BookCard;

public class NewBooks extends Fragment implements OnClickListener
{

	public static String TAG = NewBooks.class.getSimpleName();

	public static final String SCHEME = "action";
	public static final String AUTHORITY = "library.books.new";
	public static final Uri URI = new Uri.Builder().scheme(SCHEME)
	.authority(AUTHORITY).build();

	private static final boolean DEBUG = true;

	private CardUI main;

	public interface OnSettingsChangedListener
	{
		void onSettingChanged(int prefId, int value);
	}

	private View viewRoot;
	private OnSettingsChangedListener mSettingsChangedListener;

	private Bundle mBundle;

	public void setOnSettingsChangedListener(OnSettingsChangedListener listener)
	{
		mSettingsChangedListener = listener;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		if (savedInstanceState != null)
		{
		}
		if (DEBUG)
			Log.v(TAG, "SYATE : Frag_newBooks : onActivityCreated : bundle = "
				  + savedInstanceState);

		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle outState)
	{
		super.setRetainInstance(true);
		if (mBundle == null)

			if (outState != null)
				mBundle = outState;
			else
				mBundle = getArguments();

		if (DEBUG)
			Log.v(TAG, "STATE : Frag_newBooks : onCreate : bundle = "
				  + outState);

		request();
		super.onCreate(outState);
	}

	public void onViewStateRestored(Bundle b)
	{
		main.refresh();
		if (DEBUG)
			Log.v(TAG, "STATE : Frag_newBooks : onViewStateRestore : bundle = "
				  + b);
		super.onViewStateRestored(b);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		if (DEBUG)
			Log.v(TAG, "STATE : Frag_newBooks : onCreateView : bundle = "
				  + savedInstanceState);
		viewRoot = inflater.inflate(R.layout.frag_new_books, container, false);
		/***** CurdUI implementations *****/
		main = (CardUI) viewRoot.findViewById(R.id.main_cardUI);
		BookCard card1;
		for (int i = 1; i < 10; i++)
		{
			card1 = new BookCard(i, "book " + i, i, "author",
								 Uri.parse("/sdcard/img"), "12/06/1999", (float) (5.0 % i),
								 (float) 5.0, i, i * 2, i * 3, i * 4);
			main.addCard(card1);
			main.addCard(card1);
		}
		main.refresh();
		if (DEBUG)
			Log.d(TAG, "Inflate Layout: view result=" + viewRoot + " : CardUI"
				  + main);
		return viewRoot;
	}

	private void request()
	{
		if (mBundle != null)
		{
			ContentHandler h = new ConnectCodeSpecificItemHandler(
				new com.libraryclient.content.handlers.OnItemLoadListener() {
					public void onItemLoaded(Connector c,
											 com.libraryclient.content.Item i)
					{
						// update UI when an item has been loaded
					}

					public void onStartLoading(Connector c)
					{
						// triggers 'progress GUI objects'
					}

					public void onFinishLoading(Connector c)
					{
						// finish 'progress GUI objects'
					}

				});

			// triggers connection
			Request c = new BookListNew(
				new ConnectDataBuilder().put(
					String.valueOf(0),
					DateFormat.format("yyyy MM dd hh:mm:ss", System.currentTimeMillis()).toString()
				).build()
			);

			BaseApi.request(c, h);
		}
	}

	@Override
	public void onClick(final View v)
	{
		final int id = v.getId();

		final int titleId;
		final int valueId;
		final int itemsArrayId;
		final int valuesArrayId;

		switch (id)
		{
			case 0:
				break;
			default:
				return;
		}

		final Fragment prev = getFragmentManager().findFragmentByTag(
			BrowseBooks.TAG);
		if (prev != null)
		{
			getFragmentManager().beginTransaction().remove(prev).commit();
		}

	}

	private void saveStringPrefState(Bundle outState, int prefValue)
	{
		final TextView viewValue = (TextView) viewRoot.findViewById(prefValue);
		outState.putString(String.valueOf(prefValue), viewValue.getText()
						   .toString());
	}

	private void saveBooleanPrefState(Bundle outState, int prefValue)
	{
		final CompoundButton viewValue = (CompoundButton) viewRoot
			.findViewById(prefValue);
		outState.putBoolean(String.valueOf(prefValue), viewValue.isChecked());
	}

	private String restoreStringPrefState(Bundle savedInstanceState,
										  int prefValue)
	{
		final String value = savedInstanceState.getString(String
														  .valueOf(prefValue));
		final TextView viewValue = (TextView) viewRoot.findViewById(prefValue);
		viewValue.setText(value);
		return value;
	}

	private boolean restoreBooleanPrefState(Bundle savedInstanceState,
											int prefValue)
	{
		final boolean value = savedInstanceState.getBoolean(String
															.valueOf(prefValue));
		final CompoundButton viewValue = (CompoundButton) viewRoot
			.findViewById(prefValue);
		viewValue.setChecked(value);
		return value;
	}
}
