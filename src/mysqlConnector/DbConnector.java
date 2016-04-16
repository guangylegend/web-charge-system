package mysqlConnector;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.security.auth.login.Configuration;
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
	 * Create all the tables in TableConfigurations
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
	
	
}
