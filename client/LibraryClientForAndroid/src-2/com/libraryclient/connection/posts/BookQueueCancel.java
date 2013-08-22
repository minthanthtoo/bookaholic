package com.libraryclient.connection.posts;

import com.libraryclient.connection.Post;

public class BookQueueCancel extends Post {
	public BookQueueCancel(String postData) {
		this(Posts.BookQueueCancel.POST_CODE, postData);
	}

	private BookQueueCancel(int postCode, String postData) {
		super(postCode, postData);
	}

	public String getTargetResponseTag() {
		return Posts.BookQueueCancel.RESPONSE_TAG;
	}
}