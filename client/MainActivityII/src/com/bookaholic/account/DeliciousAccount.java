package com.bookaholic.account;

import java.io.UnsupportedEncodingException;

import org.apache.http.protocol.HTTP;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

import com.bookaholic.SecureConstants;
import com.libraryclient.config.Constants;
import com.libraryclient.connection.Authentication;
import com.libraryclient.connection.BasicAuthentication;
import com.libraryclient.connection.Connector;
import com.libraryclient.utils.Crypto;

public class DeliciousAccount implements Authentication {
	private AccountManager accountManager;
	private Account account;

	private Crypto crypto;

	public static DeliciousAccount get(Context context) {
		AccountManager accountManager = AccountManager.get(context
				.getApplicationContext());
		Account accounts[] = accountManager
				.getAccountsByType(Constants.ACCOUNT_TYPE);

		if (accounts.length > 0) {
			return new DeliciousAccount(context, accountManager, accounts[0]);
		} else {
			return null;
		}
	}

	public static DeliciousAccount create(Context context, String username,
			String password) {
		AccountManager accountManager = AccountManager.get(context
				.getApplicationContext());

		Account account = new Account(username, Constants.ACCOUNT_TYPE);
		DeliciousAccount deliciousAccount = new DeliciousAccount(context,
				accountManager, account);

		accountManager.addAccountExplicitly(account,
				deliciousAccount.encryptPassword(password), null);

		return deliciousAccount;
	}

	private DeliciousAccount(Context context, AccountManager accountManager,
			Account account) {
		this.accountManager = accountManager;
		this.account = account;

		crypto = new Crypto(SecureConstants.ENCRYPTION_KEY);
	}

	public String getUsername() {
		return account.name;
	}

	public Account get() {
		return account;
	}

	private String encryptPassword(String password) {
		try {
			byte[] output = crypto.encryptAsBase64(password
					.getBytes(HTTP.UTF_8));
			return new String(output, HTTP.UTF_8);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	private String getPassword() {
		String encrypted = accountManager.getPassword(account);
		try {
			byte[] output = crypto.decryptAsBase64(encrypted
					.getBytes(HTTP.UTF_8));
			return new String(output, HTTP.UTF_8);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	@Override
	public void authenticate(Connector c) {
		getAuth().authenticate(c);
	}

	private Authentication getAuth() {
		String username = account.name;
		String password = getPassword();

		return new BasicAuthentication(username, password);
	}
}
