package com.libraryclient.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public abstract class Item implements Mappable {
	String Name;
	List<Item> SubItems = new ArrayList<Item>();
	Map<String, String> Attributes = new HashMap<String, String>();
	String ItemValue = "";

	Item(String name, Map<String, String> attributes, List<Item> subItems) {
		super();
		Name = name;
		SubItems = subItems;
		Attributes = attributes;
	}

	public Item(String name) {
		Name = name;
	}

	protected int getSubItemCount() {
		return this.SubItems.size();
	}

	public String getItemName() {
		return Name;
	}

	protected void setItemValue(String string) {
		ItemValue = string;
	}

	protected void concatItemValue(String string) {
		ItemValue += string;
	}

	protected void addAttribute(String name, String value) {
		Attributes.put(name, value);
	}

	protected String getAttribute(String attribName) {
		return Attributes.get(attribName);
	}

	protected void addSubItem(Item i) {
		SubItems.add(i);
	}

	protected Item getSubItem(int index) {
		return SubItems.get(index);
	}

	@Override
	public String toString() {
		String s = "\n[ItemName=" + Name;
		s += ",Item-value=" + ItemValue;
		if ((Attributes.size()) > 0)
			s += ",Attribs=[";
		Set<Entry<String, String>> set = Attributes.entrySet();
		Iterator<Entry<String, String>> it = set.iterator();
		while (it.hasNext()) {
			Entry<String, String> e = it.next();
			s += "," + e.getKey() + "=" + e.getValue();
		}
		s += "]";
		// call the toString methods of sub-items
		Iterator<Item> it2 = SubItems.iterator();
		if (it2.hasNext())
			s += ",SubItems=[";
		while (it2.hasNext()) {
			s += it2.next().toString();
		}
		s += "]";
		return s;
	}

	Mappable mMappable;

	@Override
	public void mapValues() {
		mMappable.mapValues();
	}

	@Override
	public String getValue(int nameConstant) {
		return mMappable.getValue(nameConstant);
	}

	/**
	 * Item Type constants
	 */
	public static final int TYPE_BASIC = 0;
	public static final int TYPE_BOOK_INFO_GENERAL = 1;
}

interface Mappable {
	public void mapValues();

	public String getValue(int nameConstant);
}
