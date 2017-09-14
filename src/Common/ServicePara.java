package Common;

public class ServicePara extends SqlAble{
	
	public Integer service_id;
	public String para_name;
	public String para_type;
	
	public ServicePara() {}
	
	public ServicePara setParaName(String t) {
		para_name = new String(t);
		return this;
	}
	public ServicePara setParaType(String t) {
		para_type = new String(t);
		return this;
	}
	@Override
	public String getTableName() {
		return mysqlConnector.TableConfigurations.tableNames[4];
	}
}
