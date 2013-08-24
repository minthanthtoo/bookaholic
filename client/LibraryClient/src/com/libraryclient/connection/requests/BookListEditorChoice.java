package com.libraryclient.connection.requests;

import com.libraryclient.connection.Request;

public class BookListEditorChoice extends Request {
	public BookListEditorChoice(String requestData) {
		this(Requests.AuthorList.REQUEST_CODE, requestData);
	}

	private BookListEditorChoice(int requestCode, String requestData) {
		super(requestCode, requestData);
	}

	@Override
	public String getTargetResponseTag() {
		return Requests.AuthorList.RESPONSE_TAG;
	}
}