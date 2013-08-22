package com.libraryclient.connection;

import android.util.Base64;

public class BasicAuthentication implements Authentication
{
	private String username;
	private String password;

	public BasicAuthentication(String username, String password)
	{
		this.username = username;
		this.password = password;
	}

	@Override
	public void authenticate(Connector c)
	{
		String auth = "Basic " + Base64.encodeToString((username + ":" + password).getBytes(), Base64.DEFAULT);
		c.addHeader("Authorization", auth);
	}
}
