package com.libraryclient.content.items;

import java.util.HashMap;

import com.libraryclient.content.Item;

public class BookInfoGeneral extends Item {
	HashMap<Integer, String> map = new HashMap<Integer, String>();

	public static final String XML_TAG = ":Book:Book";

	public BookInfoGeneral() {
		super(XML_TAG);
	}

	@Override
	public void mapValues() {
		// TODO: Implement this method
	}

	@Override
	public String getValue(int nameConstant) {
		return map.get(nameConstant);
	}
}
