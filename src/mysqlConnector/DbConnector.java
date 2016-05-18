package mysqlConnector;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javax.security.auth.login.Configuration;
import javax.sql.CommonDataSource;

import Common.CustomerInfo;
import Common.Service;
import Common.UserAccessOperation;
import Common.UserInfo;
import Common.loadbalance;
import Common.machineList;

import java.sql.ResultSet;


/**
 * 
 * @author chensqi
 * All you need is the interface of DbConnector
 */
public class DbConnector {
	
	/**
	 * Check if any dangerous character is in stmt  
	 * @param stmt
	 * @return true if safe.
	 */
	boolean securityCheck(String stmt) {
		String[] dangerChar = new String[] {","
				,"|"
				,"."
				," "
				,"\\"
				,"'"
				,"\""};
		for (int i = 0 ; i < dangerChar.length ; ++i)
			if (stmt.contains(dangerChar[i]))
				return false;
		return true;
	}
	Connection conectionToDB() throws SQLException {
		
		for ( int i = 0 ; i < ConnectingConfigurations.getHostSize() ; ++i ) {
			
			try {
				return DriverManager.getConnection(
					ConnectingConfigurations.getConnectingUrlWithDatabaseName(i),
					ConnectingConfigurations.getConnectingUserName(),
					ConnectingConfigurations.getConnectingPassword());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.err.println("IP:" + ConnectingConfigurations.getHostIp(i) + " failed!");
				e.printStackTrace();
				
				if ( i == ConnectingConfigurations.getHostSize()-1 )
					throw e;
			}
		}
		return null;
	}
Connection conectionToMysql() throws SQLException {
		
		for ( int i = 0 ; i < ConnectingConfigurations.getHostSize() ; ++i ) {
			
			try {
				return DriverManager.getConnection(
					ConnectingConfigurations.getConnectingUrl(i),
					ConnectingConfigurations.getConnectingUserName(),
					ConnectingConfigurations.getConnectingPassword());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.err.println("IP:" + ConnectingConfigurations.getHostIp(i) + " failed!");
				e.printStackTrace();
				
				if ( i == ConnectingConfigurations.getHostSize()-1 )
					throw e;
			}
		}
		return null;
	}

	
	// Connect to remote mysql database
	// See ConnectingConfigurations for details
	public DbConnector() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		
	}
	/**
	 * Warning! Clear everything, create database and create all tables we needed
	 */
	public void init() throws SQLException {
		clear();
		Connection TempCon = this.conectionToMysql();
		Statement stmt = TempCon.createStatement();
		stmt.execute("CREATE DATABASE " + ConnectingConfigurations.getConnectingDatabaseName());
		createTables();
		createRelations();
		TempCon.close();
	}
	/**
	 * Warning! Delete entire database
	 * 
	 * @throws SQLException
	 */
	public void clear() throws SQLException{
		Connection con = this.conectionToMysql();
		Statement stmt = con.createStatement();
		stmt.execute("DROP DATABASE IF EXISTS " + ConnectingConfigurations.getConnectingDatabaseName() );
		con.close();
	}
	/**
	 * Create all the tables according to TableConfigurations
	 * @throws SQLException
	 */
	void createTables() throws SQLException {
		
		Connection con = this.conectionToDB();

		Statement stmt = con.createStatement();
		TableConfigurations.generateAllTables();
		for ( int i = 0 ; i < TableConfigurations.tables.size() ; ++i ) {
			Table t = TableConfigurations.tables.get(i);
			//System.err.println(t.getCreatTableStatement());
			stmt.execute(t.getCreatTableStatement());
		}
		con.close();
	}
void createRelations() throws SQLException {
		
		Connection con = this.conectionToDB();

		Statement stmt = con.createStatement();
		//				BD经理	总监		总经理	运维
		//	个人信息		true	true	true
	    //	修改密码		true	true	true
	    //	新增客户		true	true	true
	    //	修改客户		true	true	true
	    //	查看客户		true	true	true
	    //	删除客户		false	false	true
	    //	客户充值		false	true	true
	    //	充值记录查看	true	true	true
	    //	服务列表		true	true	true
	    //	服务定价		false	true	true
	    //	定价查询		true	true	true
	    //	服务统计		true	true	true
	    //	使用详情		true	true	true
		
		Common.UserAccessOperation o = new UserAccessOperation();
		o.user_type = 1;	//	BD经理
		o.personal_information = 1 ;	//	个人信息
		o.change_password = 1;	//	修改密码
		o.create_customer = 1;	//	新增客户
		o.modify_customer = 1;	//	修改客户
		o.profile_customer = 1;	//	查看客户
		o.delete_customer = 0;	//	删除客户
		o.charge_customer = 0;	//	客户充值
		o.charge_log = 1;	//		充值记录查看
		o.service_list = 1;	//	服务列表
		o.service_setup_price = 0;	//	服务定价
		o.service_price_log = 1;	//	定价查询
		o.service_analysis = 1;	//	服务统计
		o.service_details = 1;	//	使用详情
		stmt.execute("INSERT INTO " + TableConfigurations.tableNames[6] + o.getColumnNameStatement()
							+ " values " + o.getValueStatement());
		
		o.user_type = 2;	//	总监
		o.personal_information = 1 ;	//	个人信息
		o.change_password = 1;	//	修改密码
		o.create_customer = 1;	//	新增客户
		o.modify_customer = 1;	//	修改客户
		o.profile_customer = 1;	//	查看客户
		o.delete_customer = 0;	//	删除客户
		o.charge_customer = 1;	//	客户充值
		o.charge_log = 1;	//		充值记录查看
		o.service_list = 1;	//	服务列表
		o.service_setup_price = 1;	//	服务定价
		o.service_price_log = 1;	//	定价查询
		o.service_analysis = 1;	//	服务统计
		o.service_details = 1;	//	使用详情
		stmt.execute("INSERT INTO " + TableConfigurations.tableNames[6] + o.getColumnNameStatement()
							+ " values " + o.getValueStatement());
		
		o.user_type = 3;	//	总经理
		o.personal_information = 1 ;	//	个人信息
		o.change_password = 1;	//	修改密码
		o.create_customer = 1;	//	新增客户
		o.modify_customer = 1;	//	修改客户
		o.profile_customer = 1;	//	查看客户
		o.delete_customer = 1;	//	删除客户
		o.charge_customer = 1;	//	客户充值
		o.charge_log = 1;	//		充值记录查看
		o.service_list = 1;	//	服务列表
		o.service_setup_price = 1;	//	服务定价
		o.service_price_log = 1;	//	定价查询
		o.service_analysis = 1;	//	服务统计
		o.service_details = 1;	//	使用详情
		stmt.execute("INSERT INTO " + TableConfigurations.tableNames[6] + o.getColumnNameStatement()
							+ " values " + o.getValueStatement());
		
		o.user_type = 4;	//	运维
		o.personal_information = 1 ;	//	个人信息
		o.change_password = 1;	//	修改密码
		o.create_customer = 1;	//	新增客户
		o.modify_customer = 1;	//	修改客户
		o.profile_customer = 1;	//	查看客户
		o.delete_customer = 1;	//	删除客户
		o.charge_customer = 1;	//	客户充值
		o.charge_log = 1;	//		充值记录查看
		o.service_list = 1;	//	服务列表
		o.service_setup_price = 1;	//	服务定价
		o.service_price_log = 1;	//	定价查询
		o.service_analysis = 1;	//	服务统计
		o.service_details = 1;	//	使用详情
		stmt.execute("INSERT INTO " + TableConfigurations.tableNames[6] + o.getColumnNameStatement()
							+ " values " + o.getValueStatement());
		
		con.close();
	}
	
	/**
	 * Register a new costumer in database with 0.00 money.
	 * @param customer Not all fields are needed. name, password must be included
	 * @return true if register success, false if this customer information is invalid
	 * @throws SQLException 
	 */
	public boolean inputNewCustomer(Common.CustomerInfo customer) throws SQLException {
		Connection con = this.conectionToDB();

		Statement stmt = con.createStatement();
		
		if ( customer.customer_name == null ) { // @TODO password checks
			return false;
		}
		if ( customer.customer_id != null )
			return false;
		if ( customer.customer_createdByUserId == null )
			return false;
		/**
		 * Avoid SQL Injection Attack
		 */
		if ( securityCheck(customer.customer_name)/* && securityCheck(user.password)*/ ) {
			String s = "INSERT INTO " + TableConfigurations.tableNames[1]
					+ customer.getColumnNameStatement()
					+ " VALUES " + customer.getValueStatement();
			//System.out.println(s);
			int res = stmt.executeUpdate(s);
			con.close();
			return res==1;
		}
		else {
			con.close();
			return false;
		}
	}
	/**
	 * @param user
	 * @return true if success
	 * @throws SQLException
	 */
	public boolean inputNewUser(Common.UserInfo user) throws SQLException {
		Connection con = this.conectionToDB();

		Statement stmt = con.createStatement();
		
		if ( securityCheck(user.user_loginName) && securityCheck(user.user_password) ) {
			String s = "INSERT INTO " + TableConfigurations.tableNames[0]
					+ user.getColumnNameStatement()
					+ " VALUES " + user.getValueStatement();
			//System.out.println(s);
			int res = stmt.executeUpdate(s);
			con.close();
			return res==1;
		}
		else {
			con.close();
			return false;
		}
	}
	/**
	 * Create a new service in db without parameter
	 * @param service
	 * @return If success
	 * @throws SQLException 
	 */
	public boolean inputNewService(Common.Service service) throws SQLException {
		
		Connection con = this.conectionToDB();
		
		con.setAutoCommit(false);
		
		String s = "INSERT INTO " + TableConfigurations.tableNames[2]	//	service table
				+ service.getColumnNameStatement()
				+ " VALUES " + service.getValueStatement();
		con.createStatement().executeUpdate(s);		
		
		Common.loadbalance b = new loadbalance();
		b.machine_id = 1 ;
		b.service_name = new String(service.service_name);
		String s2 = "INSERT INTO " + TableConfigurations.tableNames[8]	//	service table
				+ b.getColumnNameStatement()
				+ " VALUES " + b.getValueStatement();
		con.createStatement().executeUpdate(s2);
		
		try {
			con.commit();
			con.close();
			return true;
		} catch( SQLException ex ) {
			con.rollback();
			con.close();
			throw ex;
		}
	}
	/**
	 * Insert a new parameter to an existed service
	 * @param para
	 * @return True if success, false if this service dosen't exist
	 * @throws SQLException 
	 */
	public boolean inputNewParameterIntoService( Common.ServicePara para) throws SQLException {
		Connection con = this.conectionToDB();
		
		Statement stmt = con.createStatement();
		String s = "INSERT INTO " + TableConfigurations.tableNames[4]
				+ para.getColumnNameStatement()
				+ " VALUES " + para.getValueStatement();
		
		//	Insert a new record into service table
		int res = stmt.executeUpdate(s);
		con.close();
		return res==1;
	}
	
	
	/**
	 * a new machine with service provided, you can call service API in this machine
	 * @param machine
	 * @return true if success
	 * @throws SQLException
	 */
	public boolean inputNewMachine( Common.machineList machine ) throws SQLException {
		Connection con = this.conectionToDB();
		
		Statement stmt = con.createStatement();
		String s = "INSERT INTO " + TableConfigurations.tableNames[7]
				+ machine.getColumnNameStatement()
				+ " VALUES " + machine.getValueStatement();
		
		//	Insert a new record into service table
		int res = stmt.executeUpdate(s);
		con.close();
		return res==1;
	}
	/**
	 * Set a customer's money into @money
	 * @param customerName
	 * @param money
	 * @return true if success
	 * @throws SQLException
	 */
	public boolean setIncreaseUserMoney(String customerName, int money) throws SQLException {
		Connection con = this.conectionToDB();
		Statement stmt = con.createStatement();
		String s = " UPDATE " + TableConfigurations.tableNames[1]
				+ " SET customer_banlance = customer_banlance + " + money
				+ " WHERE customer_name = " + "'" + customerName + "'"; 
		boolean res = stmt.execute(s);
		con.close();
		return res;
	}
	public boolean setServiceFee(String serviceName, int cost) throws SQLException {
		Connection con = this.conectionToDB();
		Statement stmt = con.createStatement();
		String s = " UPDATE " + TableConfigurations.tableNames[2]
				+ " SET service_guidePrice = " + cost
				+ " WHERE Service_name = " + "'" + serviceName + "'"; 
		boolean res = stmt.execute(s);
		con.close();
		return res;
	}
	/**
	 * 
	 * @param userName 
	 * @return information for this customer if existed. null if user not found 
	 * @throws SQLException
	 */
	public CustomerInfo getCustomerInfo(String customerName) throws SQLException{
		Connection con = this.conectionToDB();

		Statement stmt = con.createStatement();
		ResultSet res =  stmt.executeQuery("SELECT * FROM " 
								+ TableConfigurations.tableNames[1]
								+ " WHERE "
								+ " customer_name = " 
								+ "'" + customerName + "'");
		
		int cnt = 0 ;
		CustomerInfo customer = new Common.CustomerInfo();
		while ( res.next() ) {
			++cnt;
			customer.fetchFromResultSet(res);	
		}
		con.close();
		if ( cnt == 0 )
			return null;
		return customer;
	}
	/**
	 * 
	 * @param userName
	 * @return information for this user if existed. null if user not found 
	 * @throws SQLException
	 */
	public UserInfo getUserInfo(String userLoginName) throws SQLException {
		Connection con = this.conectionToDB();

		Statement stmt = con.createStatement();
		ResultSet res =  stmt.executeQuery("SELECT * FROM " 
								+ TableConfigurations.tableNames[0]
								+ " WHERE "
								+ " user_loginName = " 
								+ "'" + userLoginName + "'");
		
		int cnt = 0 ;
		UserInfo user = new Common.UserInfo();
		while ( res.next() ) {
			++cnt;
			user.fetchFromResultSet(res);	
		}
		con.close();
		if ( cnt == 0 )
			return null;
		return user;
	}
	
	/**
	 * @param serviceName
	 * @return a service instance, null if no such service.
	 * @throws SQLException 
	 */
	public Common.Service getServiceByName(String serviceName) throws SQLException {
		Connection con = this.conectionToDB();
		Statement stmt = con.createStatement();
		// Fetch serviceIds
		ResultSet res =  stmt.executeQuery("SELECT * FROM " 
								+ TableConfigurations.tableNames[2]
								+ " WHERE "
								+ " service_name = " 
								+ "'" + serviceName + "'");	
		Common.Service service = new Common.Service(serviceName);
		if ( res.next() ) {
			service.fetchFromResultSet(res);
			con.close();
			return service;
		}
		else {
			con.close();
			return null;
		}
	}
	/**
	 * Return paras of a service
	 * @param serviceName
	 * @return paras
	 * @throws SQLException
	 */
	public ArrayList<Common.ServicePara> getParaByServiceName(String serviceName) throws SQLException {
		Connection con = this.conectionToDB();
		Statement stmt = con.createStatement();
		// Fetch serviceIds
		ResultSet res =  stmt.executeQuery("SELECT * FROM " 
								+ TableConfigurations.tableNames[4]
								+ " WHERE "
								+ " service_name = " 
								+ "'" + serviceName + "'");	
		ArrayList<Common.ServicePara> result = new ArrayList<Common.ServicePara>();
		while ( res.next() ) {
			Common.ServicePara para = new Common.ServicePara();
			para.fetchFromResultSet(res);
			result.add(para);
		}
		con.close();
		return result;
	}
	/**
	 * @return Return all the service as an arraylist
	 * @throws SQLException
	 */
	public ArrayList<Common.Service> getAllServices() throws SQLException {
		Connection con = this.conectionToDB();
		Statement stmt = con.createStatement();
		// Fetch serviceIds
		ResultSet res =  stmt.executeQuery("SELECT * FROM " 
								+ TableConfigurations.tableNames[2] );	
		ArrayList<Common.Service> serviceList = new ArrayList<Service>();
		while ( res.next() ) {
			Common.Service service = new Common.Service();
			service.fetchFromResultSet(res);
			serviceList.add(service);
		}
		con.close();
		return serviceList;
	}
	
	/***
	 * Return a free machine accroding to service name and automatically increase machines'id for this service.
	 * @param serviceName
	 * @return
	 * @throws SQLException
	 */
	public Common.machineList getNextFreeMachine(String serviceName) throws SQLException {
		Connection con = this.conectionToDB();
		
		try {
			con.setAutoCommit(false);
			//	load current free machine
			Common.loadbalance l = new loadbalance();
			Common.machineList machine = new machineList();
			
			ResultSet r1 = con.createStatement().executeQuery("SELECT * FROM " 
					+ TableConfigurations.tableNames[8]
							+ " WHERE service_name = " + "'" + serviceName + "'");
			if ( r1.next() ) {
				l.fetchFromResultSet( r1 );
				
				ResultSet rt = con.createStatement().executeQuery("SELECT * FROM " 
						+ TableConfigurations.tableNames[7]
								+ " WHERE machine_id = " + l.machine_id);
				if ( rt.next() ) {
					machine.fetchFromResultSet(rt);
				}
				else { con.close(); return null; }
			}
			else {
				con.close();
				return null;
			}
			//	load machine size
			ResultSet r2 = con.createStatement().executeQuery("SELECT * FROM " + TableConfigurations.tableNames[7]);
			int cnt = 0;
			while (r2.next()) ++cnt;
			if ( cnt == 0 ) {
				con.close();return null;
			}
			//	update free machine
			String s = " UPDATE " + TableConfigurations.tableNames[8]
					+ " SET machine_id = " + ((l.machine_id)%cnt+1)
					+ " WHERE service_name = " + "'" + serviceName + "'"; 
			con.createStatement().executeUpdate( s );
			con.commit();
			con.close();
			return machine;
		}
		catch(SQLException ex) {
			con.rollback();
			con.close();
			throw ex;
		}
	}
}

