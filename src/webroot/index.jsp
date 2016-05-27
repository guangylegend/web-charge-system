<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>  
<html>  


<head>  
	<link rel="stylesheet" type="text/css" href="loginstyle.css"/>
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
                <div id="image"></div>  
				
            </div> 
			<div id="right">  
				<div id="welcome">  
					<span id="welcome-text">管理登录</span>  
				</div> 
					<form action="system.jsp" method="POST">
                    <div id="user-name">  
                        <span class="item">用户名:</span>  
                        <span><input type="text" name="username" class="form-input"></span>  
                    </div>  
                    <div id="user-password">  
                        <span class="item">密码:</span>  
                        <span class="input"><input type="password" name="password" class="form-input"></span>  
                    </div>  
                    <div id="error-tip">  
                        <span id="tip-text"></span>  
                    </div>  
                    <div id="button-group">  
                        <input type="submit" class="btn" value="login"/>  
                        <input type="reset" class="btn" value="reset"/>  
                    </div>  
					</form>
                </div>  
        </div>  
    </div>  
</div>  
<div id="footer">  
    <div id="text">Copyright ?  All Rights Reserved Powered By 
</div>