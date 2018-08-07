package core.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.jeefw.model.sys.ProcessInfo;

public class GetProcess extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public GetProcess() {
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
	 	JSONObject json = new JSONObject();
		try{
		 	HttpSession hs = request.getSession();  
		 	ProcessInfo pri = (ProcessInfo) hs.getAttribute("proInfo");
		 	
		 	json.put("err", 0);
		 	json.put("msg", "获取成功！");
		 	if (pri != null ){
			 	System.out.println(pri.getShow());
			 	System.out.println(pri.toString()+ ">>>111");
			 	JSONObject jsonProf = JSONObject.fromObject(pri);
			 	json.put("proInfo", jsonProf);
			 	json.put("err", 0);
			 	json.put("msg", "获取成功！");
		 	}else{
		 		json.put("err", 1);
			 	json.put("msg", "获取失败！");
		 	}
		 	out.println(json.toString());
		 	
		}catch(Exception e){
			e.printStackTrace();
			json.put("err", 1);
		 	json.put("msg", "获取失败！");
		 	out.println(json.toString());
		}
		out.flush();
		out.close();
	
		
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
