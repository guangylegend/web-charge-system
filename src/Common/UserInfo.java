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
}
