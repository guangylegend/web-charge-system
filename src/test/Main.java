package test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

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
		user.nickName = "hehe";
		user.userName = "hehe";
		user.password = "123123";
		con.inputUserRegister(user);
		
		user = new Common.UserInfo();
		user.nickName = "haha";
		user.userName = "haha";
		user.password = "123qwe";
		user.email = "haha@gmail.com";
		user.companyAddress = "nonono";
		user.signUpTime = new Date(1993,11,11,0,0,0);	
		user.lastLogInTime = new Date(2011,11,11,0,0,1);
		user.Money = 100;	//	Money = 1 <==> 0.01 RMB
		user.priviligeLevel = 2;	//	Level 0 for super manager, 1 for manager, 2,3,4.... for users
		user.activeOrNot = true; //	0 or 1
		
		con.inputUserRegister(user);
		
		System.out.println("Tests read user infomations....");
		user = con.getUserInfo("haha");
		System.err.println(user);
		
		con.setUserMoney("hehe", 100000);
		user = con.getUserInfo("hehe");
		System.err.println(user);
		
		
		System.out.println("Tests insert and read service");
		
		con.inputNewService("service0");
		con.inputNewParameterIntoService("service0", "para1", "string");
		con.inputNewParameterIntoService("service0", "para2", "int");
		con.setServiceFee("service0", 100);
		Common.Service service = con.getServiceByName("service0");
		System.err.println(service);
		
		ArrayList<Common.Service> servicelist = con.getAllServices();
		for ( Common.Service s: servicelist) 
			System.err.println(s);
		
		System.out.println("Tests insert and delete ip in white list");
		con.inputWhiteList(new Common.IP("202.120.61.1"));
		System.err.println( "IP 202.120.61.1 (true/false) "+ con.getContainedByWhiteList(new Common.IP("202.120.61.1")) );
		con.deleteWhiteList(new Common.IP("202.120.61.1"));
		System.err.println( "IP 202.120.61.1 (true/false) "+ con.getContainedByWhiteList(new Common.IP("202.120.61.1")) );
		
		
		System.out.println("Tests insert and read APIlog");
		Common.APILog log = new Common.APILog();
		log.userName = "hehe";
		log.log = "Log number 0 for user hehe";
		log.date = new Date();
		con.inputAPILog(log);
		
		log = con.getAPILogByLogid(1);
		System.err.println(log);
		
		System.out.println("All testcases done!");
	}
	
}
