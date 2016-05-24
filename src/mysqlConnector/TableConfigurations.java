package mysqlConnector;

import java.util.ArrayList;
import java.util.Date;

public final class TableConfigurations {
	static ArrayList<Table> tables = new ArrayList<Table>();
	/**
	 * Table lists:
	 * 0. credit_user - ();
	 * 1. credit_customer - ();
	 * 2. credit_service - ();
	 * 3. customer_services - ();
	 * 4. service_para - ();
	 * 5. user_type_list - (user_type,description)
	 * 6. user_access - ();
	 * 7. machine_list - (ip,machine_id)
	 * 8. load_balance - (service_name,machine_id)
	 * 9. api_log - ();
	 * 10. price_log - ()
	 * 11. charge_log - ()
	 */
	static public String[] tableNames = new String[]{"credit_user"
									,"credit_customer"
									,"credit_service"
									,"customer_service"
									,"service_para"
									,"user_type_list"
									,"user_access"
									,"machine_list"
									,"load_balance"
									,"api_log"
									,"price_log"
									,"charge_log"
									};
	static boolean first = true ;
	
	public static void generateAllTables() {
		
		if ( !first )
			return ;
		first = false;
		
		String varchar30 = "VARCHAR(30)";
		String varchar255 = "VARCHAR(255)";
		String varcharMAX = "VARCHAR(3000)";
		String INT = "INT";
		String LONG = "BIGINT";
		String DATE = "TIME";
		
		for ( String i : tableNames ) {
			/*
			 * user table
			 */
			if ( i.equals("credit_user") ) {
				Table userTable = new Table(i);
				userTable.addSchema("user_id", INT).setAutoInc(); // unique userId will be set automaticlly	
				userTable.addSchema("user_name", varchar255);	//	The real name of user
				userTable.addSchema("user_loginName", varchar255).setIndex().setUnique();	// login name
				userTable.addSchema("user_password", varchar255);	//	Encrypted password
				userTable.addSchema("user_isDelete", INT);
				userTable.addSchema("user_mailAddress", varchar255);
				userTable.addSchema("user_phoneNum",varchar255);
				userTable.addSchema("user_type",INT);	//	see role table for details 
				userTable.addSchema("user_desc",varchar255);	//	description
				userTable.addSchema("company_id",varchar255);
				userTable.addSchema("office_id",varchar255);
				userTable.addSchema("email",varchar255);
				userTable.addSchema("phone",varchar255);
				userTable.addSchema("login_ip",varchar255);
				userTable.addSchema("login_date",DATE);
				userTable.addSchema("login_flag",varchar255);
				userTable.addSchema("remarks",varchar255);
				
				tables.add(userTable);
			}
			/**
			 * customer table
			 */
			else if ( i.equals("credit_customer")) {
				Table customerTable = new Table(i);
				customerTable.addSchema("customer_id", INT).setAutoInc();
				customerTable.addSchema("customer_name", varchar255);
				customerTable.addSchema("customer_loginname", varchar255).setIndex().setUnique();
				customerTable.addSchema("customer_password", varchar255);
				customerTable.addSchema("customer_balance", INT);
				customerTable.addSchema("customer_ip", varchar255);
				customerTable.addSchema("customer_type", INT);	//	@TODO wtf
				customerTable.addSchema("customer_servicekey", varchar255);
				customerTable.addSchema("customer_contactName", varchar255);
				customerTable.addSchema("customer_areaId", varchar255);
				customerTable.addSchema("customer_createdByUserId", INT);
				tables.add(customerTable);
			}
			/*
			 * services table
			 */
			else if ( i.equals("credit_service")) {
				Table services = new Table(i);
				services.addSchema("service_id", INT).setAutoInc();
				services.addSchema("service_name", varchar255).setIndex();
				services.addSchema("service_createTime", DATE);
				services.addSchema("service_desc", varchar255);				
				services.addSchema("service_guidePrice", INT);
				services.addSchema("service_speCharging", varcharMAX);
				services.addSchema("service_nickname", varchar255);
				services.addSchema("external_URL", varchar255);
				services.addSchema("internal_URL", varchar255);
				
				tables.add(services);
			}
			
			/*
			 * user-service table
			 * 
			 */
			else if ( i.equals("customer_service")) {
				Table userToServices = new Table(i);
				userToServices.addSchema("customer_id", INT);
				userToServices.addSchema("service_id", INT);
				userToServices.addSchema("fee", INT);
				tables.add(userToServices);
			}
			
			else if ( i.equals("user_access")) {
				Table userAccess = new Table(i);
				userAccess.addSchema("user_type",INT);
				userAccess.addSchema("personal_information",INT); //	个人信息
				userAccess.addSchema("change_password",INT); //	修改密码
				userAccess.addSchema("create_customer",INT); //	新增客户
				userAccess.addSchema("modify_customer",INT); //	修改客户
				userAccess.addSchema("profile_customer",INT); //	查看客户
				userAccess.addSchema("delete_customer",INT); //	删除客户
				userAccess.addSchema("charge_customer",INT); //	客户充值
				userAccess.addSchema("charge_log",INT); //		充值记录查看
				userAccess.addSchema("service_list",INT); //	服务列表
				userAccess.addSchema("service_setup_price",INT); //	服务定价
				userAccess.addSchema("service_price_log",INT);  //	定价查询
				userAccess.addSchema("service_analysis",INT); //	服务统计
				userAccess.addSchema("service_details",INT); //	使用详情
				tables.add(userAccess);
			}
			else if ( i.equals("machine_list")) {
				Table machine_id = new Table(i);
				machine_id.addSchema("machine_id", INT).setAutoInc();
				machine_id.addSchema("ip", varchar255);
				tables.add(machine_id);
			}
			else if ( i.equals("load_balance")) {
				Table table = new Table(i);
				table.addSchema("machine_id", INT);
				table.addSchema("service_id", INT);
				tables.add(table);
			}
			else if ( i.equals("api_log")) {
				Table table = new Table(i);
				table.addSchema("sid",LONG).setAutoInc();
				table.addSchema("date", DATE);
				table.addSchema("customer_id", INT);
				table.addSchema("service_id", INT);
				table.addSchema("input", varcharMAX);
				table.addSchema("output", varcharMAX);
				table.addSchema("cost", INT);
				table.addSchema("host", varchar255);
				table.addSchema("exception", varcharMAX);
				table.addSchema("status", varchar255);
				tables.add(table);
			}
			else if ( i.equals("price_log")) {
				Table table = new Table(i);
				table.addSchema("sid",LONG).setAutoInc();
				table.addSchema("date", DATE);
				table.addSchema("service_id", INT);
				table.addSchema("cutsomer_id", INT);
				table.addSchema("user_id", INT);
				table.addSchema("new_price", INT);
				table.addSchema("old_price", INT);
				tables.add(table);
			}
			else if ( i.equals("charge_log")) {
				Table table = new Table(i);
				table.addSchema("sid",LONG).setAutoInc();
				table.addSchema("date", DATE);
				table.addSchema("user_id", INT);
				table.addSchema("customer_id", INT);
				table.addSchema("description", varchar255);
				table.addSchema("new_balance", INT);
				table.addSchema("old_balance", INT);
				table.addSchema("charge_value", INT);
				table.addSchema("additional_chargevalue", INT);
				
				tables.add(table);
			}
		}
	}
}
