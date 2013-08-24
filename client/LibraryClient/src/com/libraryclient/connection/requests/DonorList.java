package com.libraryclient.connection.requests;

import com.libraryclient.connection.Request;

public class DonorList extends Request {
	public DonorList(String requestData) {
		this(Requests.BookListTopBorrow.REQUEST_CODE, requestData);
	}

	private DonorList(int requestCode, String requestData) {
		super(requestCode, requestData);
	}

	@Override
	public String getTargetResponseTag() {
		return Requests.BookListTopBorrow.RESPONSE_TAG;
	}
}