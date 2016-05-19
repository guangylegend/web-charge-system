package Common;

public class machineList extends SqlAble {
	public String ip;
	public Integer machine_id;	//	set null when doing insert
	public machineList() {}
	@Override
	public String getTableName() {
		return mysqlConnector.TableConfigurations.tableNames[7];
	}
}
