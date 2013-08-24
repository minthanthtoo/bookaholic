package com.libraryclient.connection.posts;

import com.libraryclient.connection.Post;

public class BookBorrow extends Post {
	public BookBorrow(String postData) {
		this(Posts.BookBorrow.POST_CODE, postData);
	}

	private BookBorrow(int postCode, String postData) {
		super(postCode, postData);
	}

	@Override
	public String getTargetResponseTag() {
		return Posts.BookBorrow.RESPONSE_TAG;
	}
}