package com.libraryclient.connection.requests;

import com.libraryclient.connection.*;

public class BookTypesList extends Request
{
	public BookTypesList(String requestData)
	{
		this(Requests.BookListTopBorrow.REQUEST_CODE, requestData);
	}

	private BookTypesList(int requestCode, String requestData)
	{
		super(requestCode, requestData);
	}

	public String getTargetResponseTag()
	{
		return Requests.BookListTopBorrow.RESPONSE_TAG;
	}
}