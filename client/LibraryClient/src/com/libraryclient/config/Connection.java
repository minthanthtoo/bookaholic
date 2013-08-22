package com.libraryclient.config;

import java.net.Proxy;

public class Connection {
	/**
	 * AYT-8
	 */
	//public static String CONNECTION_WEBSITE  = "http://192.168.42.110/MyLibraryapp/index.php";
	//public static String CONNECTION_WEBSITE  = "http://192.168.43.229/MyLibraryapp/index.php";
	public static String CONNECTION_WEBSITE  = "http://ayflumm.zxq.net/MyLibraryApp/index.php";
	public static final String REQUEST_CODE_HEADER="rcode";
	public static final String REQUEST_DATA_HEADER="rdata";
	public static final String CONNECT_USER_HEADER="user";
	public static final String CONNECT_PASSWORD_HEADER="pass";
	public static final String POST_CODE_HEADER="pcode";
	public static final String POST_DATA_HEADER="pdata";// TODO
	public static String USERNAME = "username";
	public static String PASSWORD = "password";
	public static Proxy PROXY=Proxy.NO_PROXY;
	
	public static String CACHE_DIR="";
	public static int BUFFER_SIZE=1024;
	

}