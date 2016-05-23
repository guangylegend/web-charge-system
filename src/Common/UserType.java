package Common;
/**
 * user_type = 1	<-------------->	BD经理
 * user_type = 2	<-------------->	总监
 * user_type = 3	<-------------->	总经理
 * user_type = 4	<-------------->	运维人员
 * @author chensqi 2016/5/18
 */
public class UserType extends SqlAble{
	public Integer user_type;
	public String description;
	public UserType() {}
	
	public UserType setDescription(String t) {
		description = new String(t);
		return this;
	}
	@Override
	public String getTableName() {
		return mysqlConnector.TableConfigurations.tableNames[5];
	}
}
