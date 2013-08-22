package com.testbuild;

import java.io.IOException;

import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.libraryclient.config.Constants;
import com.libraryclient.connection.BasicAuthentication;
import com.libraryclient.connection.Request;
import com.libraryclientforandroid.DeliciousAccount;

public class LoginActivity extends AccountAuthenticatorActivity {
	private static final String TAG = "LoginActivity";

	private static final String DIALOG_TAG = "logging_in";

	private EditText usernameView;
	private EditText passwordView;

	private Drawable errorDrawable;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		usernameView = (EditText) findViewById(R.id.username);
		passwordView = (EditText) findViewById(R.id.password);

		// Enable user to press enter when done
		passwordView.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					handleLogin();

					return true;
				} else {
					return false;
				}
			}
		});

		View loginButton = findViewById(R.id.login);
		loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				handleLogin();
			}
		});

		// errorDrawable = DeliciousApplication.getErrorDrawable();

		// Do we already have an account?
		if (DeliciousAccount.get(this) != null) {
			Toast.makeText(this, R.string.toast_account_exists,
					Toast.LENGTH_SHORT).show();
			finish();
		}
	}

	private void handleLogin() {
		String username = usernameView.getText().toString().trim();
		String password = passwordView.getText().toString().trim();

		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
			// Validation
			if (TextUtils.isEmpty(username)) {
				usernameView.setError(
						getString(R.string.field_empty_error,
								getString(R.string.field_username)),
						errorDrawable);
			}

			if (TextUtils.isEmpty(password)) {
				passwordView.setError(
						getString(R.string.field_empty_error,
								getString(R.string.field_password)),
						errorDrawable);
			}
		} else {
			new LoginTask(this, username, password).execute();
		}
	}

	private void finishLogin(DeliciousAccount deliciousAccount) {
		Bundle result = new Bundle();
		result.putString(AccountManager.KEY_ACCOUNT_TYPE,
				Constants.ACCOUNT_TYPE);
		result.putString(AccountManager.KEY_ACCOUNT_NAME,
				deliciousAccount.getUsername());

		setAccountAuthenticatorResult(result);

		setResult(RESULT_OK);
		finish();
	}

	private class LoginTask extends AsyncTask<Void, Void, DeliciousAccount> {
		private Context context;

		private String username;
		private String password;

		private ProgressDialogFragment dialog;

		public LoginTask(Context context, String username, String password) {
			this.context = context;

			this.username = username;
			this.password = password;
		}

		@Override
		protected void onPreExecute() {
			dialog = ProgressDialogFragment
					.newInstance(getString(R.string.dialog_logging_in));
			dialog.show(getApplicationContext(), DIALOG_TAG);
		}

		@Override
		protected DeliciousAccount doInBackground(Void... params) {
			try {
				Request response = Request
						.addAuth(new BasicAuthentication(username, password))
						.execute();

				try {
					int code = response.getStatusCode();

					if (code == 200) {
						return createAccount();
					} else if (code == 401) {
						// Unauthorized
						Log.e(TAG, "401 Unauthorized");

						return null;
					} else {
						// Unknown response
						Log.e(TAG, "Unknown response code: " + response);

						return null;
					}
				} finally {
					response.disconnect();
				}
			} catch (IOException e) {
				Log.e(TAG, "Login failed", e);

				return null;
			}
		}

		private DeliciousAccount createAccount() {
			// We do this on here (on a separate thread) because this will cause
			// disk access
			return DeliciousAccount.create(context, username, password);
		}

		@Override
		protected void onPostExecute(DeliciousAccount account) {
			if (account != null) {
				finishLogin(account);
			} else {
				dialog.dismiss();

				Toast.makeText(LoginActivity.this, R.string.toast_login_failed,
						Toast.LENGTH_SHORT).show();
			}
		}
	}
}

class ProgressDialogFragment {

	private String mPrompt;
	private Dialog mDia;

	public ProgressDialogFragment(String prompt) {
		mPrompt = prompt;
	}

	public static ProgressDialogFragment newInstance(String prompt) {
		ProgressDialogFragment ins = new ProgressDialogFragment(prompt);
		return ins;
	}

	public void dismiss() {

	}

	public void show(Context context, String dialogTag) {
		mDia = new Dialog(context);
	}

}