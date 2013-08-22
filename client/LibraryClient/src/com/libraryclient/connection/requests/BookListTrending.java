package com.libraryclient.connection.requests;

import com.libraryclient.connection.*;

public class BookListTrending extends Request
{
	public BookListTrending(String requestData)
	{
		this(Requests.BookListTopBorrow.REQUEST_CODE, requestData);
	}

	private BookListTrending(int requestCode, String requestData)
	{
		super(requestCode, requestData);
	}

	public String getTargetResponseTag()
	{
		return Requests.BookListTopBorrow.RESPONSE_TAG;
	}
}