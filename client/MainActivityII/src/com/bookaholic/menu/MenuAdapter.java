package com.bookaholic.menu;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuAdapter extends BaseAdapter {
	public static boolean DEBUG = true;
	private Context mContext = null;
	private ViewHolder mViewHolder = new ViewHolder();

	private List<MenuItem> mMenuItems = new ArrayList<MenuItem>();

	String TAG = MenuAdapter.class.getSimpleName();

	private LayoutInflater mInflater = null;

	public MenuAdapter(Context nContext, List<MenuItem> menuList) {
		this.mContext = nContext;
		this.mInflater = (LayoutInflater) this.mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mMenuItems = menuList;
	}

	@Override
	public int getCount() {
		return mMenuItems.size();
	}

	@Override
	public Object getItem(int p1) {
		return mMenuItems.get(p1);
	}

	@Override
	public long getItemId(int p1) {
		return p1;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	@Override
	public boolean isEnabled(int position) {
		String type = ((MenuItem) getItem(position)).getType();
		return (type == MenuCategory.TAG) ? false : true;
	}

	@Override
	public int getItemViewType(int position) {
		return (getItem(position) instanceof MenuCategory) ? 0 : 1;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MenuItem menu = (MenuItem) getItem(position);

		convertView = menu.bindView(mContext, parent, convertView);

		if (menu instanceof MenuAction) {
			// TODO: String category =((MenuAction)menu).getCategory();
			// TODO: parent.findViewWithTag(category);
			if (DEBUG)
				Log.d(TAG, "Get MenuAction=" + menu.getTitle() + ": pos="
						+ position);
		} else if (menu instanceof MenuCategory) {
			if (DEBUG)
				Log.d(TAG, "Get MenuCategory=" + menu.getTitle() + ": pos="
						+ position);
		} else {
			if (DEBUG)
				Log.d(TAG, "MenuItem is of wrong Type. Position" + position);
		}
		return convertView;
	}

	class ViewHolder {
		TextView titleView;
		ImageView titleImage;
	}
}