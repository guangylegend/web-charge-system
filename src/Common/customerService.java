package Common;

import mysqlConnector.TableConfigurations;

public class customerService extends SqlAble{
	public Integer customer_id;
	public Integer service_id;
	public Integer fee;
	public Integer isActive;
	public Integer is_pay_each_time;	//	
	public java.util.Date free_until;	//	if @is_pay_each_time == false, see this for valid date
	@Override
	public String getTableName() {
		return TableConfigurations.tableNames[3];
	}
}
