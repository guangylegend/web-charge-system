package Common;
/**
 * user_type = 1	<-------------->	BD����
 * user_type = 2	<-------------->	�ܼ�
 * user_type = 3	<-------------->	�ܾ���
 * user_type = 4	<-------------->	��ά��Ա
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
