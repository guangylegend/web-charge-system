package work;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.Callable;

import Server.Request;
import Server.RequestInfo;
import Server.Response;

public class CallAlgoMachine implements Callable<Response> {
	Request req;
	RequestInfo info;
	String _url;
	
	public CallAlgoMachine(Request req, RequestInfo info, String url) {
		this.req = req;
		this.info = info;
		this._url = url;
	}
	
	public Response call() throws Exception {
		URL url = new URL(_url);
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection(); 
		urlConn.setDoOutput(true);
		urlConn.setDoInput(true);
		//urlConn.setRequestProperty("content-type", "text/html");
		        
		urlConn.setRequestMethod("POST");
		//urlConn.setConnectTimeout(Config.algorithmMachineTimeout);
		OutputStream out = urlConn.getOutputStream();
		//System.out.println(str);
		        
		String json = info.json;
		
		out.write(json.getBytes());  
		out.flush();
		out.close();
		        
		String ret = "";
		if (urlConn.getContentLength() != -1) {
		    if (urlConn.getResponseCode() == 200) {
		        InputStream in = urlConn.getInputStream();  
		        BufferedReader reader = new BufferedReader(new InputStreamReader(in));  
		        String temp = "";
		        while ((temp = reader.readLine()) != null) {  
		        	ret += temp;
		        }
		        reader.close();
		        in.close();
		        urlConn.disconnect();  
		    } else {
		    	return new Response(info, urlConn.getResponseCode(),
		    			"algorithm machine return code");
		    }
		} else {
	    	return new Response(info, urlConn.getResponseCode(),
	    			"algorithm machine return no content");
		}
		return new Response(info, urlConn.getResponseCode(), ret);
	}
}
