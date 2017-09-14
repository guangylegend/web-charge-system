package work;

import java.sql.SQLException;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletResponse;

import Server.Request;
import Server.RequestInfo;
import Server.Response;
import mysqlConnector.DbConnector;
import Common.CustomerInfo;

public class Validate implements Callable<Response> {
	Request req;
	RequestInfo info;
	Response res;
	
	public Validate(Request req, RequestInfo info) {
		this.req = req;
		this.info = info;
	}
	
	public Response call() throws Exception {
		CustomerInfo customer = null;
		try {
			//TODO customer_service table
			customer = new DbConnector().getCustomerInfo(req.session_info.user);
			if (customer == null) {
				return new Response(info, HttpServletResponse.SC_FORBIDDEN, "user does not exist");
			} else if (!customer.customer_servicekey.equals(req.session_info.password)) {
				return new Response(info, HttpServletResponse.SC_FORBIDDEN, "servicekey wrong");
			} else if (customer.customer_balance < 0) {
				return new Response(info, HttpServletResponse.SC_FORBIDDEN, "balance not enough");
			} else {
				return new Response(info, HttpServletResponse.SC_OK, "");
			}
		} catch (SQLException e) {
			return new Response(info, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "connect to db fail");
		}
	}

}
