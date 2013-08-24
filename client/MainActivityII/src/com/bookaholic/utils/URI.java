package com.bookaholic.utils;

import android.net.Uri;

public class URI {
	public static String[][] queryToArrays(Uri uri) {
		String[] s = uri.getEncodedQuery().split("&");
		String[][] pairs = new String[s.length][2];

		for (int i = 0; i < s.length; i++) {
			pairs[i] = s[i].split("=");
			System.out.println(pairs[i][0] + "," + pairs[i][1]);
			pairs[i][0] = Uri.decode(pairs[i][0]);
			pairs[i][1] = Uri.decode(pairs[i][1]);
		}
		return pairs;
	}

	public static final class Query {

		private String query = "";

		public Query put(String name, String value) {
			if (name == null || value == null || name == "" || value == "")
				return this;
			query += "&";
			query += Uri.encode(name) + "=" + Uri.encode(value);
			return this;
		}

		public String build() {
			return query.substring(1);// strip off the first "&" char
		}
	}
}
