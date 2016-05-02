package Server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.*;

public class test {
	static public void main(String args[]) throws FileNotFoundException {
		FileInputStream file = new FileInputStream("D:\\git\\minivision-2015\\src\\Server\\input.txt");
		Scanner in = new Scanner(file);
		String str = "";
		while (in.hasNextLine()) {
			str += in.nextLine();
		}
		System.out.println(str);
		Request req = GsonUtils.getGson().fromJson(str, Request.class);
		//System.out.println(req.image_info.get("mode"));
		System.out.println(new Gson().toJson(req));
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