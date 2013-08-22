package com.libraryclient.connection.posts;

import com.libraryclient.connection.Post;

public class BookReturn extends Post {
	public BookReturn(String postData) {
		this(Posts.BookReturn.POST_CODE, postData);
	}

	private BookReturn(int postCode, String postData) {
		super(postCode, postData);
	}

	public String getTargetResponseTag() {
		return Posts.BookReturn.RESPONSE_TAG;
	}
}