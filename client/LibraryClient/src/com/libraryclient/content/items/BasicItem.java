package com.libraryclient.content.items;

import com.libraryclient.content.*;
import java.util.*;

public class BasicItem extends Item
{
	public static final String XML_TAG = ":MyLibraryApp:MyLibraryApp";
	
	List<Item> SubItems = new ArrayList<Item>();
	Map<String, String> Attributes = new HashMap<String, String>();
	String ItemValue = "";

//	BasicItem(String name, Map<String, String> attributes, List<Item> subItems)
//	{
//		super(name);
//		Name = name;
//		SubItems = subItems;
//		Attributes = attributes;
//	}
//
	public BasicItem()
	{
		super(XML_TAG);
	}
//	public BasicItem(String name)
//	{
//		super(name);
//	}

	protected int getSubItemCount()
	{
		return this.SubItems.size();
	}

	public String getItemName()
	{
		return XML_TAG;
	}

	protected void setItemValue(String string)
	{
		ItemValue = string;
	}

	protected void concatItemValue(String string)
	{
		ItemValue += string;
	}

	protected void addAttribute(String name, String value)
	{
		Attributes.put(name, value);
	}

	protected String getAttribute(String attribName)
	{
		return Attributes.get(attribName);
	}

	protected void addSubItem(Item i)
	{
		SubItems.add(i);
	}

	protected Item getSubItem(int index)
	{
		return SubItems.get(index);
	}
	
}
