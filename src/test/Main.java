package test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import Common.APILog;
import Common.ServicePara;
import Common.UserInfo;
import Common.machineList;
import mysqlConnector.DbConnector;
import mysqlConnector.generalDBAPI;

public class Main {
	public static void main(String[] args) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, SQLException {
		Test();
		//test2();
	}
	public static void test2() throws ClassNotFoundException, SQLException {
		
		Common.UserInfo u1 = new UserInfo();
		
		u1.user_loginName = "xiaoming";
		
		Common.UserInfo u2 = (UserInfo) u1.Clone();
		u1.user_loginName = "xiaoa";
		System.out.println(u2);
	}
	public static void Test() throws SQLException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException {
			
		long startTime, endTime;
		mysqlConnector.DbConnector con = new mysqlConnector.DbConnector();
		
		System.out.println("Tests initialiing database....");
		con.init();
		
		
		System.out.println("Tests user register....");
		Common.UserInfo user = new Common.UserInfo();
		user.user_loginName = "admin1";
		user.user_password = "administrator1";
		user.user_type = 0;
		
		startTime = System.currentTimeMillis();
		con.inputNewUser(user);
		endTime = System.currentTimeMillis();
		System.err.println("elasped: " + (endTime-startTime) + " ms");
		
		
		System.out.println("Tests read user infomations....");
		user = con.getUserInfo("admin1");
		System.err.println(user);
		
		System.out.println("Tests customer register....");
		Common.CustomerInfo c = new Common.CustomerInfo();
		c.customer_name = "new1";
		c.customer_password = "123123";
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
		
		System.out.println("Tests input 2 machines......");
		Common.machineList machine = new machineList();
		machine.ip = "192.168.0.1";
		con.inputNewMachine(machine);
		machine.ip = "192.168.0.2";
		con.inputNewMachine(machine);
		
		
		System.out.println("Tests insert and read service.....");
		con.inputNewService(new Common.Service("service0"));
		con.setServiceFee("service0", 998);
		Common.Service service = con.getServiceByName("service0");
		System.err.println(service);
		
		System.out.println("Tests loadbalance......");
		Common.machineList l = con.getNextFreeMachine("service0");
		System.err.println(l);
		l = con.getNextFreeMachine("service0");
		System.err.println(l);
		l = con.getNextFreeMachine("service0");
		System.err.println(l);
		
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
		
		System.out.println("Tests api logging........");
		Common.APILog log = new APILog();
		log.customer_name = "xiaoming";
		log.input = "input1";
		log.output = "output1";
		con.inputNewApiLog(log);
		log.input = "input2";
		log.output = "output2";
		con.inputNewApiLog(log);
		
		ArrayList<Common.APILog> logList = new ArrayList<>();
		logList = con.getApiLogByCustomerName("xiaoming");
		System.err.println(logList);
		
		
		System.out.println("Tests generalAPI ....");
		generalDBAPI<Common.CustomerInfo> api = new generalDBAPI<Common.CustomerInfo>( Common.CustomerInfo.class );
		
		c = new Common.CustomerInfo();
		c.customer_name = "new2";
		c.customer_password = "123123";
		c.customer_banlance = 100;
		c.customer_contactName = "xiao ming";
		c.customer_createdByUserId = con.getUserInfo("admin1").user_id;
		api.executeInsert(c);
		
		ArrayList<Common.CustomerInfo> list = new ArrayList<Common.CustomerInfo>();
		list = api.setOrderBy("customer_banlance")
					.setTop(1).executeSelect();
		
		System.err.println(list);
		
		c = new Common.CustomerInfo();
		c.customer_banlance = 1200;
		api.clear().setWhere("customer_name=\'new2\'").executeUpdate(c);
		list = api.clear().setOrderBy("customer_banlance")
				.setTop(1).executeSelect();
		System.err.println(list);
		
		System.out.println("All testcases done!");
	}
	
}
