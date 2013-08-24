package com.bookaholic.menu;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public abstract class MenuItem {

	public abstract String getTitle();

	public abstract void setTitle(String title);

	public abstract String getType();

	public abstract View bindView(Context context, ViewGroup vg, View container);

}