package Common;

public class loadbalance extends SqlAble{
	public String service_name;
	public Integer machine_id;
	public loadbalance() {}
	@Override
	public String getTableName() {
		return mysqlConnector.TableConfigurations.tableNames[8];
	}
}
