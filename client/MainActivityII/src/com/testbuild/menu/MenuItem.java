package com.testbuild.menu;
import android.content.*;
import android.view.*;

public abstract class MenuItem
{
	
	public abstract String getTitle();

	public abstract void setTitle(String title);
	
	public abstract String getType();

	public abstract View bindView(Context context, ViewGroup vg,View container);

}