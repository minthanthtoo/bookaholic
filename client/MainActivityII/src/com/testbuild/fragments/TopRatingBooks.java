package com.testbuild.fragments;

import android.net.*;
import android.os.*;
import android.support.v4.app.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.fima.cardsui.views.*;
import com.testbuild.*;
import com.testbuild.cards.*;

public class TopRatingBooks extends Fragment implements OnClickListener
{

public static String TAG = NewBooks.class.getSimpleName();

public static final String SCHEME="action";
public static final String AUTHORITY="library.books.toprating";
public static final Uri URI= new Uri.Builder()
.scheme(SCHEME)
.authority(AUTHORITY).build();

private static final boolean DEBUG = true;

private CardUI main;

public interface OnSettingsChangedListener {
    void onSettingChanged(int prefId, int value);
	}

	private View viewRoot;
	private OnSettingsChangedListener mSettingsChangedListener;

	public void setOnSettingsChangedListener(OnSettingsChangedListener listener) {
		mSettingsChangedListener = listener;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if(DEBUG)
			Log.v(TAG,"SYATE : Frag_topRatingBooks : onSaveInstanceState : bundle = "+outState);
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
		}
		if(DEBUG)
			Log.v(TAG,"SYATE : Frag_topRatingBooks : onActivityCreated : bundle = "+savedInstanceState);

		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle outState) {
		super.setRetainInstance(true);
		if(DEBUG)
			Log.v(TAG,"STATE : Frag_topRatingBooks : onCreate : bundle = "+outState);
		super.onCreate(outState);
	}
	public void onViewStateRestored(Bundle b){
		main.refresh();
		if(DEBUG)
			Log.v(TAG,"STATE : Frag_topRatingBooks : onViewStateRestore : bundle = "+b);
		super.onViewStateRestored(b);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		if(DEBUG)
			Log.v(TAG,"STATE : Frag_topRatingBooks : onCreateView : bundle = "+savedInstanceState);
		viewRoot= inflater.inflate(R.layout.frag_new_books, container, false);
		/***** CurdUI implementations *****/
		main = (CardUI) viewRoot.findViewById(R.id.main_cardUI);
		BookCard card1;
		for(int i=0;i<10;i++){
			card1 = new BookCard("","Toprating book boook","author.....aauuuuuthhhhh");
			main.addCard(card1);
			main.addCard(card1);
		}
		main.refresh();
		if(DEBUG)
			Log.d(TAG,
				  "Inflate Layout: view result="+viewRoot+" : CardUI"+main);
		return viewRoot;
	}

	@Override
	public void onClick(final View v) {
		final int id = v.getId();

		final int titleId;
		final int valueId;
		final int itemsArrayId;
		final int valuesArrayId;

		switch (id) {
			case 0:
				break;
			default:
				return;
		}

		final Fragment prev = getFragmentManager().findFragmentByTag(BrowseBooks.TAG);
		if (prev != null) {
			getFragmentManager().beginTransaction().remove(prev).commit();
		}

	}

	private void saveStringPrefState(Bundle outState, int prefValue) {
		final TextView viewValue = (TextView) viewRoot.findViewById(prefValue);
		outState.putString(String.valueOf(prefValue), viewValue.getText().toString());
	}

	private void saveBooleanPrefState(Bundle outState, int prefValue) {
		final CompoundButton viewValue = (CompoundButton) viewRoot.findViewById(prefValue);
		outState.putBoolean(String.valueOf(prefValue), viewValue.isChecked());
	}

	private String restoreStringPrefState(Bundle savedInstanceState, int prefValue) {
		final String value = savedInstanceState.getString(String.valueOf(prefValue));
		final TextView viewValue = (TextView) viewRoot.findViewById(prefValue);
		viewValue.setText(value);
		return value;
	}

	private boolean restoreBooleanPrefState(Bundle savedInstanceState, int prefValue) {
		final boolean value = savedInstanceState.getBoolean(String.valueOf(prefValue));
		final CompoundButton viewValue = (CompoundButton) viewRoot.findViewById(prefValue);
		viewValue.setChecked(value);
		return value;
	}
}

