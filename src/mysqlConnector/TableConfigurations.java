package mysqlConnector;

import java.util.ArrayList;

public final class TableConfigurations {
	static ArrayList<Table> tables = new ArrayList<Table>();
	/**
	 * Table lists:
	 * 0. accounts;
	 * 1. services - (serviceId,serviceName,servicePort,servicefee);
	 * 2. user_services - (userName,serviceName,fee);
	 * 3. apiLog - (logId,userName,time,log);
	 * 4. servicePara - (serviceName,paraName,paraType);
	 * 5. ipWhiteList - (ip);
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
		String varcharMAX = "VARCHAR(8000)";
		String INT = "INT";
		String LONG = "BIGINT";
		
		/*
		 * Account table
		 * One user per row
		 */
		Table accountTable = new Table(tableNames[0]);
		accountTable.addSchemaWithAutoIncrease("userId", INT);	// unique userId will be set automaticlly	
		accountTable.addSchema("nickName", varchar255);	//	The real name of user
		accountTable.addSchema("userName", varchar255, false, true, true);	//	Account name
		accountTable.addSchema("password", varchar255);	//	Encrypted password
		accountTable.addSchema("email", varchar255);
		accountTable.addSchema("companyAddress", varchar255);
		accountTable.addSchema("SignUpTime", "DATE");
		accountTable.addSchema("lastLogInTime", "DATE");
		accountTable.addSchema("Money", INT);	//	Money should be integer by default
		accountTable.addSchema("priviligeLevel", INT);	//	Level 1,2,3.. for managers. Level 0 for users
		accountTable.addSchema("ActiveOrNot", INT);		//	0 or 1
		accountTable.addSchema("secretKey", varchar255);
		accountTable.addSchema("companyName", varchar255);
		accountTable.addSchema("phoneNumber", varchar255);
		
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
		userToServices.addSchema("userName", varchar255, false, true, true);
		userToServices.addSchema("serviceName", varchar255, false, true, true);
		userToServices.addSchema("fee", INT);
		tables.add(userToServices);
		
		/*
		 * user-log table
		 */
		Table apiLog = new Table(tableNames[3]);
		apiLog.addSchemaWithAutoIncrease("logId", LONG);
		apiLog.addSchema("userName", varchar255, false, true, true);
		apiLog.addSchema("date", "DATE", false, true);
		apiLog.addSchema("log", varcharMAX);	//	TODO log??
		tables.add(apiLog);
		
		
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
