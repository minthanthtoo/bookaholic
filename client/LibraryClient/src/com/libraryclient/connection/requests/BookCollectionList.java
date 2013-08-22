package com.libraryclient.connection.requests;

import com.libraryclient.connection.*;

public class BookCollectionList extends Request
{
	public BookCollectionList(String requestData)
	{
		this(Requests.AuthorList.REQUEST_CODE, requestData);
	}

	private BookCollectionList(int requestCode, String requestData)
	{
		super(requestCode, requestData);
	}

	public String getTargetResponseTag()
	{
		return Requests.AuthorList.RESPONSE_TAG;
	}
}