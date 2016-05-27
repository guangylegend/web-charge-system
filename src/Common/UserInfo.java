package Common;

import java.lang.reflect.Field;
import java.util.Date;

import sun.security.jca.GetInstance;

public class UserInfo extends SqlAble{
	public Integer user_id;	//	Unique identity
	public String user_name; //	
	public String user_loginName;	//  Login name	
	public String user_password; //	password
	public Integer user_isDelete;	//
	public String user_mailAddress;
	public String user_phoneNum;
	public Integer user_type;
	public String user_desc;	//	description
	
	public String company_id;	//	归属公司(1:表示公司内部人员 null表示其它)
	public String office_id;	//	归属部门
	public String email;
	public String phone;
	public String login_ip;
	public Date login_date;
	public Integer login_flag;	//	是否可登录(1表示可登录)
	public String remarks;		//	备注
	
	
	
	@Override
	public String getTableName() {
		return mysqlConnector.TableConfigurations.tableNames[0];
	}
}
