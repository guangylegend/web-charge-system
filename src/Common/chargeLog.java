package Common;

import java.util.Date;

public class chargeLog extends SqlAble{
	public Long sid;
	public Integer old_balance;
	public Integer new_balance;
	public Integer charge_value;
	public Integer additional_chargevalue;
	public Date date;
	public Integer customer_id;
	public Integer user_id;	//	charged by this user
	public String description;	//	description
	@Override
	public String getTableName() {
		return "charge_log";
	}
	
}
