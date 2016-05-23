package mysqlConnector;
/*
 * Connection settings
 */
public final class ConnectingConfigurations {
	static String[] hostIp = {"139.196.242.196","139.196.242.221"};
	static String userName = "sync";
	static String password = "Minivision-2015";
	static String dbName = "Minivision";
	
	static public String validCheckUrl(int index) {
		return "jdbc:mysql://" + hostIp[index] ;
	}
	
	static public String getConnectingUrl() {
		//return "jdbc:mysql://" + hostIp[index] ;
		String s = "jdbc:mysql:loadbalance://";
		//s += getHostIp(0) + "," + getHostIp(1);
		
		for ( String i : hostIp ) {
			//s += "address=(type=master)(host=" + getHostIp(0) + ")" + ",";
			//s += "address=(type=slave)(host=" + getHostIp(0) + ")" + ",";
			s += i + ",";
			
			break;
		}
		
		if ( s.endsWith(","))
			s = s.substring(0, s.length()-1);
		return s ;
	}
	static public String getConnectingUrlWithDatabaseName() {
		return getConnectingUrl() + "/" + dbName;
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
