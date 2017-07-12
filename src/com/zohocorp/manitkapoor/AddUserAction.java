package com.zohocorp.manitkapoor;

import java.io.InputStream;
import java.io.StringBufferInputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

public class AddUserAction {
	
	private InputStream inputStream;
	private String name, password, role;
	

	public InputStream getInputStream() {
		return inputStream;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	

	
	public String execute() throws Exception {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		if(!request.isUserInRole("ADMIN"))
		{
			inputStream = new StringBufferInputStream("{ \"result\": \"Only Admin can perform this operation\"}");
			return "success";
		}
		
		DBDriver dr = new DBDriver();
		if(name == null || password == null || role == null)
		{
			inputStream = new StringBufferInputStream("{ \"result\": \"Entered Details incorrect\"}");
			return "success";
		}
		
		inputStream = new StringBufferInputStream("{ \"result\": \""+dr.addUser(name,password,role)+"\"}");
		return "success";
	}
	
}
