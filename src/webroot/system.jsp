<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>  
<html>  

<head>  
    <title>Home Page</title>  
    <link rel="stylesheet" type="text/css" href="sysstyle.css" />  
</head>  
<body>  
<div id="wrapper">  
    <div id="header">  
        <div id="title">后台管理系统</div>  
        <div id="menu">  
			<div id="menu_container">  
				<ul id="menu_items">  
					<li class="menu_item on" style="border-radius:8px 0 0 8px" onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#77D1F6';this.style.borderRadius='8px 0 0 8px'"><a>系统管理</a></li>  
					<li class="menu_item" onmouseout="this.style.backgroundColor='';this.style.fontWeight='normal'" onmouseover="this.style.backgroundColor='#77D1F6';this.style.fontWeight='bold'"><a>用户管理</a></li>  
					<li class="menu_item" onmouseout="this.style.backgroundColor='';this.style.fontWeight='normal'" onmouseover="this.style.backgroundColor='#77D1F6';this.style.fontWeight='bold'"><a>新闻管理</a></li>  
					<li class="menu_item" onmouseout="this.style.backgroundColor='';this.style.fontWeight='normal'" onmouseover="this.style.backgroundColor='#77D1F6';this.style.fontWeight='bold'"><a>新闻管理</a></li>  
					<li class="menu_item" style="border-radius:8px 0 0 8px;border:0px;" onmouseout="this.style.backgroundColor='';this.style.fontWeight='normal'" onmouseover="this.style.backgroundColor='#77D1F6';this.style.borderRadius='0 8px 8px 0';this.style.fontWeight='bold'"><a>邮件管理</a></li> 
				</ul>  
			</div>  
        </div>  
        <div id="user_info">  
            <div id="welcome">欢迎<%= request.getParameter("username") %>使用本系统</div>  
            <div id="logout">安全退出</div>  
        </div>  
    </div>
<div id="navigator">  
    <iframe src="tree.html"></iframe>  
</div>  
<div id="main">  
    <iframe name="MainFrame" src="main.html"></iframe>  
</div>
<div id="footer">
	<div id="text">Copyright ©  All Rights Reserved Powered By </div>
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