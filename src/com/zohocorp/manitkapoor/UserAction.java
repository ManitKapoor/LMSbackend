package com.zohocorp.manitkapoor;

import java.io.InputStream;
import java.io.StringBufferInputStream;

public class UserAction {
	private InputStream inputStream;

	public InputStream getInputStream() {
		return inputStream;
	}
   
	public String execute() throws Exception {
		DBDriver dr = new DBDriver();
		inputStream = new StringBufferInputStream(dr.getUser());
		return "success";
	}
}
