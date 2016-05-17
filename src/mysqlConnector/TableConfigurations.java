package mysqlConnector;

import java.util.ArrayList;

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
	 */
	static String[] tableNames = new String[]{"credit_user"
									,"credit_customer"
									,"credit_service"
									,"customer_service"
									,"service_para"
									,"user_type_list"
									,"user_access"
									};
	static boolean first = true ;
	
	public static void generateAllTables() {
		
		if ( !first )
			return ;
		first = false;
		
		String varchar255 = "VARCHAR(255)";
		String varcharMAX = "VARCHAR(8000)";
		String INT = "INT";
		String LONG = "BIGINT";
		
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
				
				tables.add(userTable);
			}
			/**
			 * customer table
			 */
			else if ( i.equals("credit_customer")) {
				Table customerTable = new Table(i);
				customerTable.addSchema("customer_id", INT).setAutoInc();
				customerTable.addSchema("customer_name", varchar255).setIndex().setUnique();
				customerTable.addSchema("customer_banlance", INT);
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
				services.addSchema("service_name", varchar255).setIndex().setUnique();
				services.addSchema("service_createTime", "DATE");
				services.addSchema("service_desc", varchar255);				
				services.addSchema("service_guidePrice", INT);
				services.addSchema("service_speCharging", varcharMAX);
				services.addSchema("service_nickname", varchar255);
				tables.add(services);
			}
			
			/*
			 * user-service table
			 * 
			 */
			else if ( i.equals("customer_service")) {
				Table userToServices = new Table(i);
				userToServices.addSchema("customer_name", varchar255).setIndex();
				userToServices.addSchema("service_name", varchar255).setIndex();
				userToServices.addSchema("fee", INT);
				tables.add(userToServices);
			}
			
			/*
			 * service-parameters table
			 */
			else if ( i.equals("service_para")) {
				Table servicePara = new Table(i);
				servicePara.addSchema("service_name", varchar255);
				servicePara.addSchema("para_name", varchar255);
				servicePara.addSchema("para_type", varchar255);
				tables.add(servicePara);
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
		}
	}
}
