package Common;

import java.util.ArrayList;
import java.util.Date;

/**
 * Information for services
 * @author chensqi
 *
 */

public class Service extends SqlAble{
	
	public String service_name;
	public Integer service_id, service_guidePrice;
	public Date service_createTime;
	public String service_desc;
	public String service_speCharging;
	public String service_nickname;
	
	public String external_URL;
	public String internal_URL;
	
	public Service() {}
	public Service(String name) {
		service_name = new String(name);
	}
}
