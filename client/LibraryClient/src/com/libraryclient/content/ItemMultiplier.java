package com.libraryclient.content;

import com.libraryclient.content.items.BasicItem;
import com.libraryclient.content.items.BookInfoGeneral;

public class ItemMultiplier<E extends Item> {
	private E primaryItem;

	public ItemMultiplier(int itemTypeConst) {
		switch (itemTypeConst) {
		case Item.TYPE_BASIC:
			primaryItem = (E) new BasicItem();// TODO:
			break;
		case Item.TYPE_BOOK_INFO_GENERAL:
			primaryItem = (E) new BookInfoGeneral();// TODO:
			break;
		default:
			throw new IllegalArgumentException("Not Item Type Constant");
		}
	}

	public E newItem() {
		try {
			return (E) primaryItem.getClass().newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
