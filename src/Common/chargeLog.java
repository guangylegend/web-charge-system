package Common;

import java.sql.Date;

public class chargeLog extends SqlAble{
	public Long sid;
	public String customer_name;
	public Integer old_balance;
	public Integer new_balance;
	public Integer charge_value;
	public Integer additional_chargevalue;
	public Date date;
	public String user_name;	//	charged by this user
	public String description;	//	description
	@Override
	public String getTableName() {
		return "charge_log";
	}
	
}
