package com.libraryclient.connection.requests;

import com.libraryclient.connection.Request;

public class BookCollectionList extends Request {
	public BookCollectionList(String requestData) {
		this(Requests.AuthorList.REQUEST_CODE, requestData);
	}

	private BookCollectionList(int requestCode, String requestData) {
		super(requestCode, requestData);
	}

	@Override
	public String getTargetResponseTag() {
		return Requests.AuthorList.RESPONSE_TAG;
	}
}