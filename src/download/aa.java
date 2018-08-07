package download;
 
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException; 
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection; 
import java.util.ArrayList;
import java.util.List;

import com.jeefw.model.sys.uploadjindu;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
 
 
 
 

public class aa {
///httpDownload(httpUrl, saveFile);
	
	public static void main(String[] args) {
//		 String httpurl ="http://127.0.0.1:8080/tzz/static/upload/editvideo/20151125/1448454605518045841.mp4";
//		 String saveFile = "d:/video/2.mp4";
//		 
//		 
//		  httpDownload(httpurl, saveFile);
		List<uploadjindu> list =new ArrayList<uploadjindu>();
		uploadjindu up =new uploadjindu();
		up.setName("a");
		up.setJindu(12);
		uploadjindu up1 =new uploadjindu();
		up1.setName("a");
		up1.setJindu(12);
		list.add(up);
		list.add(up1);
	 
	
	}
	public static boolean httpDownload(String httpUrl,String saveFile){  
	       // 下载网络文件  
	       int bytesum = 0;  
	       int byteread = 0;  
	  
	       URL url = null;  
	    try {  
	        url = new URL(httpUrl);  
	    } catch (MalformedURLException e1) {  
	        // TODO Auto-generated catch block  
	        e1.printStackTrace();  
	        return false;  
	    }  
	  
	       try {
	    	   
	    	   
	           URLConnection conn = url.openConnection();  
	           InputStream inStream = conn.getInputStream();  
	           FileOutputStream fs = new FileOutputStream(saveFile);  
	           //找到要下载的文件
	           HttpURLConnection urlcon=(HttpURLConnection)url.openConnection(); 
	         //获取相应的文件长度
	         int fileLength=urlcon.getContentLength();
	         System.out.println(fileLength);
	           byte[] buffer = new byte[1204];  
	           while ((byteread = inStream.read(buffer)) != -1) {  
	        	   
	               bytesum += byteread;  
	               System.out.println(bytesum*100/fileLength);  
	               //downloadmap
	               fs.write(buffer, 0, byteread);  
	           }  
	           return true;  
	       } catch (FileNotFoundException e) {  
	           e.printStackTrace();  
	           return false;  
	       } catch (IOException e) {  
	           e.printStackTrace();  
	           return false;  
	       }  
	   }  
	
	
}
