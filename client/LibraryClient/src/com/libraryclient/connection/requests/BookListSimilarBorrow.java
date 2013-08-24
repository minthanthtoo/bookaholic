package com.libraryclient.connection.requests;

import com.libraryclient.connection.Request;

public class BookListSimilarBorrow extends Request {
	public BookListSimilarBorrow(String requestData) {
		this(Requests.AuthorList.REQUEST_CODE, requestData);
	}

	private BookListSimilarBorrow(int requestCode, String requestData) {
		super(requestCode, requestData);
	}

	@Override
	public String getTargetResponseTag() {
		return Requests.AuthorList.RESPONSE_TAG;
	}
}