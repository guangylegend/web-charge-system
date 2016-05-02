package Common;

import java.util.ArrayList;

/**
 * Information for services
 * @author chensqi
 *
 */

public class Service {
	public class Para {
		String paraName;
		String paraType;	//	enum: "int", "string", 
		
		public Para(String name, String type) {
			paraName = new String(name);
			paraType = new String(type);
		}
		/**
		 * @return 'paraname','paratype'
		 */
		public String nameType() {
			return "'" + paraName + "'" + ","
					+ "'" + paraType + "'" ;
		}
	}
	
	public String serviceName;
	public Integer serviceId, servicePort,serviceFee;
	public ArrayList<Para> paras;
	
	
	public Service(String name) {
		serviceName = new String(name);
		paras = new ArrayList<Service.Para>();
	}
	/**
	 * Claim this service has following parameter
	 * @param name
	 * @param type
	 */
	public void addPara(String name, String type) {
		paras.add(new Para(name,type));
	}
	@Override
	public String toString() {
		String res = "Name:" + this.serviceName;
		if ( serviceFee != null )
			res += ",Fee:" + serviceFee;
		res += '(';
		for ( int i = 0 ; i < paras.size(); ++i ) {
			res += paras.get(i).paraName + " " + paras.get(i).paraType + ",";
		}
		if ( res.endsWith(","))
			res = res.substring(0, res.length()-1);
		res += ')';
		return res;
	}
}
