<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.io.*,java.util.*,java.sql.*" %>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ page import="mysqlConnector.DbConnector,mysqlConnector.generalDBAPI" %>
<!DOCTYPE html>  
<html>  


<head>  
	<link rel="stylesheet" type="text/css" href="loginstyle.css"/>
    <meta charset="UTF-8" />  
    <title>小视管理平台</title>  
</head>  

<body>

<div id="header">  
    <div id="logo"></div>  
    <div id="heading">  
        <div id="title">小视管理平台</div>  
        <div id="subTitle">Ver 1.0</div>  
    </div>  
</div>  
<div id="main">  
    <div id="mainBg">  
        <div id="mainPanel">  
            
			 <div id="center">
				<div id="welcome">  
					<span id="welcome-text">登录界面</span>  
				</div> 
				
					<form action="index.jsp?submit=1" method="POST">
                    <div id="user-name">  
                        <span class="item">用户名:</span>  
                        <span><input type="text" name="username" class="form-input"></span>  
                    </div>  
                    <div id="user-password">  
                        <span class="item">密&nbsp;码:</span>  
                        <span class="input"><input type="password" name="password" class="form-input"></span>  
                    </div> 
					<div id="user-type"> 
					<label><input name="type" type="radio" value="1" checked = true />管理员登陆 </label> 
					<label><input name="type" type="radio" value="2" />客户登录 </label>
					</div> 					
					<%
					if(request.getParameter("submit")!=null && request.getParameter("submit").equals("1")){			   	
					   if(request.getParameter("username").equals("")|| request.getParameter("password").equals(""))
						   out.print("<div id='error-tip'><span id='tip-text'>用户或密码不能为空！</span></div>");
					   else {
						   if(request.getParameter("type").equals("2")) {
							   mysqlConnector.DbConnector con = new mysqlConnector.DbConnector();
								Common.CustomerInfo user = con.getCustomerInfo(request.getParameter("username")); 
								if (user==null) { 
								out.print("<div id='error-tip'><span id='tip-text'>用户不存在！</span></div>");
								} else if ( !user.customer_password.equals(request.getParameter("password"))){ 
								out.print("<div id='error-tip'><span id='tip-text'>密码不正确！</span></div>");
								} else { 
								session.setAttribute("name",request.getParameter("username")); 
								session.setAttribute("rank","0"); 
								response.sendRedirect("system.jsp?action_type=1"); 
								}
					       }
						   else {							   
							   mysqlConnector.DbConnector con = new mysqlConnector.DbConnector();
								Common.UserInfo user = con.getUserInfo(request.getParameter("username")); 
								if (user==null) { 
								out.print("<div id='error-tip'><span id='tip-text'>用户不存在！</span></div>");
								} else if ( !user.user_password.equals(request.getParameter("password"))){ 
								out.print("<div id='error-tip'><span id='tip-text'>密码不正确！</span></div>");
								} else { 
								session.setAttribute("name",request.getParameter("username"));
								session.setAttribute("id",user.user_id.toString()); 								
								session.setAttribute("rank",user.user_type.toString()); 
								response.sendRedirect("system.jsp?action_type=1"); 
								}
						   }
					   }
					} 
					%>
                    <div id="button-group">  
                        <input type="submit" class="btn" value="登录"/>  
                        <input type="reset" class="btn" value="重置"/>  
                    </div>  
					</form>
				</div>
 
        </div>  
    </div>  
</div>  
<div id="footer">  
    <div id="text">Copyright © 2015-2016 MiniVision. All rights reserved.</div>
</div>
</body>
</html>