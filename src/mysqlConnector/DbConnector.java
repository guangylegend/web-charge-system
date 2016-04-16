package mysqlConnector;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.security.auth.login.Configuration;

import java.sql.ResultSet;

public class DbConnector {
	Connection con = null ;
	Statement stmt;
	// Connect to remote mysql database
	// See ConnectingConfigurations for details
	
	DbConnector() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(
				ConnectingConfigurations.getConnectingUrl(),
				ConnectingConfigurations.getConnectingUserName(),
				ConnectingConfigurations.getConnectingPassword());
	}
	void Test() throws SQLException {
		
		stmt = con.createStatement();
		stmt.execute("CREATE TABLE test(id int)");
		stmt.execute("DROP TABLE test");
	}
	
	/*
	 * Call it to clear and initialize everything
	 */
	public void init() throws SQLException {
		clear();
		createTables();
	}
	/*
	 * Delete everything in the database! 
	 */
	void clear() throws SQLException{
		stmt = con.createStatement();
		stmt.execute("DROP DATABASE " + ConnectingConfigurations.getConnectingDatabaseName());
	}
	void createTables() throws SQLException {
		Statement stmt = con.createStatement();
		TableConfigurations.generateAllTables();
		for ( int i = 0 ; i < TableConfigurations.tables.size() ; ++i ) {
			Table t = TableConfigurations.tables.get(i);
			stmt.execute(t.getCreatTableStatement());
		}
	}
}
