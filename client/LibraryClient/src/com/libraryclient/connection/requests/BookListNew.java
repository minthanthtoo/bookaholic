package com.libraryclient.connection.requests;

import com.libraryclient.connection.Request;

public class BookListNew extends Request {
	public BookListNew(String requestData) {
		this(Requests.AuthorList.REQUEST_CODE, requestData);
	}

	private BookListNew(int requestCode, String requestData) {
		super(requestCode, requestData);
	}

	@Override
	public String getTargetResponseTag() {
		return Requests.BookListNew.RESPONSE_TAG;
	}
}
