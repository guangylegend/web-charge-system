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
	
	public String company_id;	//	������˾(1:��ʾ��˾�ڲ���Ա null��ʾ����)
	public String office_id;	//	��������
	public String email;
	public String phone;
	public String login_ip;
	public Date login_date;
	public String login_flag;	//	�Ƿ�ɵ�¼(1��ʾ�ɵ�¼)
	public String remarks;		//	��ע
	
	
	
	@Override
	public String getTableName() {
		return mysqlConnector.TableConfigurations.tableNames[0];
	}
}
