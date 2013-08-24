package com.libraryclient.connection.requests;

import com.libraryclient.connection.Request;

public class BookSuggestionList extends Request {
	public BookSuggestionList(String requestData) {
		this(Requests.BookListTopBorrow.REQUEST_CODE, requestData);
	}

	private BookSuggestionList(int requestCode, String requestData) {
		super(requestCode, requestData);
	}

	@Override
	public String getTargetResponseTag() {
		return Requests.BookListTopBorrow.RESPONSE_TAG;
	}
}