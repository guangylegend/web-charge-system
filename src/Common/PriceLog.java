package Common;

import java.util.Date;

public class PriceLog extends SqlAble{
	public Long sid;
	public String service_name;
	public String cutsomer_name;
	public Integer old_price;
	public Integer new_price;
	public String user_name;	//	price is changed by thi user
	public Date date;
	@Override
	public String getTableName() {
		return "price_log";
	}
	
}
