package com.testbuild.fragments;
import android.content.*;
import android.net.*;
import android.os.*;
import android.support.v4.app.*;
import android.support.v4.view.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.fima.cardsui.views.*;
import com.testbuild.*;
import com.testbuild.cards.*;
import java.util.*;

public class BrowseBooks extends Fragment implements OnClickListener
{

	public static String TAG = BrowseBooks.class.getSimpleName();

	public static final String SCHEME="action";
	public static final String AUTHORITY="library.books.browse";
	public static final Uri URI= new Uri.Builder()
	.scheme(SCHEME)
	.authority(AUTHORITY).build();

	private static final boolean DEBUG = true;

	private CardUI main;

	public interface OnSettingsChangedListener
	{
		void onSettingChanged(int prefId, int value);
	}

	private View viewRoot;
	private OnSettingsChangedListener mSettingsChangedListener;

	public void setOnSettingsChangedListener(OnSettingsChangedListener listener)
	{
		mSettingsChangedListener = listener;
	}

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		outState.putString("tab", mTabHost.getCurrentTabTag());
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		if (savedInstanceState != null)
		{
		}

		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle outState) {
		super.setRetainInstance(true);
		if(DEBUG)
			Log.v(TAG,"SYATE : Frag_BrowseBooks : onCreate : bundle = "+outState);
		super.onCreate(outState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		viewRoot = inflater.inflate(R.layout.frag_browse_books, container, false);
		/***** CurdUI implementations *****/
		main = (CardUI) viewRoot.findViewById(R.id.main_cardUI);
		TitleDescCard card1= new TitleDescCard("Browse books");
//		main.addCard(card1);
//		main.addCard(card1);
//		main.refresh();

		//     setContentView(R.layout.fragment_tabs_pager);
        mTabHost = (TabHost)viewRoot.findViewById(R.id.main_tabHost);
        mTabHost.setup();

        mViewPager = (ViewPager)viewRoot.findViewById(R.id.browse_book_pager);

        mTabsAdapter = new TabsAdapter(this.getActivity(), mTabHost, mViewPager);

        mTabsAdapter.addTab(mTabHost.newTabSpec("newbooks").setIndicator("New"),
							NewBooks.class, null);
		mTabsAdapter.addTab(mTabHost.newTabSpec("topbooks").setIndicator("Top Rating"),
		TopRatingBooks.class,null);
        if (savedInstanceState != null)
		{
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }
		if (DEBUG)
			Log.d(TAG,
				  "Inflate Layout: view result=" + viewRoot + " : CardUI" + main);
		return viewRoot;
	}

	@Override
	public void onClick(final View v)
	{
		final int id = v.getId();

		final int titleId;
		final int valueId;
		final int itemsArrayId;
		final int valuesArrayId;

		switch (id)
		{
			case 0:
				break;
			default:
				return;
		}

		final Fragment prev = getFragmentManager().findFragmentByTag(BrowseBooks.TAG);
		if (prev != null)
		{
			getFragmentManager().beginTransaction().remove(prev).commit();
		}

	}

	private void saveStringPrefState(Bundle outState, int prefValue)
	{
		final TextView viewValue = (TextView) viewRoot.findViewById(prefValue);
		outState.putString(String.valueOf(prefValue), viewValue.getText().toString());
	}

	private void saveBooleanPrefState(Bundle outState, int prefValue)
	{
		final CompoundButton viewValue = (CompoundButton) viewRoot.findViewById(prefValue);
		outState.putBoolean(String.valueOf(prefValue), viewValue.isChecked());
	}

	private String restoreStringPrefState(Bundle savedInstanceState, int prefValue)
	{
		final String value = savedInstanceState.getString(String.valueOf(prefValue));
		final TextView viewValue = (TextView) viewRoot.findViewById(prefValue);
		viewValue.setText(value);
		return value;
	}

	private boolean restoreBooleanPrefState(Bundle savedInstanceState, int prefValue)
	{
		final boolean value = savedInstanceState.getBoolean(String.valueOf(prefValue));
		final CompoundButton viewValue = (CompoundButton) viewRoot.findViewById(prefValue);
		viewValue.setChecked(value);
		return value;
	}

	TabHost mTabHost;
    ViewPager  mViewPager;
    TabsAdapter mTabsAdapter;


    /**
     * This is a helper class that implements the management of tabs and all
     * details of connecting a ViewPager with associated TabHost.  It relies on a
     * trick.  Normally a tab host has a simple API for supplying a View or
     * Intent that each tab will show.  This is not sufficient for switching
     * between pages.  So instead we make the content part of the tab host
     * 0dp high (it is not shown) and the TabsAdapter supplies its own dummy
     * view to show as the tab content.  It listens to changes in tabs, and takes
     * care of switch to the correct paged in the ViewPager whenever the selected
     * tab changes.
     */
    public static class TabsAdapter extends FragmentPagerAdapter
	implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener
	{
        private final Context mContext;
        private final TabHost mTabHost;
        private final ViewPager mViewPager;
        private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

        static final class TabInfo
		{
            private final String tag;
            private final Class<?> clss;
            private final Bundle args;

            TabInfo(String _tag, Class<?> _class, Bundle _args)
			{
                tag = _tag;
                clss = _class;
                args = _args;
            }
        }

        static class DummyTabFactory implements TabHost.TabContentFactory
		{
            private final Context mContext;

            public DummyTabFactory(Context context)
			{
                mContext = context;
            }

            @Override
            public View createTabContent(String tag)
			{
                View v = new View(mContext);
                v.setMinimumWidth(0);
                v.setMinimumHeight(0);
                return v;
            }
        }

        public TabsAdapter(FragmentActivity activity, TabHost tabHost, ViewPager pager)
		{
            super(activity.getSupportFragmentManager());
            mContext = activity;
            mTabHost = tabHost;
            mViewPager = pager;
            mTabHost.setOnTabChangedListener(this);
            mViewPager.setAdapter(this);
            mViewPager.setOnPageChangeListener(this);
        }

        public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args)
		{
            tabSpec.setContent(new DummyTabFactory(mContext));
            String tag = tabSpec.getTag();

            TabInfo info = new TabInfo(tag, clss, args);
            mTabs.add(info);
            mTabHost.addTab(tabSpec);
            notifyDataSetChanged();
        }

        @Override
        public int getCount()
		{
            return mTabs.size();
        }

        @Override
        public Fragment getItem(int position)
		{
            TabInfo info = mTabs.get(position);
            return Fragment.instantiate(mContext, info.clss.getName(), info.args);
        }

        @Override
        public void onTabChanged(String tabId)
		{
            int position = mTabHost.getCurrentTab();
            mViewPager.setCurrentItem(position);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
		{
        }

        @Override
        public void onPageSelected(int position)
		{
            // Unfortunately when TabHost changes the current tab, it kindly
            // also takes care of putting focus on it when not in touch mode.
            // The jerk.
            // This hack tries to prevent this from pulling focus out of our
            // ViewPager.
            TabWidget widget = mTabHost.getTabWidget();
            int oldFocusability = widget.getDescendantFocusability();
            widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            mTabHost.setCurrentTab(position);
            widget.setDescendantFocusability(oldFocusability);
        }

        @Override
        public void onPageScrollStateChanged(int state)
		{
        }
    }
}
