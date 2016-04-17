package mysqlConnector;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javax.security.auth.login.Configuration;

import Common.Permission;
import Common.Service;

import java.sql.ResultSet;


/**
 * 
 * @author chensqi
 * All you need is the interface of DbConnector
 */
public class DbConnector {
	//Connection con = null ;
	
	// Connect to remote mysql database
	// See ConnectingConfigurations for details
	
	DbConnector() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		/*
		 * con = DriverManager.getConnection(
		 
				ConnectingConfigurations.getConnectingUrlWithDatabaseName(),
				ConnectingConfigurations.getConnectingUserName(),
				ConnectingConfigurations.getConnectingPassword());
		*/
	}
	/**
	 * Simply test if the connector works well 
	 * @throws SQLException
	 */
	void Test() throws SQLException {
		
		init();
		Connection con = DriverManager.getConnection(				 
				ConnectingConfigurations.getConnectingUrlWithDatabaseName(),
				ConnectingConfigurations.getConnectingUserName(),
				ConnectingConfigurations.getConnectingPassword());
		Statement stmt = con.createStatement();
		stmt.execute("CREATE TABLE test(id int)");
		stmt.execute("DROP TABLE test");
		clear();
		System.out.print("All testcases done!");
	}
	
	/**
	 * Warning! Clear everything, create database and create all tables we needed
	 */
	public void init() throws SQLException {
		clear();
		Connection TempCon = DriverManager.getConnection(
				ConnectingConfigurations.getConnectingUrl(),
				ConnectingConfigurations.getConnectingUserName(),
				ConnectingConfigurations.getConnectingPassword());

		Statement stmt = TempCon.createStatement();
		stmt.execute("CREATE DATABASE " + ConnectingConfigurations.getConnectingDatabaseName());
		createTables();
	}
	/**
	 * Warning! Delete entire database
	 * 
	 * @throws SQLException
	 */
	public void clear() throws SQLException{
		Connection con = DriverManager.getConnection(				 
				ConnectingConfigurations.getConnectingUrl(),
				ConnectingConfigurations.getConnectingUserName(),
				ConnectingConfigurations.getConnectingPassword());

		Statement stmt = con.createStatement();
		stmt.execute("DROP DATABASE IF EXISTS " + ConnectingConfigurations.getConnectingDatabaseName() );
	}
	/**
	 * Create all the tables according to TableConfigurations
	 * @throws SQLException
	 */
	void createTables() throws SQLException {
		
		Connection con = DriverManager.getConnection(				 
				ConnectingConfigurations.getConnectingUrlWithDatabaseName(),
				ConnectingConfigurations.getConnectingUserName(),
				ConnectingConfigurations.getConnectingPassword());

		Statement stmt = con.createStatement();
		TableConfigurations.generateAllTables();
		for ( int i = 0 ; i < TableConfigurations.tables.size() ; ++i ) {
			Table t = TableConfigurations.tables.get(i);
			stmt.execute(t.getCreatTableStatement());
		}
	}
	
	/**
	 * Register a new user in database
	 * @param user Not all fields are needed, loginName, password must be included
	 * @return true if register success
	 * @throws SQLException 
	 */
	boolean inputUserRegister(Common.UserInfo user) throws SQLException {
		Connection con = DriverManager.getConnection(				 
				ConnectingConfigurations.getConnectingUrlWithDatabaseName(),
				ConnectingConfigurations.getConnectingUserName(),
				ConnectingConfigurations.getConnectingPassword());

		Statement stmt = con.createStatement();
		//@TODO
		
		
		return true ;
	}
	/**
	 * When user login, add a log in database for this login
	 * @return true if success
	 */
	boolean inputUserLogin(String userName) {
		//@TODO
		return true;
	}
	/**
	 * When user call a service, add a log in database for this calling
	 * @param userName
	 * @param serviceName
	 * @return
	 */
	boolean inputUserCallService(String userName, String serviceName) {
		return true;
	}
	/**
	 * Add a new IP into white list
	 * @return
	 */
	boolean inputWhiteList(Common.IP ipAddress) {
		//@TODO
		return true;
	}
	/**
	 * 
	 * @param service
	 * @return
	 */
	boolean inputNewService(Common.Service service) {
		//@TODO service table and somthing
		return true;
	}
	/**
	 * 
	 * @param loginName 
	 * @return information for this user if existed 
	 * @throws SQLException
	 * @throws UserNotFoundException
	 * @throws UnknownException "More than 1 user found"
	 */
	Common.UserInfo getUserInfo(String loginName) throws SQLException, UserNotFoundException, UnknownException {
		Connection con = DriverManager.getConnection(				 
				ConnectingConfigurations.getConnectingUrlWithDatabaseName(),
				ConnectingConfigurations.getConnectingUserName(),
				ConnectingConfigurations.getConnectingPassword());

		Statement stmt = con.createStatement();
		ResultSet res =  stmt.executeQuery("SELECT * from " + TableConfigurations.tableNames[0] );	//	fetch results from account table
		
		
		int cnt = 0 ;
		Common.UserInfo user = new Common.UserInfo();
		while ( res.next() ) {
			++cnt;

			user.userId = res.getInt(0);
			user.userName = res.getString(1); //	The real name of user
			user.loginName = res.getString(2);	//	Account name
			user.password = res.getString(3); //	Encrypted password
			user.email = res.getString(4);
			user.companyAddress = res.getString(5);
			user.signUpTime = res.getDate(6);	//	Time when user registered
			user.lastLogInTime = res.getDate(7);
			user.remainedMoney = res.getInt(8);	//	Money = 1 <==> 0.01 RMB
			user.priviligeLevel = res.getInt(9);	//	Level 1,2,3.. for managers. Level 0 for users
			user.activeOrNot = res.getBoolean(10); //	0 or 1
		}
		if ( cnt == 0 )
			throw new UserNotFoundException();
		else if ( cnt > 1 )
			throw new UnknownException("More than 1 user found!");
		return user;
	}
	/**
	 * 
	 * @param userName
	 * @return a list of logs
	 */
	ArrayList<Common.UserLog> getUserLog(String userName) {
		ArrayList<Common.UserLog>res = new ArrayList<Common.UserLog>();
		return res;	//@TODO
	}
	/**
	 * 
	 * @param serviceName
	 * @return a service instance
	 */
	Common.Service getServiceByName(String serviceName) {
		return new Common.Service("haha");	//	@TODO
	}
}

