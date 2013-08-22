package com.testbuild.menu;

import android.content.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import com.testbuild.*;

public class MenuCategory  extends MenuItem
{
	public static String TAG = MenuCategory.class.getSimpleName();

	private boolean DEBUG = true;

	public MenuCategory(String mName)
	{
		this.mTitle = mName;
		this.mImageId = 0;
	}

	public MenuCategory(String mTitle, int mImageId)
	{
		this.mTitle = mTitle;
		this.mImageId = mImageId;
	}

	public String getTitle()
	{
		return this.mTitle;
	}

	public void setTitle(String title)
	{
		this.mTitle = title;
		this.mTitleView.setText(title);
	}

	public String getType()
	{
		return TAG;
	}
	
	TextView mTitleView = null;
	ImageView mImageView = null;
	String mTitle = null;
	int mImageId;

	public TextView getTextView()
	{
		// TODO: Implement this method
		return mTitleView;
	}

//
//	public String getCategory()
//	{
//		// TODO: Implement this method
//		return null;
//	}
//

	public View bindView(Context context, ViewGroup vg,View container)
	{
		if(container==null){
			container = LayoutInflater.from(context).inflate(R.layout.menu_list_group_item , vg , false);
			if (DEBUG)
				Log.d(TAG, "Create MenuCategory=" + getTitle() + ": pos=" );
		}
		mTitleView = (TextView) container.findViewById(R.id.menu_text);
		mTitleView.setText(mTitle);
		if (mImageId != 0)
		{
			mImageView = (ImageView) container.findViewById(R.id.menu_ico);
			mImageView.setImageResource(mImageId);
		}

		if (DEBUG)
			Log.d(TAG, "getView: MenuTitle=" + mTitle + " : TitleView" + mTitleView);
		return container;
	}
}