package com.libraryclient.connection.requests;

import com.libraryclient.connection.*;

public class BookLendingRecords extends Request
{
	public BookLendingRecords(String requestData)
	{
		this(Requests.AuthorList.REQUEST_CODE, requestData);
	}

	private BookLendingRecords(int requestCode, String requestData)
	{
		super(requestCode, requestData);
	}

	public String getTargetResponseTag()
	{
		return Requests.AuthorList.RESPONSE_TAG;
	}
}