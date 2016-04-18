package mysqlConnector;

import java.util.ArrayList;

public final class TableConfigurations {
	/**
	 * Table lists:
	 * 1. accounts
	 * 2. services
	 * 3. user_services
	 * 4. userLog
	 */
	static ArrayList<Table> tables = new ArrayList<Table>();
	static String[] tableNames = new String[]{"accounts","services","user_services","userLog"};
	static boolean first = true ;
	
	public static void generateAllTables() {
		
		if ( !first )
			return ;
		first = false;
		
		String varchar255 = "VARCHAR(255)";
		String varcharMAX = "VARCHAR(MAX)";
		String INT = "INT";
		
		/*
		 * accounts table
		 */
		Table accountTable = new Table(tableNames[0]);
		accountTable.addSchema("userId", INT, true, false);
		accountTable.addSchema("userName", varchar255);	//	The real name of user
		accountTable.addSchema("loginName", varchar255, false, true, true);	//	Account name
		accountTable.addSchema("password", varchar255);	//	Encrypted password
		accountTable.addSchema("email", varchar255);
		accountTable.addSchema("companyAddress", varchar255);
		accountTable.addSchema("SignUpTime", "DATE");
		accountTable.addSchema("lastLogInTime", "DATE");
		accountTable.addSchema("remainedMoney", INT);	//	Money should be integer by default
		accountTable.addSchema("priviligeLevel", INT);	//	Level 1,2,3.. for managers. Level 0 for users
		accountTable.addSchema("ActiveOrNot", INT);		//	0 or 1
		tables.add(accountTable);
		
		/*
		 * services table
		 */
		Table services = new Table(tableNames[1]);
		services.addSchema("serviceId", INT, true, false);
		services.addSchema("serviceName", varchar255);
		services.addSchema("servicePort", INT);
		tables.add(services);
		
		/*
		 * user-service table
		 */
		Table userToServices = new Table(tableNames[2]);
		userToServices.addSchema("userId", INT, false, true);
		userToServices.addSchema("serviceId", INT, false, true);
		userToServices.addSchema("fee", INT);
		tables.add(userToServices);
		
		/*
		 * user-log table
		 */
		Table userLog = new Table(tableNames[3]);
		userLog.addSchema("logId", INT, true, false);
		userLog.addSchema("userId", INT, false, true);
		userLog.addSchema("time", "DATE", false, true);
		userLog.addSchema("Log", varchar255);	//	TODO log??
		tables.add(userLog);
	}
}
