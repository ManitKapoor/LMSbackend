package com.zohocorp.manitkapoor;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

public class DBDriver {
	
	Connection connection;
	public DBDriver() throws Exception {
		Class.forName("org.postgresql.Driver");
		connection = DriverManager.getConnection(
		   "jdbc:postgresql://localhost:5432/libsystem","postgres", "pass");
	}
	
	public String getBooks(String name,String aut,String ed) throws Exception
	{
		PreparedStatement pr = connection.prepareStatement("select * from lbooks where title like ? and author like ? and edition like ?;");
		pr.setString(1,name+"%");
		pr.setString(2,aut+"%");
		pr.setString(3,ed+"%");
		
		String ans = "[";
		ResultSet rs = pr.executeQuery();
		
		boolean f = true;
		while(rs.next())
		{	
			if(f)
		      { ans += "{ "; f=false; }
			else
			  	ans += " ,{ ";
			ans += " \"type\": \"book\", ";
			ans += " \"id\": \"" +rs.getString("id") +"\",";
			ans += " \"title\": \"" +rs.getString("title") +"\",";
			ans += " \"author\": \"" +rs.getString("author") +"\",";
			ans += " \"edition\": \"" +rs.getString("edition") +"\",";
			ans += " \"shelfno\": \"" +rs.getString("shelfno") +"\",";
			if(rs.getString("issued") != null)
			  ans += " \"issued\": \"" +rs.getString("issued") +"\"";
			else
				ans += " \"issued\": \"" +"Free" +"\"";	 
			ans += " } ";
		}
		ans += "]";
		
		return ans;
	}

	public String getUserBooks(String name) throws Exception
	{
		PreparedStatement pr = connection.prepareStatement("select * from lbooks where issued = ?;");
		pr.setString(1,name);
		String ans = "[";
		
		ResultSet rs = pr.executeQuery();
		
		boolean f = true;
		while(rs.next())
		{	
			PreparedStatement pr2 = connection.prepareStatement("select * from transaction where bookid = ?;");
			pr2.setInt(1,rs.getInt("id"));
			ResultSet rs2 = pr2.executeQuery();
			rs2.next();
			if(f)
		      { ans += "{ "; f=false; }
			else
			  	ans += " ,{ ";
			ans += " \"type\": \"book\", ";
			ans += " \"id\": \"" +rs.getString("id") +"\",";
			ans += " \"title\": \"" +rs.getString("title") +"\",";
			ans += " \"author\": \"" +rs.getString("author") +"\",";
			ans += " \"edition\": \"" +rs.getString("edition") +"\",";
			ans += " \"shelfno\": \"" +rs.getString("shelfno") +"\",";
			ans += " \"returndate\": \"" +rs2.getString("returndate") +"\",";
			ans += " \"issueddate\": \"" +rs2.getString("issuedate") +"\",";
			if(rs.getString("issued") != null)
				  ans += " \"issued\": \"" +rs.getString("issued") +"\"";
				else
					ans += " \"issued\": \"" +"Free" +"\"";	
			ans += "} ";
		}
		ans += "]";
		
		return ans;
	}
	
	public String getUser() throws Exception
	{
		PreparedStatement pr = connection.prepareStatement("select * from luser_roles");
		String ans = "[";
		ResultSet rs = pr.executeQuery();
		boolean f = true;
		int j = 1;
		
		while(rs.next())
		{
			if(f)
		      { ans += "{ "; f=false; }
			else
			  	ans += " ,{ ";
			ans += " \"id\": \"" +String.valueOf(j++) +"\",";
			ans += " \"username\": \"" +rs.getString("user_name") +"\",";
			ans += " \"role\": \"" +rs.getString("user_role") +"\" ";
			ans += "} ";
		}
		
		ans += "]";
		return ans;
	}
	
	public String addBook(String title,String author,String edition,String shelfno) throws Exception
	{
		String ans = "Book Added!";
		
		String maxid = "SELECT id FROM lbooks ORDER BY id DESC LIMIT 1";
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(maxid);
		int max_id = 1000;
		if(rs.next())
			max_id = rs.getInt("id")+1;
		
		PreparedStatement pr = connection.prepareStatement("insert into lbooks values(?,?,?,?,?,?)");
		pr.setString(1,title);
		pr.setString(2,author);
		pr.setString(3,edition);
		pr.setString(4,null);
		pr.setInt(5,max_id);
		pr.setString(6,shelfno);
		
		pr.executeUpdate();
		
		return ans;
	}
	
	public String addUser(String name,String password,String role) throws Exception
	{
		String ans = "User Added!";
		
		PreparedStatement pr = connection.prepareStatement("insert into luser values(?,?)");
		pr.setString(1,name);
		pr.setString(2,Jencryption.Encrypt(password));
		pr.executeUpdate();
		
		PreparedStatement pr2 = connection.prepareStatement("insert into luser_roles values(?,?)");
		pr2.setString(1,name);
		pr2.setString(2,role);
		pr2.executeUpdate();
		
		return ans;
	}
	
	public String IssueBook(String name,String title) throws Exception
	{
		String ans = "Book Issued!";
		
		PreparedStatement st = connection.prepareStatement("select * from lbooks where title = ?;");
		st.setString(1,title);
		ResultSet rs = st.executeQuery();
		
		if(rs.next())
		{
			if(rs.getString("issued") != null)
				return "Book Already Issued to "+rs.getString("issued");
		}
		else
		{
			return "No such book";
		}
		
		st = connection.prepareStatement("update lbooks set issued = ? where title = ?;");
		st.setString(1,name);
		st.setString(2,title);
		st.executeUpdate();
		
		Calendar calendar = Calendar.getInstance();
		
		String tt = String.valueOf(calendar.get(Calendar.YEAR))+"-"+
				String.valueOf(calendar.get(Calendar.MONTH)+1)+"-"+
				String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		
		Date today = Date.valueOf(tt);
		
		 calendar.add(Calendar.DATE, 14);
		 
		 tt = String.valueOf(calendar.get(Calendar.YEAR))+"-"+
					String.valueOf(calendar.get(Calendar.MONTH)+1)+"-"+
					String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

		 Date returnd = Date.valueOf(tt);
		 
		st = connection.prepareStatement("insert into transaction values(?,?,?,?);");
		st.setInt(1,rs.getInt("id"));
		st.setDate(2,today);
		st.setDate(3,returnd);
		st.setString(4,name);
		st.executeUpdate();
		
		return ans;
	}
	
	public String RenewBook(String name,String title) throws Exception
	{
		String ans = "Renewed charge: ";
		long charge = 0;
		
		PreparedStatement st = connection.prepareStatement("select * from lbooks where title = ?;");
		st.setString(1,title);
		ResultSet rs = st.executeQuery();
		int iid;
		
		if(rs.next())
		{
			if(rs.getString("issued") == null || rs.getString("issued") == "")
				return "Book is not issued to anyone";
			
			System.out.println("issued -> "+rs.getString("issued"));
			
			if(!rs.getString("issued").equals(name))
				return "Book Already Issued to "+rs.getString("issued");
			iid = rs.getInt("id");
		}
		else
		{
			return "No such book";
		}
		
		st = connection.prepareStatement("select * from transaction where user_name = ? and bookid = ?;");
		st.setString(1,name);
		st.setInt(2,iid);
		rs = st.executeQuery();

		rs.next();
		Date returndate = rs.getDate("returndate");
		
		st = connection.prepareStatement("delete from transaction where user_name = ? and bookid = ?;");
		st.setString(1,name);
		st.setInt(2,iid);
		st.executeUpdate();
		
		Calendar calendar = Calendar.getInstance();
		
		String tt = String.valueOf(calendar.get(Calendar.YEAR))+"-"+
				String.valueOf(calendar.get(Calendar.MONTH)+1)+"-"+
				String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		
		Date today = Date.valueOf(tt);
		
		if(returndate.before(today))
			charge = 1000;
		
		ans += "$"+String.valueOf(charge);
		
		st = connection.prepareStatement("update lbooks set issued = null where title = ?;");
		st.setString(1,title);
		st.executeUpdate();
		
		return ans;
	}
}
