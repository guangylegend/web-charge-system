package test;

import java.sql.SQLException;

import Common.CustomerInfo;
import Common.machine;
import mysqlConnector.DbConnector;
import Common.Service;

public class InitDb {
	
	public static void main(String args[]) throws ClassNotFoundException, SQLException {
		DbConnector db = new DbConnector();
		db.init();
		
		String[] machines = {
				"http://139.196.242.131:8080",
				"http://139.196.240.152:8080",
				"http://139.196.242.138:8080",
				"http://139.196.242.110:8080",
				"http://139.196.243.30:8080",
				"http://139.196.243.81:8080",
				"http://139.196.242.139:8080",
				"http://139.196.190.159:8080"};

		String[] machines2 = {
				"http://10.24.151.70:8080",
				"http://10.46.19.56:8080",
				"http://10.46.19.8:8080",
				"http://10.24.151.61:8080",
				"http://10.24.148.210:8080",
				"http://10.46.18.236:8080",
				"http://10.46.19.1:8080",
				"http://10.46.19.6:8080"};
		for (int i = 0; i < machines.length; ++i) {
			machine machine = new machine();
			machine.ip = machines[i];
			db.inputNewMachine(machine);
		}
		
		Service service = new Service();
		service.service_name = "OneMatchOne";
		service.service_guidePrice = 2;
		service.service_desc = "test";
		service.external_URL = "/faces/recognition/compare";
		service.internal_URL = "/Face/OneMatchOne";
		service.service_nickname = "";
		db.inputNewService(service);
		
		CustomerInfo customer = new CustomerInfo();
		customer.customer_name = "test";
		customer.customer_balance = 1000000000;
		customer.customer_ip = "59.78.57.213";
		customer.customer_type = 1;
		customer.customer_servicekey = "123456";
		customer.customer_contactName = "YaoLi";
		customer.customer_areaId = "Shanghai";
		customer.customer_createdByUserId = 1;
		db.inputNewCustomer(customer);
		
		db.setIncreaseUserMoney("test", -1);
		
		for (int i = 0; i < 8; ++i)
			db.getNextFreeMachine("OneMatchOne");
	}
}
