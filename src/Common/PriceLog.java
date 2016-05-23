package Common;

import java.util.Date;

public class PriceLog extends SqlAble{
	public Long sid;
	public Integer service_id;
	public Integer cutsomer_id;
	public Integer old_price;
	public Integer new_price;
	public Integer user_id;	//	price is changed by thi user
	public Date date;
	@Override
	public String getTableName() {
		return "price_log";
	}
	
}
