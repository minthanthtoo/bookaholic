package com.testbuild.fragments;

import android.graphics.*;
import android.graphics.drawable.*;
import android.net.*;
import android.os.*;
import android.support.v4.app.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import com.testbuild.*;

public class BookDetail extends Fragment
{

	public static String TAG = BookDetail.class.getSimpleName();

	public static final String SCHEME="action";
	public static final String AUTHORITY="library.books.bookdetail";
	public static final Uri URI= new Uri.Builder()
	.scheme(SCHEME)
	.authority(AUTHORITY).build();

	private static final boolean DEBUG = true;

	public void setBundle(Bundle myBundle)
	{
		this.mBundle = myBundle;
	}

	public Bundle getBundle()
	{
		return mBundle;
	}

	public interface OnSettingsChangedListener
	{
		void onSettingChanged(int prefId, int value);
	}

	private View viewRoot;
	private OnSettingsChangedListener mSettingsChangedListener;

	public void setOnSettingsChangedListener(OnSettingsChangedListener listener)
	{
		mSettingsChangedListener = listener;
	}

	private Bundle mBundle;

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		if (savedInstanceState != null)
		{
		}
		if (DEBUG)
			Log.v(TAG, "STATE : Frag_bookDetail: onActivityCreated : bundle = " + savedInstanceState);

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
			Log.v(TAG, "STATE : Frag_BookDetail: onCreate : bundle = " + mBundle);

		super.onCreate(outState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		if (DEBUG)
			Log.v(TAG, "STATE : Frag_BookDetail : onCreateView : bundle = " + savedInstanceState);
		viewRoot = inflater.inflate(R.layout.frag_book_detail, container, false);

		updateUI();
		if (DEBUG)
			Log.d(TAG,
				  "Inflate Layout: view result=" + viewRoot);
		return viewRoot;
	}
	public void onViewStateRestored(Bundle inState)
	{
		if (DEBUG)
			Log.v(TAG, "STATE : Frag_BookDetail : onViewStateRestore : bundle = " + inState);
		updateUI();
		super.onViewStateRestored(inState);
	}

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		outState = mBundle;
		if (DEBUG)
			Log.d(TAG, "STATE : Frag_bookDetail onSaveInstanceState out="+outState);
		super.onSaveInstanceState(outState);
	}
	
	public void updateUI()
	{
		if (mBundle != null)
		{
			//restoreStringPrefState( mBundle, UriQueryNames.BOOK_ID,R.id.tv_bookName);
			restoreTextFieldValues(mBundle, QueryNames.BOOK_NAME, R.id.tv_bookName);
			//	restoreStringPrefState(mBundle, QueryNames.CREATED_DATETIME, R.id.tv_bookCreatedDateTime);
			BitmapDrawable bit=new BitmapDrawable(BitmapFactory.decodeFile(mBundle.getString(QueryNames.IMAGE_URI)));
			((ImageView)viewRoot.findViewById(R.id.iv_book)).setBackgroundDrawable(bit);
			//restoreStringPrefState( mBundle,UriQueryNames.AUTHOR_ID
			restoreTextFieldValues(mBundle, QueryNames.AUTHOR_NAME, R.id.tv_authorName);
			// restoreStringPrefState(mBundle, QueryNames.RATING_NUM, R.id.tv_ratingNum);
			RatingBar rb = (RatingBar) viewRoot
					.findViewById(R.id.rb_book_small);
			RatingBar rb2 = (RatingBar) viewRoot
					.findViewById(R.id.rb_book_small_2);
			float ratingValue = Float.valueOf(mBundle
					.getString(QueryNames.RATING_VALUE));
			int numStars = (int) (float) Float.valueOf(mBundle
					.getString(QueryNames.RATING_BAR_STAR_NUM));
			rb.setRating(ratingValue);
			rb.setNumStars(numStars);
			rb2.setRating(ratingValue);
			rb2.setNumStars(numStars);
			//	restoreStringPrefState(mBundle, QueryNames.LIKES_NUM, R.id.tv_likesNum);
			//	restoreStringPrefState(mBundle, QueryNames.BORROW_NUM, R.id.tv_borrowNum);
			//	restoreStringPrefState(mBundle, QueryNames.REVIEWS_NUM, R.id.tv_reviewsNum);
			//	restoreStringPrefState(mBundle, QueryNames.BOOK_STATUS, R.id.tv_bookStatus);
		}
	}
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

		final Fragment prev = getFragmentManager().findFragmentByTag(BrowseBooks.TAG);
		if (prev != null)
		{
			getFragmentManager().beginTransaction().remove(prev).commit();
		}

	}

	private void saveStringPrefState(Bundle outState, int prefValue)
	{
		final TextView viewValue = (TextView) viewRoot.findViewById(prefValue);
		outState.putString(String.valueOf(prefValue), viewValue.getText().toString());
	}

	private void saveBooleanPrefState(Bundle outState, int prefValue)
	{
		final CompoundButton viewValue = (CompoundButton) viewRoot.findViewById(prefValue);
		outState.putBoolean(String.valueOf(prefValue), viewValue.isChecked());
	}

	private String restoreStringPrefState(Bundle savedInstanceState, int prefValue)
	{
		final String value = savedInstanceState.getString(String.valueOf(prefValue));
		final TextView viewValue = (TextView) viewRoot.findViewById(prefValue);
		viewValue.setText(value);
		return value;
	}
	private String restoreTextFieldValues(Bundle state, String queryName, int viewID)
	{
		final String value = state.getString(queryName);
		final TextView viewValue = (TextView) viewRoot.findViewById(viewID);
		viewValue.setText(value);
		return value;
	}

	private boolean restoreBooleanPrefState(Bundle savedInstanceState, int prefValue)
	{
		final boolean value = savedInstanceState.getBoolean(String.valueOf(prefValue));
		final CompoundButton viewValue = (CompoundButton) viewRoot.findViewById(prefValue);
		viewValue.setChecked(value);
		return value;
	}
	public static class QueryNames
	{
		public static final String BOOK_ID="bookDetail:bookId";
		public static final String BOOK_NAME="bookDetail:bookName";
		public static final String CREATED_DATETIME="bookDetail:createdDateTime";
		public static final String IMAGE_URI="bookDetail:imageUri";
		public static final String AUTHOR_ID="bookDetail:authorId";
		public static final String AUTHOR_NAME="bookDetail:authorName";
		public static final String RATING_NUM="bookDetail:ratingNum";
		public static final String RATING_VALUE="bookDetail:ratingGot";
		public static final String RATING_BAR_STAR_NUM="bookDetail:ratingGiven";
		public static final String LIKES_NUM="bookDetail:likesNum";
		public static final String BORROW_NUM="bookDetail:borrowNum";
		public static final String REVIEWS_NUM="bookDetail:reviewsNum";
		public static final String BOOK_STATUS="bookDetail:bookStatus";

	}
}

