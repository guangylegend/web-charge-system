package Common;

public class ServicePara extends SqlAble{
	
	public String service_name;
	public String para_name;
	public String para_type;
	
	public ServicePara() {}
	public ServicePara setServiceName(String t) {
		service_name = new String(t);
		return this;
	}
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
