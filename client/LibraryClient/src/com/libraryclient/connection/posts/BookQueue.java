package com.libraryclient.connection.posts;

import com.libraryclient.connection.Post;

public class BookQueue extends Post {
	public BookQueue(String postData) {
		this(Posts.BookQueue.POST_CODE, postData);
	}

	private BookQueue(int postCode, String postData) {
		super(postCode, postData);
	}

	@Override
	public String getTargetResponseTag() {
		return Posts.BookQueue.RESPONSE_TAG;
	}
}