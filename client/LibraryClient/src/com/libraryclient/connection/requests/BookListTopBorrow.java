package com.libraryclient.connection.requests;

import com.libraryclient.connection.Request;

public class BookListTopBorrow extends Request {
	public BookListTopBorrow(String requestData) {
		this(Requests.BookListTopBorrow.REQUEST_CODE, requestData);
	}

	private BookListTopBorrow(int requestCode, String requestData) {
		super(requestCode, requestData);
	}

	@Override
	public String getTargetResponseTag() {
		return Requests.BookListTopBorrow.RESPONSE_TAG;
	}
}