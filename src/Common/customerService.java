package Common;

public class customerService extends SqlAble{
	public String customer_id;
	public String service_id;
	public Integer fee;
	@Override
	public String getTableName() {
		return "customerService";
	}
}
