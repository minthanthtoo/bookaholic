package com.libraryclient.connection.requests;

import com.libraryclient.connection.*;

public class BookListSimilarView extends Request
{
	public BookListSimilarView(String requestData)
	{
		this(Requests.AuthorList.REQUEST_CODE, requestData);
	}

	private BookListSimilarView(int requestCode, String requestData)
	{
		super(requestCode, requestData);
	}

	public String getTargetResponseTag()
	{
		return Requests.AuthorList.RESPONSE_TAG;
	}
}