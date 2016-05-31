package Common;

import java.util.Date;

import mysqlConnector.TableConfigurations;

public class PriceLog extends SqlAble{
	public Long sid;
	public Integer service_id;
	public Integer cutsomer_id;
	public Integer old_price;
	public Integer new_price;
	public Integer user_id;	//	price is changed by thi user
	public Date date;	// log date
	
	public Date free_until;	//	if this filed is null, pay for each query
	
	@Override
	public String getTableName() {
		return TableConfigurations.tableNames[10];
	}
	
}
