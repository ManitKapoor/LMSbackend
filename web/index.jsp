<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>ZohoLMS</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">

<meta name="libapp/config/environment" content="%7B%22modulePrefix%22%3A%22libapp%22%2C%22environment%22%3A%22production%22%2C%22rootURL%22%3A%22/%22%2C%22baseURL%22%3A%22emberapp%22%2C%22locationType%22%3A%22hash%22%2C%22EmberENV%22%3A%7B%22FEATURES%22%3A%7B%7D%2C%22EXTEND_PROTOTYPES%22%3A%7B%22Date%22%3Afalse%7D%7D%2C%22APP%22%3A%7B%22name%22%3A%22libapp%22%2C%22version%22%3A%220.0.0+03b2a87e%22%7D%2C%22exportApplicationGlobal%22%3Afalse%7D" />

    <link rel="stylesheet" href="emberapp/assets/vendor-96fb1d11dc30487d76102644e349525a.css" integrity="sha256-5KOZsHslvP5ohyItKQ4Zcu1/YohuKqkhqYSvGRjBSYE= sha512-z+qOfkana6xj/pgRTzYm7Tn2EaAwbwMGLAhu0N6xecBMyrblkpCJjyiNuJRb1W+SiHtUdgHWSTR9wUOBskOoAg==" >
    <link rel="stylesheet" href="emberapp/assets/libapp-d41d8cd98f00b204e9800998ecf8427e.css" integrity="sha256-47DEQpj8HBSa+/TImW+5JCeuQeRkm5NMpJWZG3hSuFU= sha512-z4PhNX7vuL3xVChQ1m2AB9Yg5AULVxXcg/SpIdNs6c5H0NE8XYXysP+DGNKHfuwvY7kxvUdBeoGlODJ6+SfaPg==" >

    
</head>
<body>
<%
  String isadmin, isuser, ismanager;
  if(request.isUserInRole("ADMIN"))
	  isadmin = "true";
  else
	  isadmin = "false"; 
  if(request.isUserInRole("USER"))
	  isuser = "true";
  else
	  isuser = "false";
  if(request.isUserInRole("MANAGER"))
	  ismanager = "true";
  else
	  ismanager = "false"; 
%>
<script type="text/javascript">
  window.username = "<%= request.getUserPrincipal().getName() %>";
  window.isAdmin = <%= isadmin %>;
  window.isUser = <%= isuser %>;
  window.isManager = <%= ismanager %>;
</script>
    <script src="emberapp/assets/vendor-ae35aa982c219a63e0e447d27027ced8.js" integrity="sha256-sZa7+DTJm5IANFsrSGL2j/aiBox+phceC5cuyTtqgo4= sha512-big8L7Aa1AcvVzoB3/LJlwhdVGiKZmKSTDLO3IbaHnXuhaYktW+osoRfm17y6eT5Vo6OHLIvIY8hYUhAYFxAJA==" ></script>
    <script src="emberapp/assets/libapp-34728f7b7fbd5ce0e5d2ca3d28b188bc.js" integrity="sha256-Vp+AF0LMTYhQmiIx9WfvYYBCxTSUHZGkf8PTkImGXbo= sha512-zNLxbVBlcl8+T93sP4kT5m26sP7anCPbdDItJ78Vps+mYtwfwMQtdykHACU2jCWHX+DwLUg7xbrryY2AgPO/hg==" ></script>


    <div id="ember-bootstrap-wormhole"></div>
</body>
</html>