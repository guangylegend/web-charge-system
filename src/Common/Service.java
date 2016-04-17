package Common;

import java.util.ArrayList;

/**
 * Information for services
 * @author chensqi
 *
 */

public class Service {
	class Para {
		String paraName;
		String paraType;
		
		Para(String name, String type) {
			paraName = new String(name);
			paraType = new String(type);
		}
	}
	
	public String serviceName;
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
	void addPara(String name, String type) {
		paras.add(new Para(name,type));
	}
	@Override
	public String toString() {
		String res = "Name:" + this.serviceName;
		res += '(';
		for ( int i = 0 ; i < paras.size(); ++i ) {
			res += paras.get(i).paraName + " " + paras.get(i).paraType + ",";
		}
		
		res += ')';
		return res;
	}
}
