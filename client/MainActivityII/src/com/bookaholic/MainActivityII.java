package com.bookaholic;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.bookaholic.fragments.BookDetail;
import com.bookaholic.fragments.BrowseBooks;
import com.bookaholic.fragments.NewBooks;
import com.bookaholic.fragments.TopRatingBooks;
import com.bookaholic.global.Preferences;
import com.bookaholic.menu.MenuAction;
import com.bookaholic.menu.MenuAdapter;
import com.bookaholic.menu.MenuCategory;
import com.bookaholic.menu.MenuItem;
import com.libraryclient.config.Connection;
import com.navdrawer.SimpleSideDrawer;

public class MainActivityII extends BaseActivity implements
		OnItemClickListener, OnBackStackChangedListener {

	private SimpleSideDrawer nav;

	private static String TAG = MainActivityII.class.getSimpleName();

	private static boolean DEBUG = true;

	private String[] mMenuItems;

	private String[] mMenuItemTypes;

	private static FragmentManager FragmentManager;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initialize();
		setContentView(R.layout.activity_main);

		/***** Side-menu im0lementations *****/
		nav = new SimpleSideDrawer(this);
		nav.setLeftBehindContentView(R.layout.right_menu);
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			((Button) findViewById(R.id.main_but))
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View p1) {
							if (nav.isClosed())
								nav.openLeftSide();
						}

					});
		} else {
			getActionBar().setDisplayHomeAsUpEnabled(true);
			getActionBar().setTitle("");
		}

		/***** Menus implementations *****/
		ArrayList<com.bookaholic.menu.MenuItem> menus = new ArrayList<MenuItem>();
		mMenuItems = getResources().getStringArray(R.array.menu_items);

		mMenuItemTypes = getResources()
				.getStringArray(R.array.menu_items_types);

		for (int i = 0; i < mMenuItems.length; i++) {
			Uri uri = Uri.parse(mMenuItemTypes[i]);
			if (DEBUG)
				Log.d(TAG, "MenuInflat: menuItem=" + mMenuItems[i]
						+ " : menutype=" + mMenuItemTypes[i] + " : scheme="
						+ uri.getScheme());
			if (uri.getScheme().equals("action")) {
				menus.add(new MenuAction(mMenuItems[i]));
				if (DEBUG)
					Log.d(TAG, "MenuInflat: OK menuItem=" + mMenuItems[i]);
			} else if (uri.getScheme().equals("category")) {
				menus.add(new MenuCategory(mMenuItems[i]));
				if (DEBUG)
					Log.d(TAG, "MenuInflat: menuItems=" + mMenuItems[i]);

			}
		}
		MenuAdapter adapter = new MenuAdapter(this.getApplicationContext(),
				menus);
		ListView menu = (ListView) findViewById(R.id.menu_list);
		menu.setAdapter(adapter);
		menu.setOnItemClickListener(this);
		menu.invalidate();

		if (DEBUG)
			Log.d(TAG, "MenuInflat: menus=" + menus + ": Adapter=" + adapter);
		//
		// /***** CurdUI implementations *****/
		// main = (CardUI) findViewById(R.id.main_cardUI);
		// TitleDescCard card1= new TitleDescCard("Hello World");
		// main.addCard(card1);
		// main.refresh();
		//

		// mSettingsChangedListener = new SettingsChangedListener();

		// viewActionsContentView = (ActionsContentView)
		// findViewById(R.id.actionsContentView);
		// viewActionsContentView.setSwipingType(ActionsContentView.SWIPING_EDGE);
		//
		// final ListView viewActionsList = (ListView)
		// findViewById(R.id.actions);
		// final ActionsAdapter actionsAdapter = new ActionsAdapter(this);
		// viewActionsList.setAdapter(actionsAdapter);
		// viewActionsList.setOnItemClickListener(new
		// AdapterView.OnItemClickListener() {
		// @Override
		// public void onItemClick(AdapterView<?> adapter, View v, int position,
		// long flags) {
		// final Uri uri = actionsAdapter.getItem(position);
		//
		// if (EffectsExampleActivity.URI.equals(uri)) {
		// startActivity(new Intent(getBaseContext(),
		// EffectsExampleActivity.class));
		// return;
		// }
		//
		// updateContent(uri);
		// viewActionsContentView.showContent();
		// }
		// });

		/***** Fragments implementations *****/
		if (savedInstanceState != null) {
			currentUri = Uri.parse(savedInstanceState.getString(STATE_URI));
			currentContentFragmentTag = savedInstanceState
					.getString(STATE_FRAGMENT_TAG);
		}
		//
		FragmentManager = getSupportFragmentManager();
		FragmentManager.addOnBackStackChangedListener(this);
		updateContent(currentUri);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			if (nav.isClosed())
				nav.openLeftSide();

		}
		return super.onOptionsItemSelected(item);
	}

	private static final String STATE_URI = "state:uri";
	private static final String STATE_FRAGMENT_TAG = "state:fragment_tag";

	// private SettingsChangedListener mSettingsChangedListener;

	// private ActionsContentView viewActionsContentView;

	private static Uri currentUri = BrowseBooks.URI;
	// AboutFragment.ABOUT_URI;
	private static String currentContentFragmentTag = BrowseBooks.TAG;

	/**
	 * if 'back key' is pressed, the last 'BackStackEntry' will be popped up
	 * after this method. So we need to update 'currentUri' with
	 * 'BackStackEntry'.Or 'updateContent(Uri)' will not correctly update the UI
	 * with error,like showing multiple fragments at the same 'FrameLayout
	 * 
	 * @see android.support.v4.app.FragmentActivity#onBackPressed()
	 * 
	 */
	@Override
	public void onBackPressed() {
		if (!nav.isClosed()) {
			nav.toggleLeftDrawer();
			return;
		}
		int i = FragmentManager.getBackStackEntryCount();
		FragmentManager.BackStackEntry last = FragmentManager
				.getBackStackEntryAt(i - 1);
		Log.i(TAG, "STATE: OnBackPressd" + last.getName() + last.getId()
				+ currentContentFragmentTag);
		if (i == 1) {
			finish();
			// return;// if the latest backStackEntry is home fragment,exit the
			// program and cancel the back action
		} else {
			currentContentFragmentTag = FragmentManager.getBackStackEntryAt(
					i - 2).getName();
			currentUri = fragmentTagToUri(currentContentFragmentTag);
		}
		super.onBackPressed();
	}

	@Override
	public void onBackStackChanged() {
		int i = FragmentManager.getBackStackEntryCount();
		if (i > 0) {
			FragmentManager.BackStackEntry last = FragmentManager
					.getBackStackEntryAt(i - 1);
			Log.i(TAG,
					"STATE: BackStackchanged:" + last.getName() + last.getId()
							+ currentContentFragmentTag);
			for (int j = i - 1; j >= 0; j--) {
				last = FragmentManager.getBackStackEntryAt(j);
				Log.i(TAG, "STATE: BackStackchanged:" + last.getId() + "="
						+ last.getName());
			}
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString(STATE_URI, currentUri.toString());
		outState.putString(STATE_FRAGMENT_TAG, currentContentFragmentTag);
		FragmentManager fm = getSupportFragmentManager();
		fm.saveFragmentInstanceState(fm
				.findFragmentByTag(currentContentFragmentTag));
		if (DEBUG)
			Log.d(TAG, "STATE : onSaveInstanceState");
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onRestoreInstanceState(Bundle instate) {
		currentUri = Uri.parse(instate.getString(STATE_URI));
		currentContentFragmentTag = instate.getString(STATE_FRAGMENT_TAG);
		updateContent(currentUri);
		if (DEBUG)
			Log.d(TAG, "STATE : onRestoreInstanceState : result=" + instate);
		// super.onRestoreInstanceState(instate);
	}

	// public void onSourceCodeClick(View view) {
	// final Intent i = new Intent(Intent.ACTION_VIEW);
	// i.setData(Uri.parse(getString(R.string.sources_link)));
	// startActivity(i);
	// }

	private void initialize() {
		SharedPreferences prefs = getSharedPreferences(
				Preferences.Login.PREF_NAME, Preferences.Login.PREF_MODE);
		SharedPreferences.Editor edit = prefs.edit();
		String remote = prefs.getString(Preferences.Login.REMOTE_BACKEND, null);
		String user = prefs.getString(Preferences.Login.LAST_USERNAME, null);
		Boolean isLoggedin = prefs.getBoolean(Preferences.Login.IS_LOGGED_IN,
				false);
		String pass = prefs.getString(Preferences.Login.LAST_USER_PASSWORD,
				null);// TODO: security implementation is required

		if (remote == null) {
			remote = getResources().getString(
					R.string.default_login_remoteBackEnd);
			Connection.CONNECTION_WEBSITE = remote;
			edit.putString(Preferences.Login.REMOTE_BACKEND, remote);
		}

		if (user == null || isLoggedin == false) {

			// LAST_USERNAME
			//
			// LAST_USER_PASSWORD
			//
			// LAST_USER_TYPE
		}
		// get
	}

	public static void updateContent(Uri uri) {
		final Fragment fragment;
		final String tag;
		Bundle args = new Bundle();

		final FragmentManager fm = getMyFragmentManager();
		FragmentManager = fm;
		final FragmentTransaction tr = fm.beginTransaction();
		tr.setTransition((FragmentTransaction.TRANSIT_ENTER_MASK & FragmentTransaction.TRANSIT_FRAGMENT_FADE)
				| (FragmentTransaction.TRANSIT_EXIT_MASK & FragmentTransaction.TRANSIT_FRAGMENT_FADE));
		tr.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

		// Uri query into Bundle
		if (uri.getQuery() != null) {
			Log.d(TAG, "EncodedQuery" + uri.getEncodedQuery());
			String[][] s = com.bookaholic.utils.URI.queryToArrays(uri);
			System.out.println("\n\nQUERY: params:" + s[0][0] + "=" + s[0][1]
					+ ";length=" + s.length);

			for (int i = 0; i < s.length; i++) {
				args.putString(s[i][0], s[i][1]);
			}

			// fragment.setArguments(args);
		}

		if (currentContentFragmentTag != null) {
			final Fragment currentFragment = fm
					.findFragmentByTag(currentContentFragmentTag);
			if (currentFragment != null) {

				tr.hide(currentFragment);
				if (DEBUG)
					Log.d(TAG, "Fragment update: URI=" + currentUri
							+ " \n\tFragment will be hidden");
			} else if (DEBUG)
				Log.d(TAG,
						"Fragment update: CurrentUri=null; New Fragment will be created");
		}
		if (BrowseBooks.URI.equals(uri)) {
			tag = BrowseBooks.TAG;
			final Fragment foundFragment = fm.findFragmentByTag(tag);
			if (foundFragment != null) {
				if (DEBUG)
					Log.d(TAG, "Fragment update: fragment for URI =" + uri
							+ " is found");
				fragment = foundFragment;
			} else {
				if (DEBUG)
					Log.d(TAG, "Fragment update: fragment for URI =" + uri
							+ " is not found!" + "\nNew default fragment "
							+ BrowseBooks.class.getSimpleName() + "is created");
				fragment = new BrowseBooks();
			}
		} else if (NewBooks.URI.equals(uri)) {
			tag = NewBooks.TAG;
			final Fragment foundFragment = fm.findFragmentByTag(tag);
			if (foundFragment != null) {
				if (DEBUG)
					Log.d(TAG, "Fragment update: fragment for URI =" + uri
							+ " is found");
				fragment = foundFragment;
				// ((NewBooks)fragment).refreshCards();
			} else {
				if (DEBUG)
					Log.d(TAG, "Fragment update: fragment for URI =" + uri
							+ " is not found!" + "\nNew default fragment "
							+ NewBooks.class.getSimpleName() + "is created");
				fragment = new NewBooks();
			}
		} else if (BookDetail.URI.getEncodedAuthority().equals(
				uri.getEncodedAuthority())) {
			tag = BookDetail.TAG;
			final Fragment foundFragment = fm.findFragmentByTag(tag);
			if (foundFragment != null) {
				if (DEBUG)
					Log.d(TAG, "Fragment update: fragment for URI =" + uri
							+ " is found");
				fragment = foundFragment;
				((BookDetail) fragment).setBundle(args);
				((BookDetail) fragment).updateUI();
			} else {
				if (DEBUG)
					Log.d(TAG, "Fragment update: fragment for URI =" + uri
							+ " is not found!" + "\nNew default fragment "
							+ BookDetail.class.getSimpleName() + " is created");
				fragment = new BookDetail();
			}
		} else {
			if (DEBUG)
				Log.d(TAG, "Fragment update: Fragment for URI=" + uri
						+ " is not found!");
			return;
		}
		//
		// if (AboutFragment.ABOUT_URI.equals(uri)) {
		// tag = AboutFragment.TAG;
		// final Fragment foundFragment = fm.findFragmentByTag(tag);
		// if (foundFragment != null) {
		// fragment = foundFragment;
		// } else {
		// fragment = new AboutFragment();
		// }
		// } else if (SandboxFragment.SETTINGS_URI.equals(uri)) {
		// tag = SandboxFragment.TAG;
		// final SandboxFragment foundFragment = (SandboxFragment)
		// fm.findFragmentByTag(tag);
		// if (foundFragment != null) {
		// foundFragment.setOnSettingsChangedListener(mSettingsChangedListener);
		// fragment = foundFragment;
		// } else {
		// final SandboxFragment settingsFragment = new SandboxFragment();
		// settingsFragment.setOnSettingsChangedListener(mSettingsChangedListener);
		// fragment = settingsFragment;
		// }
		// } else if (uri != null) {
		// tag = WebViewFragment.TAG;
		// final WebViewFragment webViewFragment;
		// final Fragment foundFragment = fm.findFragmentByTag(tag);
		// if (foundFragment != null) {
		// fragment = foundFragment;
		// webViewFragment = (WebViewFragment) fragment;
		// } else {
		// webViewFragment = new WebViewFragment();
		// fragment = webViewFragment;
		// }
		// webViewFragment.setUrl(uri.toString());
		// } else {
		// return;
		// }
		//
		if (fragment.isAdded()) {
			tr.show(fragment);
			if (DEBUG)
				Log.d(TAG, "Fragment update: URI=" + uri
						+ " \n\tFragment will be shown");
		} else {
			if (DEBUG)
				Log.d(TAG, "Fragment update: URI=" + uri
						+ "\n\t Fragment will be added");
			fragment.setArguments(args);
			tr.add(R.id.content, fragment, tag);
		}

		if (uri.compareTo(currentUri) != 0 || fm.getBackStackEntryCount() == 0)
			// if the current uri is not recalled or any uri is never called
			tr.addToBackStack(tag);
		tr.commit();

		currentUri = uri;
		currentContentFragmentTag = tag;
	}

	@Override
	public void onItemClick(AdapterView<?> adView, View target, int position,
			long id) {
		if (DEBUG)
			Log.v(TAG, "MenuClick : " + target.getTag() + ". Position = "
					+ position + ". Id = " + id + ": result = "
					+ mMenuItemTypes[position]);
		nav.closeLeftSide();
		MainActivityII.updateContent(Uri.parse(mMenuItemTypes[position]));
	}

	private Uri fragmentTagToUri(String tag) {
		if (tag == BrowseBooks.TAG) {
			return BrowseBooks.URI;
		} else if (tag == NewBooks.TAG) {
			return NewBooks.URI;
		} else if (tag == TopRatingBooks.TAG) {
			return TopRatingBooks.URI;
		} else if (tag == BookDetail.TAG) {
			return BookDetail.URI;
		}
		/*
		 * else if(tag == NewBooks.TAG){ return NewBooks.URI; }
		 */

		return null;
	}

	public static FragmentManager getMyFragmentManager() {
		return FragmentManager;
	}
}
