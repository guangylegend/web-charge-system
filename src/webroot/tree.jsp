<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.io.*,java.util.*,java.sql.*" %>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ page import="mysqlConnector.DbConnector,mysqlConnector.generalDBAPI" %>
<!DOCTYPE html>  
<html>  
<link rel="stylesheet" type="text/css" href="treestyle.css" />

<head>    
</head>  
<body>  
<div>

	<% if(request.getParameter("action_type")==null) { %>
	<% } else if(request.getParameter("action_type").equals("1")) {%>
	<div id="vertmenu">
	<h1>我的面板</h1> 
	<ul>
		<li><a onclick="parent.document.getElementById('mainframe').src='main.jsp?action_type=information';">个人信息</a></li>
	</ul>
	</div>	
	<% } else if(request.getParameter("action_type").equals("2")) {%>
	<div id="vertmenu">
	<h1>客户系统</h1> 
	<ul>
		<li><a onclick="parent.document.getElementById('mainframe').src='main.jsp?action_type=list_customer';">客户列表</a></li>
	    <li><a onclick="parent.document.getElementById('mainframe').src='main.jsp?action_type=insert_customer';">新增客户</a></li>
		<li><a onclick="parent.document.getElementById('mainframe').src='main.jsp?action_type=charge';">服务充值</a></li>
    </ul>
	</div>
	
	<% } else if(request.getParameter("action_type").equals("3")) {%>
	<div id="vertmenu">
	<h1>服务管理</h1> 
	<ul>
		<li class="item"><a onclick="parent.document.getElementById('mainframe').src='main.jsp?action_type=insert_service';">新增服务</a></li>
	</ul>
	</div>
	
	<% } %>
</div>
	
</body>

</html>