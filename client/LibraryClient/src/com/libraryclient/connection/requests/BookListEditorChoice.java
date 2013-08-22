package com.libraryclient.connection.requests;

import com.libraryclient.connection.*;

public class BookListEditorChoice extends Request
{
	public BookListEditorChoice(String requestData)
	{
		this(Requests.AuthorList.REQUEST_CODE, requestData);
	}

	private BookListEditorChoice(int requestCode, String requestData)
	{
		super(requestCode, requestData);
	}

	public String getTargetResponseTag()
	{
		return Requests.AuthorList.RESPONSE_TAG;
	}
}