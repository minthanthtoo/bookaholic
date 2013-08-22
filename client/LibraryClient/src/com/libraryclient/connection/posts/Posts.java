package com.libraryclient.connection.posts;

public class Posts {

	public static final class BasicPost {
		// public static final int POST_CODE = 1;
		public static final String RESPONSE_TAG = ":BasicPost:BasicPost";
	}

	public static final class BookBorrow {
		public static final int POST_CODE = 1;
		public static final String RESPONSE_TAG = ":Book:Book";
	}

	public static final class BookReturn {
		public static final int POST_CODE = 2;
		public static final String RESPONSE_TAG = ":Book:Book";
	}

	public static final class BookBorrowCancel {
		public static final int POST_CODE = 3;
		public static final String RESPONSE_TAG = ":Book:Book";
	}

	public static final class BookQueue {
		public static final int POST_CODE = 4;
		public static final String RESPONSE_TAG = ":Book:Book";
	}

	public static final class BookQueueCancel {
		public static final int POST_CODE = 5;
		public static final String RESPONSE_TAG = ":Book:Book";
	}

	public static final class BookDescriptionUpdate {
		public static final int POST_CODE = 5;
		public static final String RESPONSE_TAG = ":Book:Book";
	}
}
