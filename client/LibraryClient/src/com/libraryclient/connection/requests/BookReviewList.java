package com.libraryclient.connection.requests;

import com.libraryclient.connection.*;

public class BookReviewList extends Request
{
	public BookReviewList(String requestData)
	{
		this(Requests.BookListTopBorrow.REQUEST_CODE, requestData);
	}

	private BookReviewList(int requestCode, String requestData)
	{
		super(requestCode, requestData);
	}

	public String getTargetResponseTag()
	{
		return Requests.BookListTopBorrow.RESPONSE_TAG;
	}
}