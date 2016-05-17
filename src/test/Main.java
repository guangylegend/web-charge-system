package test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import Common.ServicePara;

public class Main {
	public static void main(String[] args) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, SQLException {
		Test();
	}
	public static void Test() throws SQLException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException {
			
		mysqlConnector.DbConnector con = new mysqlConnector.DbConnector();
		
		System.out.println("Tests initialiing database....");
		con.init();
		
		System.out.println("Tests user register....");
		Common.UserInfo user = new Common.UserInfo();
		user.user_loginName = "admin1";
		user.user_password = "administrator1";
		user.user_type = 0;
		con.inputNewUser(user);
		
		System.out.println("Tests read user infomations....");
		user = con.getUserInfo("admin1");
		System.err.println(user);
		
		System.out.println("Tests customer register....");
		Common.CustomerInfo c = new Common.CustomerInfo();
		c.customer_name = "new1";
		c.customer_banlance = 100;
		c.customer_contactName = "xiao ming";
		c.customer_createdByUserId = con.getUserInfo("admin1").user_id;
		con.inputNewCustomer(c);
		
		System.out.println("Tests read customer infomations....");
		c = con.getCustomerInfo("new1");
		System.err.println(c);
		
		System.out.println("Tests update customer's money....");
		con.setIncreaseUserMoney("new1", 150);
		c = con.getCustomerInfo("new1");
		System.err.println(c);
		
		System.out.println("Tests insert and read service");
		con.inputNewService(new Common.Service("service0"));
		con.setServiceFee("service0", 998);
		Common.Service service = con.getServiceByName("service0");
		System.err.println(service);
		
		System.out.println("Tests insert and read para");
		Common.ServicePara para = new Common.ServicePara();
		para.setServiceName("service0").setParaName("para0").setParaType("string");
		con.inputNewParameterIntoService(para);
		para.setServiceName("service0").setParaName("para1").setParaType("int");
		con.inputNewParameterIntoService(para);
		ArrayList<ServicePara> paras = con.getParaByServiceName("service0");
		System.err.println(paras);
		
		System.out.println("Tests insert and read all services");
		ArrayList<Common.Service> servicelist = con.getAllServices();
		for ( Common.Service s: servicelist) 
			System.err.println(s);
		
		System.out.println("All testcases done!");
	}
	
}
