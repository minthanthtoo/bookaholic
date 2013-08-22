package com.libraryclient.content.items;

import com.libraryclient.content.*;
import java.util.*;

public class BookInfoGeneral extends Item
{
HashMap<Integer,String> map = new HashMap<Integer,String>();

	public static final String XML_TAG = ":Book:Book";
	
	public BookInfoGeneral(){
		super(XML_TAG);
	}
	
	public void mapValues()
	{
		// TODO: Implement this method
	}

	public String getValue(int nameConstant)
	{
		return map.get(nameConstant);
	}
}
