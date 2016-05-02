package Common;

import java.util.Date;

public class APILog extends SqlAble{
	public Long logId;	//	This member should be null when doing insert
	public String userName;
	public Date date;
	public String log;
	
}
