package Common;

import java.lang.reflect.Field;
import java.util.Date;

import sun.security.jca.GetInstance;

public class UserInfo extends SqlAble{
	public Integer userId;	//	Unique identity, this member should be null when doing insert
	public String nickName; //	
	public String userName;	//  Login name	
	public String password; //	Encrypted password
	public String email;
	public String companyName;
	public String companyAddress;
	public Date signUpTime;	//	Time when user registered
	public Date lastLogInTime;
	public Integer Money;	//	Money = 1 <==> 0.01 RMB
	public Integer priviligeLevel;	//	Level 0 for super manager, 1 for manager, 2,3,4.... for users
	public Boolean activeOrNot; //	0 or 1
	public String secretKey;
	public String phone;
	
}
