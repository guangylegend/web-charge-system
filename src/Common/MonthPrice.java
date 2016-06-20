package Common;

import java.util.Date;

import mysqlConnector.TableConfigurations;

public class MonthPrice extends SqlAble{
	public Long sid;
	public Integer user_id;
	public Integer customer_id;
	public Integer service_id;
	public Integer value;
	public Date startDate;
	public Date endDate;

	@Override
	public String getTableName() {
		return TableConfigurations.tableNames[12];
	}
}
