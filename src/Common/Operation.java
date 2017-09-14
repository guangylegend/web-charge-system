package Common;

import mysqlConnector.TableConfigurations;

public class Operation extends SqlAble{
	public Integer operation_id;
	public String name;
	@Override
	public String getTableName() {
		return TableConfigurations.tableNames[13];
	}
}
