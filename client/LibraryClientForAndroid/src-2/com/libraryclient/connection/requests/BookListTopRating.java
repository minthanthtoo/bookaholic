package com.libraryclient.connection.requests;

import com.libraryclient.connection.*;

public class BookListTopRating extends Request
{
	public BookListTopRating(String requestData)
	{
		this(Requests.BookListTopBorrow.REQUEST_CODE, requestData);
	}

	private BookListTopRating(int requestCode, String requestData)
	{
		super(requestCode, requestData);
	}

	public String getTargetResponseTag()
	{
		return Requests.BookListTopBorrow.RESPONSE_TAG;
	}
}