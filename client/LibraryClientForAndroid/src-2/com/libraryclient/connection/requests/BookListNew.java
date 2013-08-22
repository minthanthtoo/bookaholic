package com.libraryclient.connection.requests;

import com.libraryclient.connection.*;

public class BookListNew extends Request
{
	public BookListNew(String requestData)
	{
		this(Requests.AuthorList.REQUEST_CODE, requestData);
	}

	private BookListNew(int requestCode, String requestData)
	{
		super(requestCode, requestData);
	}

	public String getTargetResponseTag()
	{
		return Requests.BookListNew.RESPONSE_TAG;
	}
}
