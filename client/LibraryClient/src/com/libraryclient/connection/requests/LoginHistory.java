package com.libraryclient.connection.requests;

import com.libraryclient.connection.Request;

public class LoginHistory extends Request {
	public LoginHistory(String requestData) {
		this(Requests.BookListTopBorrow.REQUEST_CODE, requestData);
	}

	private LoginHistory(int requestCode, String requestData) {
		super(requestCode, requestData);
	}

	@Override
	public String getTargetResponseTag() {
		return Requests.BookListTopBorrow.RESPONSE_TAG;
	}
}