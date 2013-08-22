package com.libraryclientforandroid;

import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.libraryclient.config.*;
import com.libraryclient.connection.*;
import com.libraryclient.content.*;
import com.libraryclient.content.handlers.*;
import com.libraryclient.content.items.*;
import java.io.*;

public class MainActivity extends Activity {
	static int counter = 0;

	private Button b1;

	private TextView tv1;

	private EditText et1;

	private EditText et2;

	private EditText et4;

	private ConnectCodeSpecificItemHandler h;

	private EditText et3;

	/** Called when the activity is first created. */

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		tv1 = (TextView) findViewById(R.id.main_tv1);
		et1 = (EditText) findViewById(R.id.main_et1);
		b1 = (Button) findViewById(R.id.main_but1);
		et2 = (EditText) findViewById(R.id.main_et2);
		et3 = (EditText) findViewById(R.id.main_et3);
		et4 = (EditText) findViewById(R.id.main_et4);

		et1.setText("1");
		et2.setText("");
		et3.setText("/sdcard/");
		et4.setText("http://127.0.0.1/mylibraryapp/index.php");

		/**** prepare connection client ******/
		Connection.USERNAME = "username";
		Connection.PASSWORD = "password";
		final BaseApi api = new BaseApi();
		
		/***** create content handler *****/
		h = new ConnectCodeSpecificItemHandler<BookInfoGeneral>(
				new com.libraryclient.content.handlers.OnItemLoadListener<BookInfoGeneral>() {
					int itemCount = 0;
					long startTime = 0;
					long endTime = 0;

					public void onItemLoaded(Connector c, final BookInfoGeneral i) {
						runOnUiThread(new Runnable() {

							public void run() {
								tv1.append("-|-");
								itemCount++;
							}

						});
					}

					public void onStartLoading(Connector c) {
						runOnUiThread(new Runnable() {

							public void run() {
								itemCount = 0;
								startTime = System.currentTimeMillis();
								tv1.append("\n<<START>>\nLoading...");
							}

						});
					}

					public void onFinishLoading(Connector c) {
						runOnUiThread(new Runnable() {

							public void run() {
								endTime = System.currentTimeMillis();
								int c = h.getItemCount();
								Item i = (c > 0) ? h.getLoadedItem(c - 1)
										: null;//new Item();
								tv1.append("\nItem COUNT="
									//	+ i.getSubItemCount() + "/" + itemCount
										+ " Duration=" + (endTime - startTime)
										+ "\n<<-START->>\n" + i + "\n<-Item END->\n");
								tv1.append("\n<<-FINISH->>\n");
								b1.setText("Left:"
										+ BaseApi
												.getCurrentWorkingRequestsCount());
							}

						});
					}

				});
		
		/**** triggers connection *****/
		b1.setOnClickListener(new OnClickListener() {

			public void onClick(View p1) {
				b1.setText("Request:" + (counter++));
				Connection.CONNECTION_WEBSITE = et4.getText().toString();
				Connector c = new BasicRequest(Integer.valueOf(et1.getText()
						.toString()), et2.getText().toString());
				if (c instanceof Request) {
					api.request((Request) c, h);
				} else if (c instanceof Post) {
					((Post) c).addPostFile("0", new File(et3.getText()
							.toString()));
					api.post((Post) c, h);
				}
				// if (h.getItemCount() > 0)
				// tv1.setText(
				// h.getLoadedItem(
				// (h.getItemCount() > (++counter)) ?counter: (counter = 0)
				// ).toString()
				// );
			}

		});
	}

}
