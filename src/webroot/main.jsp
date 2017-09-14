<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.io.*,java.util.*,java.sql.*" %>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ page import="mysqlConnector.DbConnector,mysqlConnector.generalDBAPI" %>
<%@ page import="java.util.regex.Matcher,java.util.regex.Pattern" %>
<%@ page import="common.*" %>
<!DOCTYPE html>  
<html>  
<link rel="stylesheet" type="text/css" href="mainstyle.css" />
<link href="./assets/img/favicon.ico" rel="shortcut icon" type="image/x-icon">
  	<!-- Bootstrap -->
    <link href="./assets/css/bootstrap.css" rel="stylesheet">
	<!-- Bootstrap Material Design -->
	<link rel="stylesheet" href="./assets/css/bootstrap-material-design.css">
	<link rel="stylesheet" href="./assets/css/ripples.css">
	<!-- My style -->
	<link rel="stylesheet" href="./assets/css/app.css">
	<script src="./assets/js/jquery.min.js"></script>
    <script src="./assets/js/bootstrap.min.js"></script>
    <script src="./assets/js/material.js"></script>
    <script src="./assets/js/ripples.js"></script>
	<script>
      	// material初始化
    	$.material.init();

		// 树形菜单交互
		$('.tree>li>a').on('click', function() {
			$(this).parent('li').toggleClass('in').siblings().removeClass('in');
		});
		$('.tree1 li a').on('click', function() {
			$('.tree1 li').removeClass('active');
			$(this).parent('li').addClass('active');
		});

		// 菜单点击
		$(document).on('click', '.tree1 a', function(event) {
			event.preventDefault();
			if(!$('.main').hasClass('in')) $('.main').addClass('in');

			var id = $(this).attr('href');
			var lv1 = $(this).parents('.tree1').siblings('a').text().trim();
			var lv2 = $(this).text().trim();

			renderQuery(id);

			$('#breadcrumb').html('<li><a href="javascript:void(0)">'+'hahahahahaha'+'</a></li><li class="active">'+lv2+'</li>');
		});
		function renderQuery(id){
			$('#query').html($(id).html());
		}
    </script>




<%!
boolean iswrong = false; 
public String showmsg(String s, String regex, String wrongmsg1, String wrongmsg2)
{
		if(s.equals("")){
			iswrong = true;
			return "<div id='error-tip'><span id='tip-text'>" + wrongmsg1 + "</span></div>";
		}
		else if(!Pattern.matches(regex, s)){
			iswrong = true;
			return "<div id='error-tip'><span id='tip-text'>" + wrongmsg2 + "</span></div>";			
		}
		return "";
}
%>
//从show的方式改为直接更改预留内容！
//对所有的输入进行替换！
//增加服务！
//错误成功页面优化？

//整体替换！

<head>    
</head>  
<body>  


<div>
	<% if(request.getParameter("action_type")==null) { %>
	<div class="content content-query">
          <div class="wrap">
            <ul class="breadcrumb" id="breadcrumb"></ul>

            <div class="query" id="query">
	<div class="query-param panel panel-default">
          <div class="panel-heading"><span class="text-info">修改密码</span></div>
          <div class="panel-body">
            <form class="form-horizontal" >
                <div class="form-group">
                  <label class="col-md-2 control-label">旧密码</label>
                  <div class="col-md-10">
                    <input type="text" class="form-control form-info"  placeholder="请输入">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-2 control-label">新密码</label>
                  <div class="col-md-10">
                    <input type="text" class="form-control form-info"  placeholder="请输入">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-2 control-label">请再次输入新密码</label>
                  <div class="col-md-10">
                    <input type="text" class="form-control form-info"  placeholder="请输入">
                  </div>
                </div>
                <div class="form-group">
                  <div class="col-md-10 col-md-offset-2">
                    <button type="button" class="btn btn-default">重置</button>
                    <button type="submit" class="btn btn-info">提交</button>
                  </div>
                </div>
            </form>
          </div>
        </div>
		</div>
		</div>
	</div>

	<% } else if(request.getParameter("action_type").equals("insert_customer")) {%>
	<%
		if(session.getAttribute("rank").equals("0"))
			response.sendRedirect("wrong.jsp?type=right");
	%>
	<div class="query-param panel panel-default">
          <div class="panel-heading"><span class="text-info">新增用户</span></div>
		  <div class="panel-body">
	<form class="form-horizontal" action="main.jsp?submit=1&action_type=insert_customer" method="POST">
		<div id="name" class="form-group">  
			<label class="col-md-2 control-label">客户名称</label>
                  <div class="col-md-10">
                    <input type="text" name="name" class="form-control form-info"  placeholder="请输入">
                  </div>			 
			<%
			if(request.getParameter("submit")!=null && request.getParameter("submit").equals("1"))
			{
				String s = request.getParameter("name");
				out.print(showmsg(s,".*","客户名称不能为空！",""));
			}			
			%>
        </div>		
		<div id="username">  
            <span class="item">用户名称:</span>  
            <span><input type="text" name="username" class="form-input"></span> 
			<%
			if(request.getParameter("submit")!=null && request.getParameter("submit").equals("1"))
			{
				mysqlConnector.DbConnector con = new mysqlConnector.DbConnector();	
				String s = request.getParameter("username");
				Common.CustomerInfo user = con.getCustomerInfo(s); 
				if(user!=null){
					iswrong = true;
					out.print("<div id='error-tip'><span id='tip-text'>" + "该用户名已经存在！" + "</span></div>");
				}
				else out.print(showmsg(s,".*","用户名称不能为空！",""));
			}			
			%>			
        </div>
		<div id="password">  
            <span class="item">用户密码:</span>  
            <span><input type="text" name="password" class="form-input"></span> 
			<%
			if(request.getParameter("submit")!=null && request.getParameter("submit").equals("1"))
			{
				String s = request.getParameter("password");
				out.print(showmsg(s,".*","用户密码不能为空！",""));
			}			
			%>			
        </div>
		<div id="type">  
            <span class="item">用户类型:</span>  
            <span><span><select name="type">
				  <option value="金融公司">金融公司</option>
				  <option value="数据公司">数据公司</option>
				  <option value="租车">租车</option>
				  <option value="婚恋">婚恋</option>
				  <option value="其他">其他</option>
			</select></span> 			
        </div>
		</div>
		<div id="area">  
            <span class="item">客户地区:</span>  
            <span><select name="area">
				  <option value="北京">北京市</option>
				  <option value="浙江省">浙江省</option>
				  <option value="天津市">天津市</option>
				  <option value="安徽省">安徽省</option>
				  <option value="上海市">上海市</option>
				  <option value="福建省">福建省</option>
				  <option value="重庆市">重庆市</option>
				  <option value="江西省">江西省</option>
				  <option value="山东省">山东省</option>
				  <option value="河南省">河南省</option>
				  <option value="湖北省">湖北省</option>
				  <option value="湖南省">湖南省</option>
				  <option value="广东省">广东省</option>
				  <option value="海南省">海南省</option>
				  <option value="山西省">山西省</option>
				  <option value="青海省">青海省</option>
				  <option value="江苏省">江苏省</option>
				  <option value="辽宁省">辽宁省</option>
				  <option value="吉林省">吉林省</option>
				  <option value="台湾省">台湾省</option>
				  <option value="河北省">河北省</option>
				  <option value="贵州省">贵州省</option>
				  <option value="四川省">四川省</option>
				  <option value="云南省">云南省</option>
				  <option value="陕西省">陕西省</option>
				  <option value="甘肃省">甘肃省</option>
				  <option value="黑龙江省">黑龙江省</option>
				  <option value="香港特别行政区">香港特别行政区</option>
				  <option value="澳门特别行政区">澳门特别行政区</option>
				  <option value="广西壮族自治区">广西壮族自治区</option>
				  <option value="宁夏回族自治区">宁夏回族自治区</option>
				  <option value="新疆维吾尔自治区">新疆维吾尔自治区</option>
				  <option value="内蒙古自治区">内蒙古自治区</option>
				  <option value="西藏自治区">西藏自治区</option>
			</select></span> 			
        </div>
		<div id="balance">  
            <span class="item">余额:</span>  
            <span><input type="text" name="balance" class="form-input"></span> 
			<%
			if(request.getParameter("submit")!=null && request.getParameter("submit").equals("1"))
			{
				String s = request.getParameter("balance");
				out.print(showmsg(s,"[1-9][0-9]*","余额不能为空！","余额必须是个大于0的整数！"));
			}			
			%>
		<div id="ip">  
            <span class="item">IP白名单:</span>  
            <span><input type="text" name="ip" class="form-input"></span> 
			<%
			if(request.getParameter("submit")!=null && request.getParameter("submit").equals("1"))
			{
				String s = request.getParameter("ip");
				out.print(showmsg(s,"((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))(;(?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))*","IP白名单不能为空！","IP地址需为xxx.xxx.xxx.xxx的格式，多个IP地址需用分号';'分隔"));
			}			
			%>			
        </div>
		<div id="contactname">  
            <span class="item">联系人姓名:</span>  
            <span><input type="text" name="contactname" class="form-input"></span> 
			<%
			if(request.getParameter("submit")!=null && request.getParameter("submit").equals("1"))
			{
				String s = request.getParameter("contactname");
				out.print(showmsg(s,".*","联系人姓名不能为空！",""));
			}			
			%>			
        </div>
		<div id="contactmail">  
            <span class="item">联系人邮箱:</span>  
            <span><input type="text" name="contactmail" class="form-input"></span> 
			<%
			if(request.getParameter("submit")!=null && request.getParameter("submit").equals("1"))
			{
				String s = request.getParameter("contactmail");
				out.print(showmsg(s,"([a-zA-Z0-9_\\-\\.])+@([a-zA-Z0-9_\\-])+(\\.[a-zA-Z0-9_\\-]+)+","联系人邮箱不能为空！","请填写正确的邮箱地址！"));
			}			
			%>			
        </div>
		<div id="contactphone">  
            <span class="item">联系人电话:</span>  
            <span><input type="text" name="contactphone" class="form-input"></span> 
			<%
			if(request.getParameter("submit")!=null && request.getParameter("submit").equals("1"))
			{
				String s = request.getParameter("contactphone");
				out.print(showmsg(s,"[0-9\\-]+","联系人电话不能为空！","请填写正确的电话号码！"));
			}			
			%>			
        </div>
		<div id="BDname">  
            <span class="item">BD负责人:</span>  
            <span><input type="text" name="BDname" class="form-input"></span> 
			<%
			if(request.getParameter("submit")!=null && request.getParameter("submit").equals("1"))
			{
				String s = request.getParameter("BDname");
				out.print(showmsg(s,".*","BD负责人不能为空！",""));
			}			
			%>			
        </div>
		<div id="BDmail">  
            <span class="item">BD邮箱:</span>  
            <span><input type="text" name="BDmail" class="form-input"></span> 
			<%
			if(request.getParameter("submit")!=null && request.getParameter("submit").equals("1"))
			{
				String s = request.getParameter("BDmail");
				out.print(showmsg(s,"([a-zA-Z0-9_\\-\\.])+@([a-zA-Z0-9_\\-])+(\\.[a-zA-Z0-9_\\-]+)+","BD邮箱不能为空！","请填写正确的邮箱地址！"));
			}			
			%>			
        </div><div id="BDphone">  
            <span class="item">BD电话:</span>  
            <span><input type="text" name="BDphone" class="form-input"></span> 
			<%
			if(request.getParameter("submit")!=null && request.getParameter("submit").equals("1"))
			{
				String s = request.getParameter("BDphone");
				out.print(showmsg(s,"[0-9\\-]+","BD电话不能为空","请填写正确的电话号码！"));
			}			
			%>			
        </div>
		<div id="button-group">  
            <input type="submit" class="btn" value="提交"/>  
            <input type="reset" class="btn" value="重置"/>  
        </div>
		<% 
		if(!iswrong) {
			if(request.getParameter("submit")!=null && request.getParameter("submit").equals("1"))
			{
				mysqlConnector.DbConnector con = new mysqlConnector.DbConnector();
				Common.CustomerInfo c = new Common.CustomerInfo();
				c.customer_name = request.getParameter("name");
				c.customer_loginname = request.getParameter("username");
				c.customer_password = request.getParameter("password");
				c.customer_balance = Integer.parseInt(request.getParameter("balance"));
				c.customer_ip = request.getParameter("ip");
				c.customer_type = request.getParameter("type");
				c.customer_contactName= request.getParameter("contactname");
				c.customer_areaId = request.getParameter("area");
				c.customer_createdByUserId = Integer.parseInt(session.getAttribute("id").toString());
				out.print(c.customer_createdByUserId);
				con.inputNewCustomer(c);
				response.sendRedirect("success.jsp?type=insert");
			}
		}
		%>
	</form>	
	<% } else if(request.getParameter("action_type").equals("charge")) {%>
	<%
		if(session.getAttribute("rank").equals("0"))
			response.sendRedirect("wrong.jsp?type=right");
	%>
		<span class="top"><a>用户充值</a></span> 
		<form action="main.jsp?submit=1&action_type=charge" method="POST">
		<div id="username">  
            <span class="item">客户登陆名称:</span>  
            <span><input type="text" name="username" class="form-input"></span>  
			<%
			if(request.getParameter("submit")!=null && request.getParameter("submit").equals("1"))
			{
				mysqlConnector.DbConnector con = new mysqlConnector.DbConnector();	
				String s = request.getParameter("username");
				Common.CustomerInfo user = con.getCustomerInfo(s); 
				if(user==null) out.print("<div id='error-tip'><span id='tip-text'>" + "该用户不存在！" + "</span></div>");
				else out.print(showmsg(s,".*","客户登陆名称不能为空！",""));
			}			
			%>
        </div>
		<div id="balance">  
            <span class="item">充值金额:</span>  
            <span><input type="text" name="balance" class="form-input"></span> 
			<%
			if(request.getParameter("submit")!=null && request.getParameter("submit").equals("1"))
			{
				String s = request.getParameter("balance");
				out.print(showmsg(s,"[1-9][0-9]*","充值金额不能为空！","充值金额必须是个大于0的整数！"));
			}			
			%>	
		</div>		
		<div id="user-type"> 
			<label><input name="type" type="radio" value="1" checked = true />正常充值 </label> 
			<label><input name="type" type="radio" value="2" />附加充值 </label>
		</div>
		<div id="button-group"> 
			<span class="item">描述:<br></span>  		
            <textarea rows="10" cols="30" name="description" onfocus="if(value=='请在此输入对此次充值的描述。'){value=''}"  
    onblur="if (value ==''){value='请在此输入对此次充值的描述。'}">请在此输入对此次充值的描述。</textarea>  
        </div>
		<div id="button-group">  
            <input type="submit" class="btn" value="充值"/>  
            <input type="reset" class="btn" value="重置"/>  
        </div>
		<% 
		if(!iswrong) {
			if(request.getParameter("submit")!=null && request.getParameter("submit").equals("1"))
			{
				mysqlConnector.DbConnector con = new mysqlConnector.DbConnector();
				Common.CustomerInfo c = con.getCustomerInfo(request.getParameter("username"));
				generalDBAPI<Common.CustomerInfo> api = new generalDBAPI<Common.CustomerInfo>( Common.CustomerInfo.class );
				api.setWhere("customer_loginname = " + "'" + c.customer_loginname + "'")
					.executeUpdate("customer_balance = customer_balance + " + Integer.parseInt(request.getParameter("balance")) );
				if(request.getParameter("type").equals("1"))
				{
					Common.chargeLog log = new Common.chargeLog();
					log.old_balance = c.customer_balance;
					log.new_balance = c.customer_balance+Integer.parseInt(request.getParameter("balance"));
					log.charge_value = Integer.parseInt(request.getParameter("balance"));
					log.additional_chargevalue = 0;
					log.date = new java.util.Date();
					log.customer_id = c.customer_id;
					log.user_id = Integer.parseInt(session.getAttribute("id").toString());
					generalDBAPI<Common.chargeLog> logapi = new generalDBAPI<Common.chargeLog>( Common.chargeLog.class );
					logapi.executeInsert(log);
				}
				else
				{
					Common.chargeLog log = new Common.chargeLog();
					log.old_balance = c.customer_balance;
					log.new_balance = c.customer_balance+Integer.parseInt(request.getParameter("balance"));
					log.charge_value = 0;
					log.additional_chargevalue = Integer.parseInt(request.getParameter("balance"));
					log.date = new java.util.Date();
					log.customer_id = c.customer_id;
					log.user_id = Integer.parseInt(session.getAttribute("id").toString());
					log.description = request.getParameter("description");
					generalDBAPI<Common.chargeLog> logapi = new generalDBAPI<Common.chargeLog>( Common.chargeLog.class );
					logapi.executeInsert(log);
				}
				response.sendRedirect("success.jsp?type=charge");
			}
		}
		%>
		</form>	
	
	<% } else if(request.getParameter("action_type").equals("insert_service")) {%>
	<%
		if(session.getAttribute("rank").equals("0"))
			response.sendRedirect("wrong.jsp?type=right");
	%>
	<span class="top">新增服务</span> 
	<div>
		<span class="item"></span>
	</div>
	
	<% } else if(request.getParameter("action_type").equals("list_customer")) {%>
	<%
		if(session.getAttribute("rank").equals("0"))
			response.sendRedirect("wrong.jsp?type=right");
	%>
	<span class="top">客户列表</span> 
	<div>
		<span class="item"></span>
	</div>
	
	<% } %>
</div>
	
</body>

</html>