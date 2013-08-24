package com.libraryclient.connection;

import com.libraryclient.connection.requests.Requests;

public class BasicRequest extends Request {
	public BasicRequest(int requestCode, String requestData) {
		super(requestCode, requestData);
	}

	/**
	 * return String that will be the name of the target tags in the response
	 * xml stream.These target tags will be parsed as response items and their
	 * lower level tags will also be added as their sub-items
	 * 
	 */
	@Override
	public String getTargetResponseTag() {
		return Requests.BasicRequest.RESPONSE_TAG;
	};
}