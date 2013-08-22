package com.libraryclient.connection.requests;

import com.libraryclient.connection.*;

public class MemberInfo extends Request
{
	public MemberInfo(String requestData)
	{
		this(Requests.BookListTopBorrow.REQUEST_CODE, requestData);
	}

	private MemberInfo(int requestCode, String requestData)
	{
		super(requestCode, requestData);
	}

	public String getTargetResponseTag()
	{
		return Requests.BookListTopBorrow.RESPONSE_TAG;
	}
}