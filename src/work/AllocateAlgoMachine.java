package work;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletResponse;

import mysqlConnector.DbConnector;
import Common.Service;
import Common.machineList;
import Server.Request;
import Server.RequestInfo;
import Server.Response;

public class AllocateAlgoMachine implements Callable<Response> {
	Request req;
	RequestInfo info;
	Service service;
	
	public AllocateAlgoMachine(Request req, RequestInfo info, Service service) {
		this.req = req;
		this.info = info;
		this.service = service;
	}
	
	public Response call() throws Exception {
		try {
			machineList machine = new DbConnector().getNextFreeMachine(service.service_name);
			if (machine == null) {
				System.out.println("fuck");
			}
			return new Response(info, HttpServletResponse.SC_OK,
					machine.ip + service.internal_URL);
		} catch (SQLException e) {
			return new Response(info, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"connect to db fail");
		}
	}
}
