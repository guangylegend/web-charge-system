package mysqlConnector;
/*
 * Connection settings
 */
public final class ConnectingConfigurations {
	static String hostIp = "139.196.152.71";
	static String userName = "user";
	static String password = "123qwe";
	static String dbName = "test";
	
	static public String getConnectingUrl() {
		return "jdbc:mysql://" + hostIp + "/" + dbName;
	}
	static public String getConnectingUserName() {
		return userName;
	}
	static public String getConnectingPassword() {
		return password;
	}
	static public String getConnectingDatabaseName() {
		return dbName;
	}
}
