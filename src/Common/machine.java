package Common;

public class machine extends SqlAble {
	public String ip;
	public Integer machine_id;	//	set null when doing insert
	public machine() {}
	@Override
	public String getTableName() {
		return mysqlConnector.TableConfigurations.tableNames[7];
	}
}
