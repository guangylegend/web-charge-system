package mysqlConnector;

import java.util.ArrayList;

public final class TableConfigurations {
	static ArrayList<Table> tables = new ArrayList<Table>();
	static boolean first = true ;
	
	public static void generateAllTables() {
		
		if ( !first )
			return ;
		first = false;
		
		Table table;
		String varchar255 = "VARCHAR(255)";
		String varcharMAX = "VARCHAR(MAX)";
		String INT = "INT";
		
		/*
		 * user/manager table
		 * all information about accounts
		 */
		table = new Table("accounts");
		table.addSchema("userId", INT, true, false);
		table.addSchema("userName", varchar255);	//	The real name of user
		table.addSchema("loginName", varchar255);	//	Account name
		table.addSchema("password", varchar255);	//	Encrypted password
		table.addSchema("email", varchar255);
		table.addSchema("companyAddress", varchar255);
		table.addSchema("SignUpTime", "DATE");
		table.addSchema("lastLogInTime", "DATE");
		table.addSchema("remainedMoney", INT);	//	Money should be integer by default
		
		table.addSchema("priviligeLevel", INT);	//	Level 1,2,3.. for managers. Level 0 for users
		table.addSchema("ActiveOrNot", INT);		//	0 or 1
		tables.add(table);
		
		/*
		 * services table
		 * manage service
		 */
		Table services = new Table("services");
		services.addSchema("serviceId", INT, true, false);
		services.addSchema("serviceName", varchar255);
		services.addSchema("servicePort", INT);
		tables.add(services);
		
		/*
		 * user-service table
		 */
		Table userToServices = new Table("user_services");
		userToServices.addSchema("userId", INT, false, true);
		userToServices.addSchema("serviceId", INT, false, true);
		userToServices.addSchema("fee", INT);
		tables.add(userToServices);
		
		/*
		 * user-log table
		 */
		Table userLog = new Table("userLog");
		userToServices.addSchema("logId", INT, true, false);
		userToServices.addSchema("userId", INT, false, true);
		userToServices.addSchema("time", "DATE", false, true);
		userToServices.addSchema("Log", varchar255);	//	TODO log??
		tables.add(userToServices);
	}
}
