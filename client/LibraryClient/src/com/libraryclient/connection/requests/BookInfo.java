package com.libraryclient.connection.requests;

import com.libraryclient.connection.*;

public class BookInfo extends Request
{
	public BookInfo(String requestData)
	{
		this(Requests.BookInfo.REQUEST_CODE, requestData);
	}

	private BookInfo(int requestCode, String requestData)
	{
		super(requestCode, requestData);
	}

	public String getTargetResponseTag()
	{
		return Requests.BookInfo.RESPONSE_TAG;
	}
}