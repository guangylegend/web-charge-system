package Common;

import java.util.Date;

public class APILog extends SqlAble {
	public Long sid;
	public Date date;
	public Integer customer_id;
	public Integer service_id;
	public String input;
	public String output;
	public Integer cost;
	public String host;	//	请求主机ip
	public String exception;	//	异常
	public String status;	//		pending/processing/ok/fail
	@Override
	public String getTableName() {
		return mysqlConnector.TableConfigurations.tableNames[9];
	}
}