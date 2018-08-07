package core.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.servlet.ModelAndView;

import com.jeefw.model.sys.ProcessInfo;
import com.jeefw.model.sys.TzzUser;

public class FileUpload extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public FileUpload() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
		/*
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
		*/
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try{
			
			SimpleDateFormat sdfa = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			final HttpSession hs = request.getSession(); 
			
			TzzUser tzzUser = (TzzUser)hs.getAttribute("tzzuser");
			if (tzzUser == null ){
				request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
			}
			
	        ModelAndView mv = new ModelAndView();  
	        boolean isMultipart = ServletFileUpload.isMultipartContent(request);  
	        if(!isMultipart){  
	//            return mv;  
	         }  
	         // Create a factory for disk-based file items  
	         FileItemFactory factory = new DiskFileItemFactory();  
	   
	         // Create a new file upload handler  
	         ServletFileUpload upload = new ServletFileUpload(factory);  
	         upload.setProgressListener(new ProgressListener(){
	        	 public void update(long pBytesRead, long pContentLength, int pItems) {  
	                    ProcessInfo pri = new ProcessInfo();  
	                    pri.setItemNum(pItems);  
	                    pri.setReadSize(pBytesRead);  
	                    pri.setTotalSize(pContentLength);  
	                    pri.setShow(pBytesRead+"/"+pContentLength+" byte");  
	                    pri.setRate(Math.round(new Float(pBytesRead) / new Float(pContentLength)*100));  
	                    hs.setAttribute("proInfo", pri);  
	                }  
	             });  
	        String shareName  = request.getParameter("name");
	        String shareContent  = request.getParameter("content");
			List items = upload.parseRequest(request);
			Iterator iter = items.iterator();  
			while (iter.hasNext()) {  
				FileItem item = (FileItem) iter.next();  
				if (item.isFormField()) {  
					String name = item.getFieldName();  
					String value = item.getString();  
				} else {  
					String fieldName = item.getFieldName();  
	                String fileName = item.getName();
	                String filePathStr = "D:/user/";
	                String preName = sdfa.format(new Date());
	                String fileRealName = "";
	                if (fieldName.equals("flvfile")){
	                	filePathStr += "video";
	                	if (fileName.endsWith(".flv")){
	                		fileRealName = filePathStr + "/"+ preName + ".flv";
	                	}else if(fileName.endsWith(".swf")){
	                		fileRealName = filePathStr + "/"+ preName + ".swf";
	                	}
	                	hs.setAttribute("flvFileName", fileRealName);
	                }
	                if(fieldName.equals("pdffile")){
	                	filePathStr += "pdf";
	                	fileRealName = filePathStr + "/"+ preName + ".pdf";
	                	hs.setAttribute("pdfFileName", fileRealName);
	                	
	                }
	                /*
	                 * 判断文件的根目录是否存在
	                 */
	                File filePath = new File(filePathStr);
	                if (!filePath.exists()) {
	    	            filePath.mkdirs();
	    	         }
	                String contentType = item.getContentType();  
	                boolean isInMemory = item.isInMemory();  
	                long sizeInBytes = item.getSize();  
	                File uploadedFile = new File(fileRealName); 
	                item.write(uploadedFile);  
	          }  
			String outStr = "<script type='text/javascript'> history.go(-1);location.href= 'top/center/saveUserShare?name="+shareName +"&content="+ shareContent + "' </script>";
			out.println(outStr);
			out.flush();
			out.close();
	      }  
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			response.sendRedirect("test.jsp");
			/*
			 * 重定向到列表页面。
			 * 
			 * json 返回。
			 */
	
		//request.getRequestDispatcher("/jsp/center_fx.jsp").forward(request, response);
	
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
