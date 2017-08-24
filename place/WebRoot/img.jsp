<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>上传照片</title>
    
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
     <div align="center">
    <form action="UploadImg.action" method="post" enctype="multipart/form-data">
    <table>
        <tr>
            <td><label for="txtname">消息报文</label></td>
            <td><input type="text" id="msgdata" name="msgdata" value="{}"/></td>
        </tr>        		
		<tr>
            <td><label for="txtname">图片</label></td>
            <td><input type="file" name="file" style="width:160px;" /></td>
        </tr> 		  				
        <tr>
            <td colspan=2>                
                <input type="submit" />
                <input type="reset" />
            </td>
        </tr>
    </table>
</form> 
</div>
  </body>
</html>
