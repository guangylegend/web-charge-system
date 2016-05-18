package mysqlConnector;
/*
 * Connection settings
 */
public final class ConnectingConfigurations {
	static String[] hostIp = {"139.196.242.196","139.196.242.221"};
	static String userName = "sync";
	static String password = "Minivision-2015";
	static String dbName = "Minivision";
	
	static public String getConnectingUrl(int index) {
		return "jdbc:mysql://" + hostIp[index] ;
	}
	static public String getConnectingUrlWithDatabaseName(int index) {
		return getConnectingUrl(index) + "/" + dbName;
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
	static public int getHostSize() {
		return hostIp.length;
	}
	static public String getHostIp(int index) {
		return hostIp[index];
	}
}
