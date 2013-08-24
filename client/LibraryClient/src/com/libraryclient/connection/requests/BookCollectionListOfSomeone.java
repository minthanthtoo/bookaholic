package com.libraryclient.connection.requests;

import com.libraryclient.connection.Request;

public class BookCollectionListOfSomeone extends Request {
	public BookCollectionListOfSomeone(String requestData) {
		this(Requests.AuthorList.REQUEST_CODE, requestData);
	}

	private BookCollectionListOfSomeone(int requestCode, String requestData) {
		super(requestCode, requestData);
	}

	@Override
	public String getTargetResponseTag() {
		return Requests.AuthorList.RESPONSE_TAG;
	}
}