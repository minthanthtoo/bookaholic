package com.libraryclient.connection.requests;

import com.libraryclient.connection.Request;

public class AuthorList extends Request {
	public AuthorList(String requestData) {
		this(Requests.AuthorList.REQUEST_CODE, requestData);
	}

	private AuthorList(int requestCode, String requestData) {
		super(requestCode, requestData);
	}

	@Override
	public String getTargetResponseTag() {
		return Requests.AuthorList.RESPONSE_TAG;
	}
}