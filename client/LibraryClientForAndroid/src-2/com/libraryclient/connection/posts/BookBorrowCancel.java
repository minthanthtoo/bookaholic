package com.libraryclient.connection.posts;

import com.libraryclient.connection.Post;

public class BookBorrowCancel extends Post {
	public BookBorrowCancel(String postData) {
		this(Posts.BookBorrowCancel.POST_CODE, postData);
	}

	private BookBorrowCancel(int postCode, String postData) {
		super(postCode, postData);
	}

	public String getTargetResponseTag() {
		return Posts.BookBorrowCancel.RESPONSE_TAG;
	}
}