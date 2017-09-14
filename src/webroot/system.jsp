<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.io.*,java.util.*,java.sql.*" %>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ page import="mysqlConnector.DbConnector,mysqlConnector.generalDBAPI" %>
<!DOCTYPE html>  
<html>  
<script>
function set_action()
{
	document.getElementById("mainframe").src="main.jsp?action_type=1";  
}
</script>
<%	
	session.setAttribute("name","admin");
	session.setAttribute("rank","3");
	session.setAttribute("id","1");	
  if(session.getAttribute("name")==null)
	  response.sendRedirect("wrong.jsp");
%>




<head>  
    <title>小视管理平台</title>  
    <link rel="stylesheet" type="text/css" href="sysstyle.css" />  
	<meta charset="UTF-8" />
</head>  
<body>  
<div id="wrapper">  
    <div id="header">  
        <div id="title">小视管理平台</div>		
        <div id="menu">  
			<div id="menu_container">  
				<ul id="menu_items"> 
					<% if(request.getParameter("action_type").equals("1")) { %>
					<li class="menu_item on" style="border-radius:8px 0 0 8px" onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#77D1F6';this.style.borderRadius='8px 0 0 8px'"><a href = "system.jsp?action_type=1">我的面板</a></li>  
					<% } else {%>
					<li class="menu_item" style="border-radius:8px 0 0 8px" onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#77D1F6';this.style.borderRadius='8px 0 0 8px'"><a href = "system.jsp?action_type=1">我的面板</a></li>  
					<% } %>
					<% if(request.getParameter("action_type").equals("2")) { %>
					<li class="menu_item on" onmouseout="this.style.backgroundColor='';this.style.fontWeight='normal'" onmouseover="this.style.backgroundColor='#77D1F6';this.style.fontWeight='bold'"><a href = "system.jsp?action_type=2">客户系统</a></li>  
					<% } else {%>
					<li class="menu_item" onmouseout="this.style.backgroundColor='';this.style.fontWeight='normal'" onmouseover="this.style.backgroundColor='#77D1F6';this.style.fontWeight='bold'"><a href = "system.jsp?action_type=2">客户系统</a></li>  
					<% } %>
					<% if(request.getParameter("action_type").equals("3")) { %>
					<li class="menu_item" onmouseout="this.style.backgroundColor='';this.style.fontWeight='normal'" onmouseover="this.style.backgroundColor='#77D1F6';this.style.fontWeight='bold'"><a href = "system.jsp?action_type=3">服务管理</a></li>   
					<% } else {%>
					<li class="menu_item" onmouseout="this.style.backgroundColor='';this.style.fontWeight='normal'" onmouseover="this.style.backgroundColor='#77D1F6';this.style.fontWeight='bold'"><a href = "system.jsp?action_type=3">服务管理</a></li>   
					<% } %>
					
					
					<li class="menu_item" style="border-radius:8px 0 0 8px;border:0px;" onmouseout="this.style.backgroundColor='';this.style.fontWeight='normal'" onmouseover="this.style.backgroundColor='#77D1F6';this.style.borderRadius='0 8px 8px 0';this.style.fontWeight='bold'"><a onclick="set_action();">系统配置</a></li> 
				</ul>  
			</div>  
        </div>  
        <div id="user_info">  
            <div id="welcome">欢迎<%= session.getAttribute("name") %>使用本系统</div>  
            <div id="logout"><a href = "logout.jsp">安全退出</a></div>  
        </div>  
    </div>
<div id="navigator">  
    <iframe name="tree" id="treeframe" src="tree.jsp?action_type=<%=request.getParameter("action_type")%>"></iframe>  
</div>  
<div id="main">  
    <iframe name="main" id="mainframe" src="main.jsp?submit=0"></iframe>  
</div>
<div id="footer">
	<div id="text">Copyright © 2015-2016 MiniVision. All rights reserved.</div>
</div>  	
</div>  

</body>  
</html> 
<script type="text/javascript">  
function screenAdapter(){  
    document.getElementById('footer').style.top=document.documentElement.scrollTop+document.documentElement.clientHeight- document.getElementById('footer').offsetHeight+"px";  
    document.getElementById('navigator').style.height=document.documentElement.clientHeight-100+"px";  
    document.getElementById('main').style.height=document.documentElement.clientHeight-100+"px";  
    document.getElementById('main').style.width=document.documentElement.clientWidth-230+"px";  
}  
  
window.onscroll=function(){screenAdapter()};  
window.onresize=function(){screenAdapter()};  
window.onload=function(){screenAdapter()};  
</script>   