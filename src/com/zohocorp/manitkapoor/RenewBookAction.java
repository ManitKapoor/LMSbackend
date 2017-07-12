package com.zohocorp.manitkapoor;

import java.io.InputStream;
import java.io.StringBufferInputStream;

import javax.servlet.http.HttpServletRequest;



import org.apache.struts2.ServletActionContext;

public class RenewBookAction {
	private InputStream inputStream;
	private String title, name;
	
	public InputStream getInputStream() {
		return inputStream;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String execute() throws Exception {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		if(!request.isUserInRole("MANAGER"))
		{
			inputStream = new StringBufferInputStream("{ \"result\": \"Only Manager can perform this operation\"}");
			return "success";
		}
		
		DBDriver dr = new DBDriver();
		if(name == null || title == null)
		{
			inputStream = new StringBufferInputStream("{ \"result\": \"Entered Details incorrect\"}");
			return "success";
		}
		
		inputStream = new StringBufferInputStream("{ \"result\": \""+dr.RenewBook(name,title)+"\"}");
		return "success";
		
	}
}
