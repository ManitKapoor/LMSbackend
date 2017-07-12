package com.zohocorp.manitkapoor;

import java.io.InputStream;
import java.io.StringBufferInputStream;

public class BookAction {
   private InputStream inputStream;
   private String title, author, edition;
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
public String execute() throws Exception
{
	if(title == null)
		title = "";
	if(author == null)
		author = "";
	if(edition == null)
		 edition = "";
	DBDriver db = new DBDriver();
	String ans = db.getBooks(title, author, edition);
	inputStream = new StringBufferInputStream(ans);
	return "success";
}
}
