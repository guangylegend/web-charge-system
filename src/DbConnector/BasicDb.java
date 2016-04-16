package DbConnector;

import Common.*;
import java.util.Date;

public abstract class BasicDb {
	protected int timeout = Config.dbTimeout;
	
	protected abstract User userInfo(String userName, String password) throws Exception;
	//md5
	
	protected abstract Request newRequest(String userName, Date curDate) throws Exception;
	//Date.toString()
	//return request id = max + 1
	
	protected abstract BasicResponse updateRequest(RequestResponse response) throws Exception;
	
	protected abstract WhiteList whiteList() throws Exception;
}
