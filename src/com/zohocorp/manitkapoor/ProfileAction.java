package com.zohocorp.manitkapoor;

import java.io.InputStream;
import java.io.StringBufferInputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

public class ProfileAction {
	   private InputStream inputStream;
	   private String name, role;
	public InputStream getInputStream() {
		return inputStream;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String execute()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		if(request.getRemoteUser() == null)
		{	 
			inputStream = new StringBufferInputStream("{ \"name\" : \"guest\" }");
			return "success";
		}
		String role = "";
		if(request.isUserInRole("ADMIN"))
			role = "ADMIN";
		if(request.isUserInRole("USER"))
			role = "USER";
		if(request.isUserInRole("MANAGER"))
			role = "MANAGER";
		inputStream = new StringBufferInputStream("{ \"name\": \""+ request.getRemoteUser()  +"\", \"role\": \""+role+"\" }");
		return "success";
	}
	
}
