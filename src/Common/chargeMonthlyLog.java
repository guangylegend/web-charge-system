package Common;

import java.util.Date;

import mysqlConnector.TableConfigurations;

public class chargeMonthlyLog extends SqlAble{
	public Long sid;
	public Integer user_id;
	public Integer customer_id;
	public Integer charge_value;
	public Integer additional_chargevalue;
	public Date chargeDate;
	public Date startDate;
	public Date endDate;
	public String description;

	@Override
	public String getTableName() {
		return TableConfigurations.tableNames[12];
	}
}
