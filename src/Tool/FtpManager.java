package Tool;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import Config.Config;

/*
 * upload image, featureFile, logFile to ftpServer
 * 
 * path, like "abc/123/xyz"
 * InputStream to fit FTPClient.storeFile(String, InputStream)
 * 
 */

public class FtpManager {
	
	static void upload(String path, String fileName, InputStream input) {
		try {
			FTPClient ftp = new FTPClient();
			ftp.connect(Config.ftpUrl);
			ftp.login(Config.ftpUsername, Config.ftpPassword);
			ftp.setFileType(FTP.BINARY_FILE_TYPE);

			if (path.length() > 0) {
				String[] paths = path.split("/");
				for (int i = 0; i < paths.length; ++i) {
					String dir = paths[i];
					ftp.makeDirectory(dir);
					ftp.changeWorkingDirectory(dir);
				}
			}
			
			ftp.storeFile(fileName, input);
			input.close();
			ftp.logout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * transfer base64 String to InputStream
	 */
	public static void uploadBase64(String path, String fileName, String base64) {
		try {
			byte[] bytes = new BASE64Decoder().decodeBuffer(base64);
			
			//not sure whether the code below is needed
			
			/*for (int i = 0; i < bytes.length; ++i)
				if (bytes[i] < 0)
					bytes[i] += 256;
			*/
			
			InputStream input = new ByteArrayInputStream(bytes);
			upload(path, fileName, input);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static public void main(String args[]) {
		//System.out.println(pic1);
		//uploadbase64String(pic1);
	}
}
