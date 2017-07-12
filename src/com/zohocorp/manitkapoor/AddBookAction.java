package com.zohocorp.manitkapoor;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

public class AddBookAction {
	private InputStream inputStream;
	private String title, author, edition, shelfno;
	

	public InputStream getInputStream() {
		return inputStream;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getEdition() {
		return edition;
	}
	public void setEdition(String edition) {
		this.edition = edition;
	}
	public String getShelfno() {
		return shelfno;
	}
	public void setShelfno(String shelfno) {
		this.shelfno = shelfno;
	}

	
	public String execute() throws Exception{
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		if(!request.isUserInRole("ADMIN"))
		{
			inputStream = new StringBufferInputStream("{ \"result\": \"Only Admin can perform this operation\" }");
			return "success";
		}
		
		DBDriver dr = new DBDriver();
		if(title == null || author == null || edition == null || shelfno == null)
		{
			inputStream = new StringBufferInputStream("{ \"result\": \"Entered Details incorrect\" }");
			return "success";
		}
		inputStream = new StringBufferInputStream("{ \"result\": \""+dr.addBook(title,author,edition,shelfno)+"\" }");
		return "success";
	}
}
