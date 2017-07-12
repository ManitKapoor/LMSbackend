package com.zohocorp.manitkapoor;

import java.io.InputStream;
import java.io.StringBufferInputStream;

public class BookUserAction {
   private InputStream inputStream;
   private String username;
public InputStream getInputStream() {
	return inputStream;
}

public String execute() throws Exception
{
	if(username == null)
		username = "";
	DBDriver db = new DBDriver();
	String ans = db.getUserBooks(username);
	inputStream = new StringBufferInputStream(ans);
	return "success";
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}


}
