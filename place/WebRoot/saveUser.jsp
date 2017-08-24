<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'home.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <form action="http://127.0.0.1:8080/placeserver/restful/saveUser" method="post">  
    uid:<input type="text" name="uid" />  <p> 
            用户名:<input type="text" name="user_name"/> <p>  
    password:<input type="text" name="password" value="8Ogr9X/Cmvy3J6CV2V97WA=="/>  <p> 
    user_type:<input type="text" name="user_type"/> <p>
    user_id:<input type="text" name="user_id" />  <p> 
    mobile:<input type="text" name="mobile"/> <p>
    salt:<input type="text" name="salt" />  <p> 
    card_type:<input type="text" name="card_type"/> <p>
    id:<input type="text" name="id" />  <p> 
    digest:<input type="text" name="digest"/> <p>
    
    <input type="submit" value="submit" />  
</form>  
  </body>
</html>
