package mysqlConnector;
/*
 * Connection settings
 */
public final class ConnectingConfigurations {
	
	static String[] hostIp = {"139.196.242.196","139.196.242.221"};
	static String userName = "sync";
	static String password = "Minivision-2015";
	static String dbName = "Minivision";
	/*
	static String[] hostIp = {"139.196.242.196"};
	static String userName = "test";
	static String password = "test";
	static String dbName = "foo";
	*/
	
	static public String validCheckUrl(int index) {
		return "jdbc:mysql://" + hostIp[index] ;
	}
	static public String getCoreURL() {
		String s = "jdbc:mysql:loadbalance://";
		for ( String i : hostIp ) {
			s += i + ",";
			break;
		}
		
		if ( s.endsWith(","))
			s = s.substring(0, s.length()-1);
		return s ;
	}
	
	static public String getConnectingUrl() {
		return getCoreURL() + "?" 
				+ "user=" + userName
				+ "&password=" + password
				//+ "&useUnicode=true"
				//+ "&characterEncoding=uft8"
				;
	}
	static public String getConnectingUrlWithDatabaseName() {
		return getCoreURL() + "/" + dbName + "?" 
				+ "user=" + userName
				+ "&password=" + password
				//+ "&useUnicode=true"
				//+ "&characterEncoding=utf8"
				;
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
