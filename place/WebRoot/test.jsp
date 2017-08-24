<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>发送消息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  <script language="javascript">
function check(){   
  document.form1.action="SearchStateList.action";
}
</script>
  </head>  
  <body>
     <div align="center">
<form enctype="multipart/form-data" name="form1" action="SearchStateList" method="post">
    <table>
        <tr>
            <td><label for="txtname">消息体</label></td>
            <td><input type="text" id="msgdata" name="msgdata" value="9n6mkm1%2F2CpMODev8OdNCA%3D%3D" /></td>
        </tr>
        <tr>
            <td><label for="txtname">签名串</label></td>
            <td><input type="text" id="msgdatasign" name="msgdatasign" value="249a57a0d6e1b437ac0276ce35de4568" /></td>
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
