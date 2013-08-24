package com.libraryclient.content.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.libraryclient.content.Item;

public class BasicItem extends Item {
	public static final String XML_TAG = ":MyLibraryApp:MyLibraryApp";

	List<Item> SubItems = new ArrayList<Item>();
	Map<String, String> Attributes = new HashMap<String, String>();
	String ItemValue = "";

	// BasicItem(String name, Map<String, String> attributes, List<Item>
	// subItems)
	// {
	// super(name);
	// Name = name;
	// SubItems = subItems;
	// Attributes = attributes;
	// }
	//
	public BasicItem() {
		super(XML_TAG);
	}

	// public BasicItem(String name)
	// {
	// super(name);
	// }

	@Override
	protected int getSubItemCount() {
		return this.SubItems.size();
	}

	@Override
	public String getItemName() {
		return XML_TAG;
	}

	@Override
	protected void setItemValue(String string) {
		ItemValue = string;
	}

	@Override
	protected void concatItemValue(String string) {
		ItemValue += string;
	}

	@Override
	protected void addAttribute(String name, String value) {
		Attributes.put(name, value);
	}

	@Override
	protected String getAttribute(String attribName) {
		return Attributes.get(attribName);
	}

	@Override
	protected void addSubItem(Item i) {
		SubItems.add(i);
	}

	@Override
	protected Item getSubItem(int index) {
		return SubItems.get(index);
	}

}
