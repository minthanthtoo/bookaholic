package com.libraryclient.connection;

import com.libraryclient.connection.posts.Posts;

public class BasicPost extends Post {
	public BasicPost(int postCode, String postData) {
		super(postCode, postData);
	}

	/**
	 * return String that will be the name of the target tags in the response
	 * xml stream.These target tags will be parsed as response items and their
	 * lower level tags will also be added as their sub-items
	 * 
	 */
	@Override
	public String getTargetResponseTag() {
		return Posts.BasicPost.RESPONSE_TAG;
	}

}