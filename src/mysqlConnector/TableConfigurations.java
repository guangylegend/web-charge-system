package mysqlConnector;

import java.util.ArrayList;

public final class TableConfigurations {
	static ArrayList<Table> tables = new ArrayList<Table>();
	/**
	 * Table lists:
	 * 0. accounts;
	 * 1. services - (serviceId,serviceName,servicePort,servicefee);
	 * 2. user_services - (loginName,serviceName,fee);
	 * 3. userLog;
	 * 4. servicePara - (serviceName,paraName,paraType);
	 * 5. ipWhiteList;
	 */
	static String[] tableNames = new String[]{"accounts"
									,"services"
									,"user_services"
									,"userLog"
									,"servicePara"
									,"ipWhiteLists"};
	static boolean first = true ;
	
	public static void generateAllTables() {
		
		if ( !first )
			return ;
		first = false;
		
		String varchar255 = "VARCHAR(255)";
		String varcharMAX = "VARCHAR(MAX)";
		String INT = "INT";
		
		/*
		 * Account table
		 * One user per row
		 */
		Table accountTable = new Table(tableNames[0]);
		accountTable.addSchemaWithAutoIncrease("userId", INT);	// unique userId will be set automaticlly	
		accountTable.addSchema("userName", varchar255);	//	The real name of user
		accountTable.addSchema("loginName", varchar255, false, true, true);	//	Account name
		accountTable.addSchema("password", varchar255);	//	Encrypted password
		accountTable.addSchema("email", varchar255);
		accountTable.addSchema("companyAddress", varchar255);
		accountTable.addSchema("SignUpTime", "DATE");
		accountTable.addSchema("lastLogInTime", "DATE");
		accountTable.addSchema("Money", INT);	//	Money should be integer by default
		accountTable.addSchema("priviligeLevel", INT);	//	Level 1,2,3.. for managers. Level 0 for users
		accountTable.addSchema("ActiveOrNot", INT);		//	0 or 1
		tables.add(accountTable);
		
		/*
		 * services table
		 */
		Table services = new Table(tableNames[1]);
		services.addSchemaWithAutoIncrease("serviceId", INT);
		services.addSchema("serviceName", varchar255, false, true, true);
		services.addSchema("servicePort", INT);
		services.addSchema("serviceFee", INT);
		tables.add(services);
		
		/*
		 * user-service table
		 */
		Table userToServices = new Table(tableNames[2]);
		userToServices.addSchema("loginName", varchar255, false, true, true);
		userToServices.addSchema("serviceName", varchar255, false, true, true);
		userToServices.addSchema("fee", INT);
		tables.add(userToServices);
		
		/*
		 * user-log table
		 */
		Table userLog = new Table(tableNames[3]);
		userLog.addSchemaWithAutoIncrease("logId", INT);
		userLog.addSchema("userName", INT, false, true, true);
		userLog.addSchema("time", "DATE", false, true);
		userLog.addSchema("Log", varchar255);	//	TODO log??
		tables.add(userLog);
		
		
		/*
		 * service-parameters table
		 */
		Table servicePara = new Table(tableNames[4]);
		servicePara.addSchema("serviceName", varchar255, false, true);
		servicePara.addSchema("paraName", varchar255);
		servicePara.addSchema("paraType", varchar255);
		tables.add(servicePara);
		
		/*
		 * ip white listss table
		 */
		Table ipList = new Table(tableNames[5]);
		ipList.addSchema("ip", varchar255);
		tables.add(ipList);
		
		
	}
}
