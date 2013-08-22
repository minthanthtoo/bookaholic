package com.testbuild.cards;

import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.net.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.fima.cardsui.objects.*;
import com.testbuild.*;
import com.testbuild.fragments.*;

public class BookCard extends Card implements OnClickListener {

	private static boolean DEBUG = true;

	private static String TAG = "BookCard";

	private View viewRoot;

	private int mBookId;

	private String mBookName;

	private int mAuthorId;

	private String mAuthorName;

	private Uri mImgURI;

	private String mDataCreatedDateTime;

	private float mRatingGot;

	private float mRatingGiven;

	private int mRatingNum;

	private int mBorrowNum;

	private int mLikeNum;

	private int mReviewNum;

	public BookCard(int mBookId, String mBookName, int mAuthorId,
			String mAuthorName, Uri mImgURI, String mDataCreatedDateTime,
			float mRatingGot, float mRatingGiven, int mRatingNum,
			int mBorrowNum, int mLikeNum, int mReviewNum) {
		this.mBookId = mBookId;
		this.mBookName = mBookName;
		this.mAuthorId = mAuthorId;
		this.mAuthorName = mAuthorName;
		this.mImgURI = mImgURI;
		this.mDataCreatedDateTime = mDataCreatedDateTime;
		this.mRatingGot = mRatingGot;
		this.mRatingGiven = mRatingGiven;
		this.mRatingNum = mRatingNum;
		this.mBorrowNum = mBorrowNum;
		this.mLikeNum = mLikeNum;
		this.mReviewNum = mReviewNum;
	}

	public BookCard(String imgURI, String bookName, String authorName) {

		mImgURI = Uri.parse(imgURI);
		mBookName = bookName;
		mAuthorName = authorName;
	}

	@Override
	public View getCardContent(Context context) {
		viewRoot = LayoutInflater.from(context).inflate(
				R.layout.card_book_general, null);

		updateUI();
		super.setOnClickListener(this);
		return viewRoot;
	}

	private void updateUI() {
		TextView tvBookName = (TextView) viewRoot
				.findViewById(R.id.tv_bookName);
		tvBookName.setText(mBookName);
		tvBookName.setSelected(true);
		TextView tvAuthorName = (TextView) viewRoot
				.findViewById(R.id.tv_authorName);
		tvAuthorName.setText(mAuthorName);
		tvAuthorName.setSelected(true);
		BitmapDrawable bit = new BitmapDrawable(
				BitmapFactory.decodeFile(mImgURI.toString()));
		if (DEBUG)
			Log.i(TAG,
					"BookCard:imgURI="
							+ mImgURI.toString()
							+ " bit="
							+ bit.getBounds()
							+ " iv="
							+ ((ImageView) viewRoot.findViewById(R.id.iv_book))
									.toString());
		((ImageView) viewRoot.findViewById(R.id.iv_book))
				.setBackgroundDrawable(bit);
		RatingBar rb = (RatingBar) viewRoot.findViewById(R.id.rb_book);
		rb.setRating(mRatingGot);
		rb.setNumStars((int) mRatingGiven);
	}

	@Override
	public void onClick(View v) {
		if (DEBUG) {
			Log.d(TAG, "BookCard.onClick()");
		}
		String query = new com.testbuild.utils.URI.Query()
				.put(BookDetail.QueryNames.BOOK_ID, mBookId + "")
				.put(BookDetail.QueryNames.BOOK_NAME, mBookName)
				.put(BookDetail.QueryNames.AUTHOR_ID, mAuthorId + "")
				.put(BookDetail.QueryNames.AUTHOR_NAME, mAuthorName)
				.put(BookDetail.QueryNames.CREATED_DATETIME,
						mDataCreatedDateTime)
				.put(BookDetail.QueryNames.IMAGE_URI, mImgURI.toString())
				.put(BookDetail.QueryNames.RATING_VALUE, mRatingGot + "")
				.put(BookDetail.QueryNames.RATING_BAR_STAR_NUM,
						mRatingGiven + "")
				.put(BookDetail.QueryNames.RATING_NUM, mRatingNum + "")
				.put(BookDetail.QueryNames.BORROW_NUM, mBorrowNum + "")
				.put(BookDetail.QueryNames.LIKES_NUM, mLikeNum + "")
				.put(BookDetail.QueryNames.REVIEWS_NUM, mReviewNum + "").build();

		MainActivityII.updateContent(BookDetail.URI.buildUpon()
				.encodedPath("query").encodedQuery(query).build());
		if (DEBUG)
			Log.d(TAG, "BookDetail Uri query:" + query);
	}

}
