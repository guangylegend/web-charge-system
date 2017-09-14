package Common;

public class loadbalance extends SqlAble{
	public Integer service_id;
	public Integer machine_id;
	public loadbalance() {}
	@Override
	public String getTableName() {
		return mysqlConnector.TableConfigurations.tableNames[8];
	}
}
