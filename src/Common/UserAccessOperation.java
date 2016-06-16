package Common;

public class UserAccessOperation extends SqlAble{
	public Integer user_type;
	public Integer operation_id;
	@Override
	public String getTableName() {
		return mysqlConnector.TableConfigurations.tableNames[14];
	}
}
