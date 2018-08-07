package core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream; 

/**
 * 处理HTML代码
 * @ 
 */
public class CopyUtils { 
	   /** 
	    * 移动文件到指定目录 
	    * @param oldPath String 如：c:/fqf.txt 
	    * @param newPath String 如：d:/fqf.txt 
	    */ 
	  public static void moveFolder(String oldPath, String newPath) { 
	      copyFolder(oldPath, newPath); 
	      delAllFile(oldPath);
	  
	  } 
	  public static void main(String[] args) {
		 // int num=  delByName("F:\\zhuanhuan\\swffile\\20151113\\20151113141403175018a.swf");
		  int num=Foldernum("http://127.0.0.1:8080/tzz/static/upload/zhuanhuan/swffile/20151109/20151109100720063361a.swf");
		    System.out.println(num);
	}
	   /** 
	    * 删除文件夹里面的所有文件 
	    * @param path String 文件夹路径 如 c:/fqf 
	    */ 
	  public static void delAllFile(String path) { 
	      File file = new File(path); 
	      if (!file.exists()) { 
	          return; 
	      } 
	      if (!file.isDirectory()) { 
	          return; 
	      } 
	      String[] tempList = file.list(); 
	      File temp = null; 
	      for (int i = 0; i < tempList.length; i++) { 
	          if (path.endsWith(File.separator)) { 
	              temp = new File(path + tempList[i]); 
	          } 
	          else { 
	              temp = new File(path + File.separator + tempList[i]); 
	          } 
	          if (temp.isFile()) { 
	              temp.delete(); 
	          } 
	          if (temp.isDirectory()) { 
	              delAllFile(path+"/"+ tempList[i]);//先删除文件夹里面的文件 
	    //          delFolder(path+"/"+ tempList[i]);//再删除空文件夹 
	          } 
	      } 
	  }
	 
	  /**
	   * 查询文件个数
	   * @param path
	   * @return
	   */
	  public static int  Foldernum(String path){
		 // path="F:/apache-tomcat-7.0.64/webapps/tzz/static/upload/zhuanhuan/swffile/20151106/20151106181812397559a.swf";
		  
		  String name;
		  name=path.split("[.]swf")[0];
		  name=path.split("/")[name.split("/").length-1].split("[.]")[0];
		  path=path.split(name)[0];
		  int num=0;
	      try { 
	          File a=new File(path); 
	          String[] file=a.list(); 
	          File temp=null;  
	          for (int i = 0; i < file.length; i++) { 
	              if(path.endsWith(File.separator)){ 
	                  temp=new File(path+file[i]);
	              } 
	              else{ 
	                  temp=new File(path+File.separator+file[i]); 
	              } 
	              if(temp.isFile()){ 
	            	  if (temp.getName().contains(name)) {
	            		  num++;
					}
	              } 
	          } 
	      } 
	      catch (Exception e) { 
	          System.out.println("计算整个文件夹内容操作出错"); 
	          e.printStackTrace(); 
	      }  
	      return num;
	  }
	   
	  /**
	   * 根据名字删除
	   * @param path
	   * @return
	   */
	  public static int  delByName(String path){
		 // path="F:/apache-tomcat-7.0.64/webapps/tzz/static/upload/zhuanhuan/swffile/20151106/20151106181812397559a.swf";
		  
		  String name;
		  name=path.split("[.]swf")[0];
		  name=path.split("/")[name.split("/").length-1].split("[.]")[0];
		  path=path.split(name)[0];
		  int num=0;
	      try { 
	          File a=new File(path); 
	          String[] file=a.list(); 
	          File temp=null;  
	          for (int i = 0; i < file.length; i++) { 
	              if(path.endsWith(File.separator)){ 
	                  temp=new File(path+file[i]);
	              } 
	              else{ 
	                  temp=new File(path+File.separator+file[i]); 
	              } 
	              if(temp.isFile()){ 
	            	  if (temp.getName().contains(name)) {
	            		  num++;
	            		  System.out.println(temp.getPath());
	            		 File file2=new File(temp.getPath());
	            		 file2.delete();
					}
	              } 
	          } 
	      } 
	      catch (Exception e) { 
	          System.out.println("计算整个文件夹内容操作出错"); 
	          e.printStackTrace(); 
	      }  
	      return num;
	  }
	  
	  /** 
	    * 复制整个文件夹内容 
	    * @param oldPath String 原文件路径 如：c:/fqf 
	    * @param newPath String 复制后路径 如：f:/fqf/ff 
	    * @return boolean 
	    */ 
	  public static void copyFolder(String oldPath, String newPath) {

	      try { 
	          (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹 
	          File a=new File(oldPath); 
	          String[] file=a.list(); 
	          File temp=null; 
	          for (int i = 0; i < file.length; i++) { 
	              if(oldPath.endsWith(File.separator)){ 
	                  temp=new File(oldPath+file[i]); 
	              } 
	              else{ 
	                  temp=new File(oldPath+File.separator+file[i]); 
	              }

	              if(temp.isFile()){ 
	                  FileInputStream input = new FileInputStream(temp); 
	                  FileOutputStream output = new FileOutputStream(newPath + "/" + 
	                          (temp.getName()).toString()); 
	                  byte[] b = new byte[1024 * 5]; 
	                  int len; 
	                  while ( (len = input.read(b)) != -1) { 
	                      output.write(b, 0, len); 
	                  } 
	                  output.flush(); 
	                  output.close(); 
	                  input.close(); 
	              } 
	              if(temp.isDirectory()){//如果是子文件夹 
	                  copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]); 
	              } 
	          } 
	      } 
	      catch (Exception e) { 
	          System.out.println("复制整个文件夹内容操作出错"); 
	          e.printStackTrace();

	      }
	  }

	   
}
