package Server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import work.AllocateAlgoMachine;
import work.CallAlgoMachine;
import work.Validate;
import Tool.Logger;
import Common.Service;
import Common.UserInfo;
import mysqlConnector.DbConnector;
import mysqlConnector.UnknownException;
import Config.Config;
import HttpServer.HeartbeatServlet;
import HttpServer.ServiceServlet;

public class Core extends Thread {
	Map<Integer, Service> services;
	ReadWriteLock lock = new ReentrantReadWriteLock();
	/*
	 * Use Callable and Future to monitor time
	 */
	public Response handleRequest(Request req, RequestInfo info) {
		ExecutorService exec = Executors.newSingleThreadExecutor();
		
		/*
		 * Validate Phase
		 * 
		 * to verify whiteIpList, username and password, balance
		 * to verify whether the customer can use the service
		 * 
		 */
		/*Validate validate = new Validate(req, info);
		try {
		    Future<Response> future = exec.submit(validate);
		    Response res = future.get(Config.validateTimeout, TimeUnit.MILLISECONDS);
		    if (res.status != HttpServletResponse.SC_OK) {
		    	return res;
		    }
		} catch (TimeoutException ex) {
			//time out
			return new Response(info, HttpServletResponse.SC_REQUEST_TIMEOUT, "validate phase timeout");
		} catch (InterruptedException | ExecutionException e) {
			return new Response(info, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"unexpected error in Validate");
		}*/
		
		/*
		 * Allocate Algorithm Machine
		 * 
		 */
		/*String url = null;
		Service service = services.get(info.serviceId);
		AllocateAlgoMachine allocateAlgoMachine = new AllocateAlgoMachine(req, info, service);
		try {
		    Future<Response> future = exec.submit(allocateAlgoMachine);
		    Response res = future.get(Config.allocateAlgoMachine, TimeUnit.MILLISECONDS);
		    if (res.status != HttpServletResponse.SC_OK) {
		    	return res;
		    }
		    url = res.content;
		} catch (TimeoutException ex) {
			//time out
			return new Response(info, HttpServletResponse.SC_REQUEST_TIMEOUT,
					"allocateAlgoMachine phase timeout");
		} catch (InterruptedException | ExecutionException e) {
			return new Response(info, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"unexpected error in AllocateAlgoMachine");
		}*/
		
		/*
		 * Call Algorithm Machine Phase
		 * 
		 * After passing the validate phase, call algorithm machine to get response
		 * 
		 */
/*		Response response = null;
		CallAlgoMachine callAlgoMachine = new CallAlgoMachine(req, info, url);
		try {
		    Future<Response> future = exec.submit(callAlgoMachine);  
		    Response res = future.get(Config.callAlgoMachineTimeout, TimeUnit.MILLISECONDS);
		    if (res.status != HttpServletResponse.SC_OK) {
		    	return res;
		    }
		    response = res;
		} catch (TimeoutException ex) {
			return new Response(info, HttpServletResponse.SC_REQUEST_TIMEOUT, "callAlgoMachine phase timeout");
		} catch (InterruptedException | ExecutionException e) {
			return new Response(info, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"unexpected error in CallAlgoMachine");
		}*/
		
		/*
		 * Write Back Phase
		 * 
		 * TODO
		 * 
		 */
		
		Response response = new Response(info, HttpServletResponse.SC_OK, "");
		
		return response;
	}
	
	public String getTime() {
		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return timeFormat.format(new java.util.Date());
	}
	
	void loadDb() throws ClassNotFoundException, SQLException {
		ArrayList<Service> services2 = new DbConnector().getAllServices();
		services = new HashMap<Integer, Service>();
		for (Service service : services2) {
			System.out.println(service.service_id + " " + service.external_URL);
			services.put(service.service_id, service);
		}
	}
	
	void initHttpServer() throws Exception {
		Server server = new Server();
		ServerConnector connector = new ServerConnector(server);
		connector.setPort(80);
		server.setConnectors(new Connector[] { connector });
		ServletContextHandler context = new ServletContextHandler();
		context.setContextPath("/");
		context.addServlet(HeartbeatServlet.class, "/heartbeat");
		for (Service service : services.values()) {
			ServiceServlet servlet = new ServiceServlet(this, service.service_id);
			ServletHolder holder = new ServletHolder(servlet); 
			context.addServlet(holder, service.external_URL);
		}
		HandlerCollection handlers = new HandlerCollection();
		handlers.setHandlers(new Handler[] { context, new DefaultHandler() });
		server.setHandler(handlers);
		server.start();
		server.join();
	}
	
	@Override
	public void run() {
		try {
			loadDb();
		} catch (Exception e) {
			//TODO
		}
		
		try {
			initHttpServer();
		} catch (Exception e) {
			//TODO
		}
		/*while (true) {
			try {
				Thread.sleep(10000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				refresh();
			} catch (Exception e) {
				//todo
			}
		}*/
	}
	
	public static void main(String[] args) {
		new Core().run();
	}
}