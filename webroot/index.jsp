<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>  
<html>  
<jsp:declaration>
	int a = 1;
</jsp:declaration>	


<head>  
	<link rel="stylesheet" type="text/css" href="style.css"/>
    <meta charset="UTF-8" />  
    <title>Login Page</title>  
</head>  

<body>

<div id="header">  
    <div id="logo"></div>  
    <div id="heading">  
        <div id="title">后台管理系统</div>  
        <div id="subTitle">Ver 1.0</div>  
    </div>  
</div>  
<div id="main">  
    <div id="mainBg">  
        <div id="mainPanel">  
            <div id="left">  
			<li><p><b>First Name:</b>
				<%= request.getParameter("userName")%>
			</p></li>
                <div id="image"></div>  
            </div> 
			<div id="right">  
				<div id="welcome">  
					<span id="welcome-text">管&nbsp;理&nbsp;登&nbsp;录</span>  
				</div> 
					<form action="index.jsp" method="POST">
                    <div id="user-name">  
                        <span class="item">用户名:</span>  
                        <span><input type="text" name="userName" class="form-input"></span>  
                    </div>  
                    <div id="user-password">  
                        <span class="item">密&nbsp;码:</span>  
                        <span class="input"><input type="password" name="password" class="form-input"></span>  
                    </div>  
                    <div id="error-tip">  
                        <span id="tip-text"></span>  
                    </div>  
                    <div id="button-group">  
                        <input type="submit" class="btn" value="提交"/>  
                        <input type="reset" class="btn" value="重置"/>  
                    </div>  
					</form>
                </div>  
        </div>  
    </div>  
</div>  
<div id="footer">  
    <div id="text">Copyright ©  All Rights Reserved Powered By 
</div>