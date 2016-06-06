package mysqlConnector;

import java.lang.reflect.Constructor;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.security.auth.login.Configuration;
import javax.sql.CommonDataSource;

import com.mysql.jdbc.ReplicationConnection;
import com.mysql.jdbc.ReplicationDriver;
import com.sun.org.apache.bcel.internal.util.Class2HTML;

import Common.APILog;
import Common.CustomerInfo;
import Common.Service;
import Common.SqlAble;
import Common.UserAccessOperation;
import Common.UserInfo;
import Common.loadbalance;
import Common.machine;

import java.sql.ResultSet;


/**
 * 
 * @author chensqi
 * Deprecated
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
	public static String getDatabaseHost(int index) {
		return ConnectingConfigurations.getHostIp(index);
	}
	/**
	 * @return total number of database
	 */
	public static int getDatabaseNum() {
		return ConnectingConfigurations.getHostSize();
	}
	/**
	 * @param i check i-th database is working or not 
	 * @return true if ok. false if crashed.
	 */
	public static boolean validCheck( int index ) {
		
			try {
				DriverManager.getConnection(
					ConnectingConfigurations.validCheckUrl(index),
					ConnectingConfigurations.getConnectingUserName(),
					ConnectingConfigurations.getConnectingPassword());
			} catch (SQLException e) {
				return false;
			}
			return true;
	}
	static Connection conectionToDB() throws SQLException {
		/*
		ReplicationDriver driver = new ReplicationDriver();
	    Properties props = new Properties();
	    props.put("autoReconnect", "true");
	    props.put("failOverReadOnly", "false");
	    props.put("roundRobinLoadBalance", "true");
	    props.put("user", ConnectingConfigurations.getConnectingUserName());
	    props.put("password", ConnectingConfigurations.getConnectingPassword());
	    return driver.connect(ConnectingConfigurations.getConnectingUrlWithDatabaseName(),
	            props);
		*/
		
		return DriverManager.getConnection(
				ConnectingConfigurations.getConnectingUrlWithDatabaseName(),
				ConnectingConfigurations.getConnectingUserName(),
				ConnectingConfigurations.getConnectingPassword());
	}
	static Connection conectionToMysql() throws SQLException {
		/*
		ReplicationDriver driver = new ReplicationDriver();
		
	    Properties props = new Properties();
	    props.put("autoReconnect", "true");
	    props.put("failOverReadOnly", "false");
	    props.put("roundRobinLoadBalance", "true");
	    props.put("user", ConnectingConfigurations.getConnectingUserName());
	    props.put("password", ConnectingConfigurations.getConnectingPassword());
	    return driver.connect(ConnectingConfigurations.getConnectingUrl(),
	            props);
		*/
		return DriverManager.getConnection(
				ConnectingConfigurations.getConnectingUrl(),
				ConnectingConfigurations.getConnectingUserName(),
				ConnectingConfigurations.getConnectingPassword());
		
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
			//System.err.println( t.getCreatTableStatement() );
			stmt.execute(t.getCreatTableStatement());
			
		}
		con.close();
	}
void createRelations() throws SQLException {
		
		Connection con = this.conectionToDB();

		Statement stmt = con.createStatement();
		//				BD����	�ܼ�		�ܾ���	��ά
		//	������Ϣ		true	true	true
	    //	�޸�����		true	true	true
	    //	�����ͻ�		true	true	true
	    //	�޸Ŀͻ�		true	true	true
	    //	�鿴�ͻ�		true	true	true
	    //	ɾ���ͻ�		false	false	true
	    //	�ͻ���ֵ		false	true	true
	    //	��ֵ��¼�鿴	true	true	true
	    //	�����б�		true	true	true
	    //	���񶨼�		false	true	true
	    //	���۲�ѯ		true	true	true
	    //	����ͳ��		true	true	true
	    //	ʹ������		true	true	true
		
		Common.UserAccessOperation o = new UserAccessOperation();
		o.user_type = 1;	//	BD����
		o.personal_information = 1 ;	//	������Ϣ
		o.change_password = 1;	//	�޸�����
		o.create_customer = 1;	//	�����ͻ�
		o.modify_customer = 1;	//	�޸Ŀͻ�
		o.profile_customer = 1;	//	�鿴�ͻ�
		o.delete_customer = 0;	//	ɾ���ͻ�
		o.charge_customer = 0;	//	�ͻ���ֵ
		o.charge_log = 1;	//		��ֵ��¼�鿴
		o.service_list = 1;	//	�����б�
		o.service_setup_price = 0;	//	���񶨼�
		o.service_price_log = 1;	//	���۲�ѯ
		o.service_analysis = 1;	//	����ͳ��
		o.service_details = 1;	//	ʹ������
		stmt.execute("INSERT INTO " + TableConfigurations.tableNames[6] + o.getColumnNameStatement()
							+ " values " + o.getValueStatement());
		
		o.user_type = 2;	//	�ܼ�
		o.personal_information = 1 ;	//	������Ϣ
		o.change_password = 1;	//	�޸�����
		o.create_customer = 1;	//	�����ͻ�
		o.modify_customer = 1;	//	�޸Ŀͻ�
		o.profile_customer = 1;	//	�鿴�ͻ�
		o.delete_customer = 0;	//	ɾ���ͻ�
		o.charge_customer = 1;	//	�ͻ���ֵ
		o.charge_log = 1;	//		��ֵ��¼�鿴
		o.service_list = 1;	//	�����б�
		o.service_setup_price = 1;	//	���񶨼�
		o.service_price_log = 1;	//	���۲�ѯ
		o.service_analysis = 1;	//	����ͳ��
		o.service_details = 1;	//	ʹ������
		stmt.execute("INSERT INTO " + TableConfigurations.tableNames[6] + o.getColumnNameStatement()
							+ " values " + o.getValueStatement());
		
		o.user_type = 3;	//	�ܾ���
		o.personal_information = 1 ;	//	������Ϣ
		o.change_password = 1;	//	�޸�����
		o.create_customer = 1;	//	�����ͻ�
		o.modify_customer = 1;	//	�޸Ŀͻ�
		o.profile_customer = 1;	//	�鿴�ͻ�
		o.delete_customer = 1;	//	ɾ���ͻ�
		o.charge_customer = 1;	//	�ͻ���ֵ
		o.charge_log = 1;	//		��ֵ��¼�鿴
		o.service_list = 1;	//	�����б�
		o.service_setup_price = 1;	//	���񶨼�
		o.service_price_log = 1;	//	���۲�ѯ
		o.service_analysis = 1;	//	����ͳ��
		o.service_details = 1;	//	ʹ������
		stmt.execute("INSERT INTO " + TableConfigurations.tableNames[6] + o.getColumnNameStatement()
							+ " values " + o.getValueStatement());
		
		o.user_type = 4;	//	��ά
		o.personal_information = 1 ;	//	������Ϣ
		o.change_password = 1;	//	�޸�����
		o.create_customer = 1;	//	�����ͻ�
		o.modify_customer = 1;	//	�޸Ŀͻ�
		o.profile_customer = 1;	//	�鿴�ͻ�
		o.delete_customer = 1;	//	ɾ���ͻ�
		o.charge_customer = 1;	//	�ͻ���ֵ
		o.charge_log = 1;	//		��ֵ��¼�鿴
		o.service_list = 1;	//	�����б�
		o.service_setup_price = 1;	//	���񶨼�
		o.service_price_log = 1;	//	���۲�ѯ
		o.service_analysis = 1;	//	����ͳ��
		o.service_details = 1;	//	ʹ������
		stmt.execute("INSERT INTO " + TableConfigurations.tableNames[6] + o.getColumnNameStatement()
							+ " values " + o.getValueStatement());
		
		con.close();
	}
	
	private int insertQuery(Connection con, String tableName, SqlAble item) throws SQLException {
		Statement stmt = con.createStatement();
		String s = "INSERT INTO " + tableName
				+ item.getColumnNameStatement()
				+ " VALUES " + item.getValueStatement();
		return stmt.executeUpdate(s);
	}
	/**
	 * Register a new costumer in database with 0.00 money.
	 * @param customer Not all fields are needed. name, password must be included
	 * @return true if register success, false if this customer information is invalid
	 * @throws SQLException 
	 */
	public boolean inputNewCustomer(Common.CustomerInfo customer) throws SQLException {
		Connection con = this.conectionToDB();

		if ( customer.customer_loginname == null || customer.customer_password == null) {
			con.close();
			return false;
		}
		if ( customer.customer_id != null ) {	//	id should be null when insert
			con.close();
			return false;
		}
		if ( customer.customer_createdByUserId == null ) {//	customer must be created by some user
			return false;
		}
		
		/**
		 * Avoid SQL Injection Attack
		 */
		if ( !securityCheck(customer.customer_loginname) || !securityCheck(customer.customer_password)) {
			con.close();
			return false;
		}
		int r = insertQuery(con, TableConfigurations.tableNames[1], customer);
		con.close();
		return r==1;
	}
	/**
	 * @param user
	 * @return true if success
	 * @throws SQLException
	 */
	public boolean inputNewUser(Common.UserInfo user) throws SQLException {
		Connection con = this.conectionToDB();

		if ( user.user_loginName == null || user.user_password == null) {
			con.close();
			return false;
		}
		if ( user.user_id != null ) {	//	id should be null when insert
			con.close();
			return false;
		}
		
		/**
		 * Avoid SQL Injection Attack
		 */
		if ( !securityCheck(user.user_loginName) || !securityCheck(user.user_password)) {
			con.close();
			return false;
		}
		
		int r = insertQuery(con, TableConfigurations.tableNames[0], user);
		con.close();
		return r==1;
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
		b.service_id = getServiceByName(service.service_name).service_id;
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
		
		int res = insertQuery(con, TableConfigurations.tableNames[4], para);
		con.close();
		return res==1;
	}
	
	
	/**
	 * a new machine with service provided, you can call service API in this machine
	 * @param machine
	 * @return true if success
	 * @throws SQLException
	 */
	public boolean inputNewMachine( Common.machine machine ) throws SQLException {
		Connection con = this.conectionToDB();
		
		int res = insertQuery(con, TableConfigurations.tableNames[7], machine);
		con.close();
		return res==1;
	}
	/**
	 * Input a new log and return it's id 
	 * @param log
	 * @return
	 * @throws SQLException
	 */
	public Long inputNewApiLog( Common.APILog log ) throws SQLException {
		Connection con = this.conectionToDB();
		con.setAutoCommit(false);
		Statement stmt = con.createStatement();
		Long res ;
		
		String s = "INSERT INTO " + TableConfigurations.tableNames[9]
				+ log.getColumnNameStatement()
				+ " VALUES " + log.getValueStatement();
		stmt.executeUpdate(s);
		//int res = insertQuery(con, TableConfigurations.tableNames[9], log);
		
		ResultSet r = stmt.executeQuery("SELECT LAST_INSERT_ID()");
		r.next();
		res = r.getLong("LAST_INSERT_ID()");
		con.commit();
		con.close();
		return res;
	}
	/**
	 * Set a customer's money into @money
	 * @param customerName
	 * @param money
	 * @return true if success
	 * @throws SQLException
	 */
	public boolean setIncreaseUserMoney(String customer_loginname, int money) throws SQLException {
		Connection con = this.conectionToDB();
		Statement stmt = con.createStatement();
		String s = " UPDATE " + TableConfigurations.tableNames[1]
				+ " SET customer_balance = customer_balance + " + money
				+ " WHERE customer_loginname = " + "'" + customer_loginname + "'"; 
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
	 * @param loginName
	 * @return information for this customer if existed. null if user not found 
	 * @throws SQLException
	 */
	public CustomerInfo getCustomerInfo(String loginName) throws SQLException{
		Connection con = this.conectionToDB();

		Statement stmt = con.createStatement();
		ResultSet res =  stmt.executeQuery("SELECT * FROM " 
								+ TableConfigurations.tableNames[1]
								+ " WHERE "
								+ " customer_loginname = " 
								+ "'" + loginName + "'");
		
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
	public Common.machine getNextFreeMachine(String serviceName) throws SQLException {
		Connection con = this.conectionToDB();
		
		try {
			con.setAutoCommit(false);
			//	load current free machine
			Common.loadbalance l = new loadbalance();
			Common.machine machine = new machine();
			
			Common.Service service = getServiceByName(serviceName);
			
			ResultSet r1 = con.createStatement().executeQuery("SELECT * FROM " 
					+ TableConfigurations.tableNames[8]
							+ " WHERE service_id = " + service.service_id);
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
					+ " WHERE service_id = " + service.service_id; 
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
	/**
	 * 
	 * @param whereCondition could be null, or "status = \'xxx\'"
	 * @return result
	 * @throws SQLException
	 */
	public int getCountAPILog(String whereCondition) throws SQLException {
		Connection con = this.conectionToDB();
		Statement stmt = con.createStatement();
		String s = "SELECT count(*) FROM " 
				+ TableConfigurations.tableNames[9];
		if ( whereCondition != null )
			s += "WHERE " + whereCondition; 
		ResultSet res =  stmt.executeQuery(s);
		res.next();
		int r = res.getInt(1);
		con.close();
		return r;
	}
	
	/**
	 * Find all users created or indirectly created by a user.
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public java.util.ArrayList<Common.UserInfo> getDownStreamUsers(int userId) throws SQLException {
		java.util.ArrayList<Common.UserInfo> userIds = new ArrayList<Common.UserInfo>();
		java.util.HashMap<Integer, java.util.ArrayList<Integer> > edge = new java.util.HashMap<Integer, java.util.ArrayList<Integer>>();
		java.util.HashMap<Integer, Common.UserInfo> information = new HashMap<Integer, Common.UserInfo>();
		
		Connection con = this.conectionToDB();
		Statement stmt = con.createStatement();
		String s = "SELECT * FROM " 
				+ TableConfigurations.tableNames[0];
		ResultSet res = stmt.executeQuery(s);
		while ( res.next() ) {
			Integer st, ed;
			ed = res.getInt("user_id");
			st = res.getInt("user_createdById");
			
			if ( !res.wasNull() ) {
				System.err.println(st + " --> " + ed);
				if ( !edge.containsKey(st) )
					edge.put(st, new java.util.ArrayList<>());
				edge.get(st).add(ed);
			}
			Common.UserInfo u = new UserInfo();
			u.fetchFromResultSet(res);
			information.put(ed, u);
			if ( u.user_id == userId )
				userIds.add(u);
		}
		con.close();
		
		for ( int i = 0 ; i < userIds.size() ; ++i ) {
			ArrayList<Integer> t = edge.get( userIds.get(i).user_id );
			if ( t != null ) {
				for ( Integer j : t ) {
					userIds.add( information.get(j) );
				}
			}
		}
		return userIds;
	}
	
	/**
	 * Find all customers created or indirectly created by a user.
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public java.util.ArrayList<Common.CustomerInfo> getDownStreamCustomers(int userId) throws SQLException {
		java.util.ArrayList<Common.CustomerInfo> customerIds = new ArrayList<Common.CustomerInfo>();
		java.util.HashMap<Integer, java.util.ArrayList<Integer> > edge = new java.util.HashMap<Integer, java.util.ArrayList<Integer>>();
		java.util.HashMap<Integer, Common.CustomerInfo> information = new HashMap<Integer, Common.CustomerInfo>();
		
		Connection con = this.conectionToDB();
		Statement stmt = con.createStatement();
		String s = "SELECT * FROM " 
				+ TableConfigurations.tableNames[1];
		ResultSet res = stmt.executeQuery(s);
		while ( res.next() ) {
			int st, ed;
			ed = res.getInt("customer_id");
			st = res.getInt("customer_createdByUserId");
			
			if ( !edge.containsKey(st) )
				edge.put(st, new java.util.ArrayList<>());
			edge.get(st).add(ed);
			Common.CustomerInfo c = new CustomerInfo();
			c.fetchFromResultSet(res);
			information.put(ed, c);
		}
		con.close();
		
		java.util.ArrayList<Common.UserInfo> us = this.getDownStreamUsers(userId);
		for ( Common.UserInfo u : us ) {
			ArrayList<Integer> l = edge.get(u.user_id);
			if ( l != null )
			for ( Integer nn : l ) {
				customerIds.add(information.get(nn));
			}
		}
		
		return customerIds;
	}
	
	
	
	/*
	public ArrayList<Common.APILog> getApiLogByCustomerName(String customer_loginname) throws SQLException {
		ArrayList<Common.APILog> res = new ArrayList<>();
		
		Connection con = this.conectionToDB();
		ResultSet r1 = con.createStatement().executeQuery("SELECT * FROM " 
				+ TableConfigurations.tableNames[9]
						+ " WHERE customer_loginname = " + "'" + customer_loginname + "'");
		while ( r1.next() ) {
			Common.APILog log = new APILog();
			log.fetchFromResultSet(r1);
			res.add(log);
		}
		con.close();
		return res;
	}
	*/
}

