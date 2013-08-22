package com.libraryclient.connection.requests;

public class Requests {
	public static final class BasicRequest {
		// public static final int REQUEST_CODE =1;
		public static final String RESPONSE_TAG = ":BasicRequest:BasicRequest";
	}

	public static final class BookInfo {
		public static final int REQUEST_CODE = 1;
		public static final String RESPONSE_TAG = ":Book:Book";
	}

	public static final class BookListSimilarView {
		public static final int REQUEST_CODE = 2;
		public static final String RESPONSE_TAG = ":Book:Book";
	}

	public static final class BookListSimilarBorrow {
		public static final int REQUEST_CODE = 3;
		public static final String RESPONSE_TAG = ":Book:Book";
	}

	public static final class BookListNew {
		public static final int REQUEST_CODE = 4;
		public static final String RESPONSE_TAG = ":Book:Book";

		public static final class Constants {
			// <Book id="1" name="aksfj"
			// description="/sdcard/htdocs/MyLibraryApp/data/books/1/des"
			// createdDate="2012-12-15 00:00:00"><Author id="1"
			// name="auth"/><Score><RatingScore got="4.0000"
			// given="5"></RatingScore><BorrowScore>17</BorrowScore><LikeScore>0</LikeScore><ReviewScore>0</ReviewScore></Score><Status
			// isOnShelf="1" isInSuggestion="0" isInCollection="0"/></Book>

			public static final String BOOK_ID = "id";
			public static final String BOOK_NAME = "name";
			public static final String DESCRIPTION_URI = "description";
			public static final String CREATED_DATETIME = "createdDate";
			public static final String IMAGE_URI = "bookDetail:imageUri";
			public static final String AUTHOR_ID = "id";
			public static final String AUTHOR_NAME = "name";
			public static final String RATING_NUM = "bookDetail:ratingNum";
			public static final String RATING_VALUE = "got";
			public static final String RATING_BAR_STAR_NUM = "given";
			public static final String LIKES_NUM = "LikeScore";
			public static final String BORROW_NUM = "BorrowScore";
			public static final String REVIEWS_NUM = "ReviewScore";
			public static final String STATUS_ISONSHELF = "isOnShelf";
			public static final String STATUS_ISINSUGGESTION = "isInSuggestion";
			public static final String STATUS_ISINCOLLECTION = "isInCollection";
		}
	}

	public static final class BookListTrending {
		public static final int REQUEST_CODE = 5;
		public static final String RESPONSE_TAG = ":Book:Book";
	}

	public static final class BookListTopBorrow {
		public static final int REQUEST_CODE = 6;
		public static final String RESPONSE_TAG = ":Book:Book";
	}

	public static final class BookListTopRating {
		public static final int REQUEST_CODE = 7;
		public static final String RESPONSE_TAG = ":Book:Book";
	}

	public static final class BookListEditorChoice {
		public static final int REQUEST_CODE = 8;
		public static final String RESPONSE_TAG = ":Book:Book";
	}

	public static final class BookListSearchResult {
		public static final int REQUEST_CODE = 9;
		public static final String RESPONSE_TAG = ":Book:Book";
	}

	public static final class BookTypesList {
		public static final int REQUEST_CODE = 10;
		public static final String RESPONSE_TAG = ":BookType:BookType";
	}

	public static final class BookCollectionList {
		public static final int REQUEST_CODE = 11;
		public static final String RESPONSE_TAG = ":BookCollection:BookCollection";
	}

	public static final class BookListCollection {
		public static final int REQUEST_CODE = 14;
		public static final String RESPONSE_TAG = ":BookCollection:BookCollection";
	}

	public static final class BookSuggestionList {
		public static final int REQUEST_CODE = 12;
		public static final String RESPONSE_TAG = ":BookSuggest:BookSuggest";
	}

	public static final class BookListSuggestion {
		public static final int REQUEST_CODE = 12;
		public static final String RESPONSE_TAG = ":BookSuggest:BookSuggest";
	}

	public static final class AuthorList {
		public static final int REQUEST_CODE = 13;
		public static final String RESPONSE_TAG = ":Author:Author";
	}

	public static final class DonorList {
		public static final int REQUEST_CODE = 15;
		public static final String RESPONSE_TAG = ":Donor:Donor";
	}

	public static final class MemberInfo {
		public static final int REQUEST_CODE = 16;
		public static final String RESPONSE_TAG = ":Member:Member";
	}

	public static final class BookLendingRecords {
		public static final int REQUEST_CODE = 17;
		public static final String RESPONSE_TAG = ":LendRecord:LendRecord";
	}

	public static final class MemberFeeHitory {
		public static final int REQUEST_CODE = 18;
		public static final String RESPONSE_TAG = ":MemberFee:MemberFee";
	}

	public static final class LoginHistory {
		public static final int REQUEST_CODE = 19;
		public static final String RESPONSE_TAG = ":Login:Login";
	}
}