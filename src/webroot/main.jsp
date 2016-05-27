<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.io.*,java.util.*,java.sql.*" %>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ page import="mysqlConnector.DbConnector,mysqlConnector.generalDBAPI" %>
<!DOCTYPE html>  
<html>  

<head>    
</head>  
<body>  
<p>shabi</p>

<div>

	<% if(request.getParameter("action_type")==null) { %>
	<span class="top">hahaha</span> 
	<div>
		<span class="item">hahaha</span>
	</div>
	<% } else if(request.getParameter("action_type").equals("1")) {%>
	<span class="top">HomePage</span> 
	<div>
		<span class="item">Information</span>
	</div>	
	<% } else if(request.getParameter("action_type").equals("2")) {%>
	<span class="top">Manage</span> 
	<div>
		<span class="item">Information</span>
	</div>
	
	<% } else if(request.getParameter("action_type").equals("3")) {%>
	<span class="top">Service</span> 
	<div>
		<span class="item">Information</span>
	</div>
	
	<% } %>
</div>
	
</body>

</html>