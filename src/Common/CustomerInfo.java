package Common;

public class CustomerInfo extends SqlAble{
	public Integer customer_id;	//	auto inc
	public String customer_name;	//	customer's name
	public String customer_password;
	public Integer customer_banlance;	//	1 RMB = 100, money remained
	public String customer_ip;	//	xx.xx.xx.xxs
	public Integer customer_type;	//	@TODO wtf
	public String customer_servicekey;
	public String customer_contactName;
	public String customer_areaId;
	public Integer customer_createdByUserId;
	
	public CustomerInfo() {}
}
