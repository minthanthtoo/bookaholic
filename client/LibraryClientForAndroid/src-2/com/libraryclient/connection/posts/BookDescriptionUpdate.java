package com.libraryclient.connection.posts;

import com.libraryclient.connection.Post;

public class BookDescriptionUpdate extends Post {
	public BookDescriptionUpdate(String postData) {
		this(Posts.BookDescriptionUpdate.POST_CODE, postData);
	}

	private BookDescriptionUpdate(int postCode, String postData) {
		super(postCode, postData);
	}

	public String getTargetResponseTag() {
		return Posts.BookDescriptionUpdate.RESPONSE_TAG;
	}
}