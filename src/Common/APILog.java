package Common;

import java.util.Date;

public class APILog extends SqlAble {
	public Long sid;
	public Date date;
	public String customer_name;
	public String service_name;
	public String input;
	public String output;
	public Integer cost;
	public String host;	//	��������ip
	public String exception;	//	�쳣
	@Override
	public String getTableName() {
		return mysqlConnector.TableConfigurations.tableNames[9];
	}
}