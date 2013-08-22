/**
 * 
 */
package com.libraryclient.test;

import com.libraryclient.*;
import com.libraryclient.connection.*;
import com.libraryclient.content.*;
import org.junit.*;
import java.io.*;

/**
 * This class will test whether java.net.URLConnection does work.
 * 
 * @author Min Thant Htoo
 * 
 */
public class Test2 {

	@Test
	public void Connect(){
		boolean connected= new Client().connect("username", "password");
		System.out.print("CONNECTED:"+connected);
		
	}
	@Test
	public void loadWebData(){
		Client cl=new Client();
		Connector r=new BasicRequest(1,"");
		cl.setContentHandler(1, new DefaultContentHandler());
		try
		{
			cl.request(r);
		}
		catch (IOException e)
		{}
		try
		{
			cl.loadContent(r);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}
}
