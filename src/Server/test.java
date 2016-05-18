package Server;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mysqlConnector.DbConnector;
import sun.misc.BASE64Encoder;

import com.google.gson.*;

public class test {
	public static String GetImageStr(String imgFilePath) {
        byte[] data = null;
        try {
            InputStream in = new FileInputStream(imgFilePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }
	
	static public void main(String args[]) throws Exception {
		FileInputStream file = new FileInputStream("D:\\git\\minivision-2015\\src\\Server\\input.txt");
		Scanner in2 = new Scanner(file);
		String str = "";
		while (in2.hasNextLine()) {
			str += in2.nextLine();
		}
		Request req = GsonUtils.getGson().fromJson(str, Request.class);
		req.image_info.put("image1", GetImageStr("D:\\picture\\IMG_8630.jpg"));
		req.image_info.put("image2", GetImageStr("D:\\picture\\IMG_8630.jpg"));
		str = GsonUtils.getGson().toJson(req);
		
		final String strstr = str;

		//str = "{}";
		//System.out.println(str);
		

		ExecutorService exec = Executors.newFixedThreadPool(20);

		long x = Calendar.getInstance().getTimeInMillis();
		for (int i = 0; i < 500; ++i) {
			final int f = i;
			exec.execute(new Thread() {
		@Override()
		public void run() {
			try{
	        
		URL url = new URL("http://139.196.243.178/faces/recognition/compare");  
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection(); 
        urlConn.setDoOutput(true);  
        urlConn.setDoInput(true);
        //urlConn.setRequestProperty("content-type", "text/html");
        
        urlConn.setRequestMethod("POST");
        
        
        OutputStream out = urlConn.getOutputStream();
        //System.out.println(str);
        out.write(strstr.getBytes());  
        out.flush();
        out.close();
        
        if (urlConn.getContentLength() != -1) {  
            if (urlConn.getResponseCode() == 200) {
                InputStream in = urlConn.getInputStream();  
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));  
                String temp = "";  
                while ((temp = reader.readLine()) != null) {  
            //        System.out.println("server response: " + temp);
                }
                reader.close();
                in.close();
                urlConn.disconnect();  
            }  
        }
    	System.out.println(urlConn.getResponseCode());
				System.out.println(f);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		});
		}
		exec.shutdown();
		while (true) {
			if (exec.isTerminated()) {
				long y = Calendar.getInstance().getTimeInMillis();
				System.out.println("time: " + (y - x));
				break;
			}
		}
	}
}

/*
,
"image_info" :
{
"mode" : 1,
"image1" : "123",
"image2" : "456"
},
"options_info" :
{
"auto_rotate" : true,
"auto_flip" : true,
"check_repo":false,
"mode": "normal",
"remove_watermark" : false,
"true_negative_rate" : "99.99",
"async" : false
}
*/