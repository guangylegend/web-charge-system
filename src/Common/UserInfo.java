package Common;

import java.util.Date;

public class UserInfo {
	public int userId;	//	Unique identity
	public String userName; //	The real name of user
	public String loginName;	//	Account name
	public String password; //	Encrypted password
	public String email;
	public String companyAddress;
	public Date signUpTime;	//	Time when user registered
	public Date lastLogInTime;
	public int remainedMoney;	//	Money = 1 <==> 0.01 RMB
	public int priviligeLevel;	//	Level 1,2,3.. for managers. Level 0 for users
	public boolean activeOrNot; //	0 or 1
}
