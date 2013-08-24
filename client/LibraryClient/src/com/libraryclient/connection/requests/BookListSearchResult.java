package com.libraryclient.connection.requests;

import com.libraryclient.connection.Request;

public class BookListSearchResult extends Request {
	public BookListSearchResult(String requestData) {
		this(Requests.AuthorList.REQUEST_CODE, requestData);
	}

	private BookListSearchResult(int requestCode, String requestData) {
		super(requestCode, requestData);
	}

	@Override
	public String getTargetResponseTag() {
		return Requests.AuthorList.RESPONSE_TAG;
	}
}