package com.testbuild;

import android.annotation.*;
import android.app.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;

import com.libraryclient.*;
import com.libraryclient.connection.*;
import com.testbuild.contenthandlers.*;
import java.io.*;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends Activity {

	private Button mainBut;

	private int[] mainScrLocation = new int[4];

	private float yDiff;// aide

	class MyMenuListAdapter extends BaseExpandableListAdapter {

		private Context mContext;
		private LayoutInflater mInflater;
		private int iv[][];
		private int iv_id[][];
		private String tv[][];

		private int tv_id[][];
		private int iv_group[];
		private String tv_group[];
		private int mExpandedGroup = 0;

		private String mTag = "MenuList view detection";
		private int[] tv_group_id = { R.id.menu_text, R.id.menu_text,
				R.id.menu_text, R.id.menu_text, };
		private int[] iv_group_id = { R.id.menu_ico, R.id.menu_ico,
				R.id.menu_ico, R.id.menu_ico, };

		private ExpandableListView list;

		MyMenuListAdapter(Context con, int[] groupIconRid, String[] groupText,
				int[][] childIcoRid, String[][] childText) {
			mContext = con;
			iv_group = groupIconRid;
			tv_group = groupText;
			iv = childIcoRid;
			tv = childText;
			iv_id = new int[iv.length][];
			for (int i = 0; i < iv.length; i++) {
				iv_id[i] = new int[iv[i].length];
				for (int j = 0; j < iv[i].length; j++)
					iv_id[i][j] = R.id.menu_ico;
			}
			tv_id = new int[tv.length][];
			for (int i = 0; i < tv.length; i++) {
				tv_id[i] = new int[tv[i].length];
				for (int j = 0; j < tv[i].length; j++)
					tv_id[i][j] = R.id.menu_text;
			}
			mInflater = (LayoutInflater) con
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return iv[groupPosition][childPosition];
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return tv[groupPosition].length;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			Log.i(mTag, "ChildView detected:groupvPosi" + groupPosition
					+ ":childPosi" + childPosition);
			if (convertView == null)
				convertView = newChildView(parent);
			bindChildViewData(convertView, groupPosition, childPosition);
			return convertView;
		}

		private void bindChildViewData(View view, int groupPos, int childPos) {
			ImageView IV = (ImageView) view
					.findViewById(iv_id[groupPos][childPos]);
			TextView TV = (TextView) view
					.findViewById(tv_id[groupPos][childPos]);
			IV.setImageResource(iv[groupPos][childPos]);
			TV.setText(tv[groupPos][childPos]);
			TV.setGravity(Gravity.CENTER_VERTICAL);
		}

		private View newChildView(ViewGroup parent) {
			View v = mInflater.inflate(R.layout.menu_list_child_item, parent,
					false);
			// v.setLayoutParams(new
			// AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,33));
			return v;
		}

		@Override
		public Object getGroup(int groupPosition) {

			return iv_group[groupPosition];
		}

		@Override
		public int getGroupCount() {
			return tv_group.length;
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			Log.i(mTag, "GroupView detected:Posi" + groupPosition
					+ ":isExpanded" + isExpanded);
			if (convertView == null)
				convertView = newGroupView(parent);
			;
			if (mExpandedGroup == groupPosition)
				;
			bindGroupViewData(convertView, groupPosition);
			return convertView;
		}

		private void bindGroupViewData(View view, int groupPos) {
			ImageView IV = (ImageView) view.findViewById(iv_group_id[groupPos]);
			TextView TV = (TextView) view.findViewById(tv_group_id[groupPos]);
			IV.setImageResource(iv_group[groupPos]);
			TV.setText(tv_group[groupPos]);
			// TV.setLayoutParams(new
			// AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
			// ViewGroup.LayoutParams.WRAP_CONTENT));
		}

		private View newGroupView(ViewGroup parent) {
			View v = mInflater.inflate(R.layout.menu_list_group_item, parent,
					false);
			v.setLayoutParams(new AbsListView.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT, 66));
			return v;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

		@Override
		public void onGroupCollapsed(int groupPosition) {
			list.expandGroup(groupPosition);
		}

		public void setExpandedGroup(int groupIndex) {
			mExpandedGroup = groupIndex;
		}

		public void setListView(ExpandableListView v) {
			this.list = v;
		}
	}

	private static final String LOG = "main activity";

	private int[] groupIconId;
	private String[] groupText;
	private int[][] childIconId;
	private String[][] childText;

	private Client cl;

	private Request r;

	@SuppressLint("WrongViewCast")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		// TextView tx2=(TextView) findViewById(R.id.textView2);
		// tx2.setTypeface(Typeface.createFromAsset(getAssets(),
		// "mymyanmar5.ttf"));
		// tx2.invalidate();
		ExpandableListView exv = (ExpandableListView) findViewById(R.id.menu_list);
		loadMenu();
		MyMenuListAdapter adapter = new MyMenuListAdapter(
				getApplicationContext(), groupIconId, groupText, childIconId,
				childText);
		exv.setAdapter(adapter);
		adapter.setListView(exv);
		exv.invalidate();

		mainLay = (LinearLayout) findViewById(R.id.main_layout);
		menuLay = (LinearLayout) findViewById(R.id.menu_layout);
		mainBut = (Button) findViewById(R.id.main_but);
		mainLay.setLeft(-250);
		exv.setChildIndicator(null);
		exv.setGroupIndicator(null);
		mainLay.invalidate();

		prepareToPageLoad();
		loadPage();
	}

	private void prepareToPageLoad() {
		// TODO Auto-generated method stub

				Toast.makeText(getApplicationContext(),"Started to prepare load page",Toast.LENGTH_LONG).show();
		cl = new Client();
		com.libraryclient.config.Connection.CONNECTION_WEBSITE=
		"http://localhost:8080/";
		r = new BasicRequest(1, "");
		cl.setContentHandler(1, new ContentHandlerLogcat());
	}

	private void loadPage() {
		// TODO Auto-generated method stub

		try {
			// The newInstance() call is a work around for some
			// broken Java implementations
			Class.forName("com.libraryclient.Client").newInstance();
		} catch (Exception ex) {
			// handle the error
			Log.e(LOG, "com.libraryclient.Client() class not found!");
		}
		final Dialog dia = new Dialog(getApplicationContext());
		class LoadPagesTask extends AsyncTask<Request, Integer, Boolean> {
			protected Boolean doInBackground(Request... requests) {
				// android.os.Debug.waitForDebugger();
				int count = requests.length;
				boolean finish = false;
				// TODO support multiple simultaneous requests or not
				for (int i = 0; i < count; i++) {
					try {
						cl.request(requests[i]);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try
					{
						cl.loadContent(requests[i]);
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
					Log.d("ttttt", "eeeeeeee");
					publishProgress((int) ((i / (float) count) * 100));
					// Escape early if cancel() is called
					if (isCancelled())
						break;
				}
				return finish;
			}

			protected void onProgressUpdate(Integer... progress) {
				dia.setTitle(progress[0].toString());
			}

			protected void onPostExecute(Boolean result) {
				Toast.makeText(getApplicationContext(),
						"Downloaded " + result + " bytes", Toast.LENGTH_LONG).show();
			}
		}
		new LoadPagesTask().execute(r);

	}

	private void loadMenu() {

		Context mContext = this.getApplicationContext();
		Resources mResources = mContext.getResources();
		int[][] childIconId = {
				{ R.drawable.ic_menu_child, R.drawable.ic_menu_child,
						R.drawable.ic_menu_child, R.drawable.ic_menu_child },
				{ R.drawable.ic_menu_child, R.drawable.ic_menu_child,
						R.drawable.ic_menu_child },
				{ R.drawable.ic_menu_child, R.drawable.ic_menu_child },
				{ R.drawable.ic_menu_child, R.drawable.ic_menu_child,
						R.drawable.ic_menu_child, R.drawable.ic_menu_child,
						R.drawable.ic_menu_child, R.drawable.ic_menu_child,
						R.drawable.ic_menu_child, R.drawable.ic_menu_child,
						R.drawable.ic_menu_child } };
		String[][] childText = {
				mResources.getStringArray(R.array.menu_group_library),
				mResources.getStringArray(R.array.menu_group_dashboard),
				mResources.getStringArray(R.array.menu_group_activities),
				mResources.getStringArray(R.array.menu_group_myplace) };
		int[] groupIconId = { R.drawable.ic_menu_group, R.drawable.ic_menu_group, R.drawable.ic_menu_group,
				R.drawable.ic_menu_group };
		String[] groupText = mResources.getStringArray(R.array.menu_group);
		this.childIconId = childIconId;
		this.childText = childText;
		this.groupIconId = groupIconId;
		this.groupText = groupText;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	float x, y, xDiff = 0;
	private final float Touch_threshold = 1;
	private final float Touch_menu_draw_threshold = 30;
	private final float Touch_menu_open_threshold = 45;
	private LinearLayout mainLay;
	private LinearLayout menuLay;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		int i = event.getAction();
		menuLay.getLocationOnScreen(mainScrLocation);
		Rect r = new Rect();
		mainLay.getDrawingRect(r);
		mainLay.getHitRect(r);
		mainLay.getFocusedRect(r);
		switch (i) {
		case MotionEvent.ACTION_DOWN:
			x = event.getX();
			y = event.getY();
			xDiff = 0;
			yDiff = 0;

			break;
		case MotionEvent.ACTION_MOVE:
			defineGesture(event);
			performGesture(event);
			break;
		case MotionEvent.ACTION_POINTER_UP:
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			Log.e(LOG, "touch up");
			performGesture(event);
		default:
			break;
		}
		mainBut.setText(xDiff + "#\n" + mainScrLocation[0] + "#"
				+ mainScrLocation[1] + "\n" + r.right + "," + r.bottom);
		return super.onTouchEvent(event);
	}

	private void performGesture(MotionEvent event) {
		// TODO: Implement this method
		switch (CurrentGesture) {
		case Gestures.MenuDraw:
			if (event.getAction() == MotionEvent.ACTION_MOVE) {
				xDiff = (event.getX() - x);
				// before the dragging has not overcome the threshold
				if (Touch_menu_draw_threshold > Math.abs(xDiff)) {
					break;
				}
				// after overcome
				float tempX = 0;
				float tempY = 0;

				// if(xDiff>0)
				// tempX=event.getX() - x;
				// else
				tempX = (menuLay.getWidth() + mainScrLocation[0])
						+ (event.getX() - x);
				refreshMainLayout(tempX, 0);
			} else {

				// check if the gesture is valid for menu open
				// touching outside the menu layout will close the menu
				if (x == event.getX() && x > menuLay.getRight())
					refreshMainLayout(0, 0);
				// the rigidity of the menu is defined below
				float tempX = 0;
				float tempY = 0;
				xDiff = (event.getX() - x);
				if (xDiff > 0)
					if (Touch_menu_open_threshold < Math.abs(xDiff))
						tempX = menuLay.getWidth();
					else
						tempX = 0;

				if (xDiff < 0)
					if (Touch_menu_open_threshold < Math.abs(xDiff)) {
						Log.d(LOG, "touch up tempx:" + tempX);

						tempX = 0;
					} else
						tempX = menuLay.getWidth();
				refreshMainLayout(tempX, tempY);
			}
		}
	}

	int CurrentGesture = 0;

	private void defineGesture(MotionEvent event) {
		// TODO: Implement this method such that the object the first touch
		// point lie on define the gesture type

		// single plain touch along the Y axis;
		xDiff = x - event.getX();
		yDiff = y - event.getY();

		if (yDiff < xDiff)
			CurrentGesture = Gestures.MenuDraw;

	}

	class Gestures {
		public static final int MenuDraw = 1;
	}

	/**
	 * @param x
	 *            the left point of the new origin for main layout
	 * @param y
	 *            the top point of the new origin for main layout
	 */
	private void refreshMainLayout(float x, float y) {
		// TODO to implement codes for y origin
		Log.i(LOG, "Menu origin:" + x);
		int menuWidth = menuLay.getWidth();
		int temp = (int) (x - menuWidth);
		if (temp + menuWidth < 0)
			temp = 0 - menuWidth;
		if (temp > 0)
			temp = 0;

		// animation showing menu slide
		int i = mainLay.getLeft();
		while (i < temp) {
			i += ((temp - i) / 2) + 5;
			mainLay.setLeft(i);
			mainLay.invalidate();
			try {
				int sleeptime = (Math.abs(temp - i) < 50) ? 1 : 15;
				Thread.sleep(sleeptime);
			} catch (InterruptedException e) {
			}
		}
		while (i > temp) {
			i -= (i - temp) / 2 + 2;
			mainLay.setLeft(i);
			mainLay.invalidate();
			try {
				int sleeptime = (Math.abs(temp - i) < 50) ? 1 : 15;
				Thread.sleep(sleeptime);
			} catch (InterruptedException e) {
			}
		}
		mainLay.setLeft(temp);
		mainLay.invalidate();
		mainLay.forceLayout();
	}

}