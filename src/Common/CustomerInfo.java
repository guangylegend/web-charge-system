package Common;

public class CustomerInfo extends SqlAble{
	public Integer customer_id;	//	auto inc
	public String customer_name;	//	customer's name
	public String customer_loginname;
	public String customer_password;
	public Integer customer_balance;	//	1 RMB = 100, money remained
	public String customer_ip;	//	xx.xx.xx.xxs
	public String customer_type;	//	@TODO wtf
	public String customer_servicekey;
	public String customer_contactName;
	public String customer_areaId;
	public Integer customer_createdByUserId;
	
	public String customer_contactmail;
	public String customer_contactphone;
	public String customer_BDmail;
	public String customer_BDname;
	public String customer_BDphone;
	
	public CustomerInfo() {}
	@Override
	public String getTableName() {
		return mysqlConnector.TableConfigurations.tableNames[1];
	}
}
