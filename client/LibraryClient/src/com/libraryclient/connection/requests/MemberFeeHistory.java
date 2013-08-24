package com.libraryclient.connection.requests;

import com.libraryclient.connection.Request;

public class MemberFeeHistory extends Request {
	public MemberFeeHistory(String requestData) {
		this(Requests.AuthorList.REQUEST_CODE, requestData);
	}

	private MemberFeeHistory(int requestCode, String requestData) {
		super(requestCode, requestData);
	}

	@Override
	public String getTargetResponseTag() {
		return Requests.AuthorList.RESPONSE_TAG;
	}
}