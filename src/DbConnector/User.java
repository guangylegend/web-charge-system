package DbConnector;

import java.util.Date;
import Common.*;

public class User extends BasicResponse{
	public String userName;
	public int balance; //balance = 1 means 0.01 RMB
	public int alert;
	public Date lastNotifyDate;
	public Permission permission;
}
