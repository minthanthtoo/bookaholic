package com.libraryclient.connection;

import android.net.*;
import java.net.*;

public class ConnectDataBuilder
	{
		private String requestData = "";

		public ConnectDataBuilder put(String name, String value)
		{
			if (name == null || value == null || name == "" || value == "")
				return this;
			requestData += "&&";
			requestData += URLEncoder.encode(name) + "=" + URLEncoder.encode(value);
			return this;
		}

		public String build()
		{
			return requestData.substring("&&".length());// strip off the first
			// "&&" string
		}
	}
