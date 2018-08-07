package com.jeefw.controller.sys;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder; 
import java.util.Date;
import java.util.HashMap; 
import java.util.List;
import java.util.Map; 

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject; 

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController; 
import com.jeefw.model.sys.TzzDictionary; 
import com.jeefw.service.sys.TzzDictionaryService;

import core.support.ExtJSBaseParameter;
import core.support.JqGridPageView;
import core.support.QueryResult;

/**
 * 角色的控制层
 * @ 
 */
@Controller
@RequestMapping("/sys/Tzzdic")
public class TzzDictionaryController extends JavaEEFrameworkBaseController<TzzDictionary> implements Constant {
	@Resource
	TzzDictionaryService dictionaryService;
	@Resource
	TzzDictionaryService tzzDictionaryService;

	/**
	 * 获取新闻分类下拉菜单
	 * @param request
	 * @param response
	 * @throws Exception
	 */
		@RequestMapping(value = "/getTypeSelectList", method = { RequestMethod.POST, RequestMethod.GET })
		public void getTypeSelectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
			Integer parentId = Integer.parseInt(request.getParameter("parentId"));
			List<TzzDictionary> tzzdicList = tzzDictionaryService.queryByProerties("parentId", parentId);
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < tzzdicList.size(); i++) {
				builder.append("<option value='" + tzzdicList.get(i).getId() + "'>" +tzzdicList.get(i).getName()+  "</option>");
			}
			writeJSON(response, builder.toString());
		}
	
	/**
	 * 详细分类级联下拉菜单
	 * /sys/Tzzdic/getjilian
	 * @param request
	 * @param response
	 * @throws Exception
	 */
		@RequestMapping(value = "/getjilian", method = { RequestMethod.POST, RequestMethod.GET })
		public void getjilian(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
			String[] name=new String[3];
			Object[] v=new Object[3];
			name[0]="type";
			name[1]="level";
			name[2]="parentId";
			v[0]="1";
			if(request.getParameter("level")!=null){
				v[1]=request.getParameter("level");
			}else{
				v[1]="1";
			} 
			if(!v[1].equals("1")){  
				v[2]=Integer.valueOf(request.getParameter("did"));
			}else {
				v[2]= 0;
			}
			List<TzzDictionary> tzzdicList = tzzDictionaryService.queryByProerties(name, v);
			StringBuilder builder = new StringBuilder();
			if(v[1].equals("1")){
				builder.append("<select  name='familyOne'>");  
				builder.append("<option>请选择分类</option>");
			} 
			if(v[1].equals("2")){
				builder.append("<select  name='familyTwo'>");
				builder.append("<option>请选择分类</option>");
			}
			if(v[1].equals("3")){
				builder.append("<select  name='familyId'>"); 
				builder.append("<option>请选择分类</option>");
			}
			
			for (int i = 0; i < tzzdicList.size(); i++) {
				builder.append("<option value='" + tzzdicList.get(i).getId() + "'>" +tzzdicList.get(i).getName()+  "</option>");
			}
			builder.append("</select>"); 
			writeJSON(response, builder.toString());
		}
	 
	/**
	 * 获取大分类（营地，社会等）下拉菜单
	 * @param request
	 * @param response
	 * @throws Exception
	 */
		@RequestMapping(value = "/getviptypeSelectList", method = { RequestMethod.POST, RequestMethod.GET })
		public void getvipSelectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
			List<TzzDictionary> tzzdicList = tzzDictionaryService.queryByProerties("type", "3");
			StringBuilder builder = new StringBuilder();
			builder.append("<select  name='classifyId'>"); 
			for (int i = 0; i < tzzdicList.size(); i++) {
				builder.append("<option value='" + tzzdicList.get(i).getId() + "'>" +tzzdicList.get(i).getName()+  "</option>");
			}
			builder.append("</select>"); 
			writeJSON(response, builder.toString());
		}
		/**
		 * 获取大分类（营地，社会等）复选框
		 * /sys/Tzzdic/getcoursecheckbox
		 * @param request
		 * @param response
		 * @throws Exception
		 */
			@RequestMapping(value = "/getcoursecheckbox", method = { RequestMethod.POST, RequestMethod.GET })
			public void getcoursecheckbox(HttpServletRequest request, HttpServletResponse response) throws Exception {
				List<TzzDictionary> tzzdicList = tzzDictionaryService.queryByProerties("type", "3");
				StringBuilder builder = new StringBuilder(); // <input type="checkbox" name="boxes" value="实习 没做过开发">实习 没做过开发<br>  
				 
				for (int i = 0; i < tzzdicList.size(); i++) {
					builder.append(" <input type=\"checkbox\" name=\"boxes\" value=\""+tzzdicList.get(i).getId()+"\"/>" +tzzdicList.get(i).getName()+"&nbsp&nbsp");
				} 
				writeJSON(response, builder.toString());
			}
			
		/**
		 * 获取课程分类下拉菜单
		 * @param request
		 * @param response
		 * @throws Exception
		 */
			@RequestMapping(value = "/gettypeSelectList", method = { RequestMethod.POST, RequestMethod.GET })
			public void getSelectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
				String[] name=new String[2];
				String[] v=new String[2];
				name[0]="type";
				name[1]="level";
				v[0]="1";
				v[1]="3";
				List<TzzDictionary> tzzdicList = tzzDictionaryService.queryByProerties(name,v);
				StringBuilder builder = new StringBuilder();
				builder.append("<select name='category' id='acategory'>");
				for (int i = 0; i < tzzdicList.size(); i++) {
					builder.append("<option value='" + tzzdicList.get(i).getId() + "'>" +tzzdicList.get(i).getName()+  "</option>");
				}
				builder.append("</select>"); 
				writeJSON(response, builder.toString());
			}
			
			@RequestMapping(value = "/gettixikuSelectList", method = { RequestMethod.POST, RequestMethod.GET })
			public void gettixikuSelectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
				String[] name=new String[2];
				String[] v=new String[2];
				name[0]="type";
				name[1]="level";
				v[0]="1";
				v[1]="3";
				List<TzzDictionary> tzzdicList = tzzDictionaryService.queryByProerties(name,v);
				StringBuilder builder = new StringBuilder();
				builder.append("<select name='familyId' id='familyId'>");
				for (int i = 0; i < tzzdicList.size(); i++) {
					builder.append("<option value='" + tzzdicList.get(i).getId() + "'>" +tzzdicList.get(i).getName()+  "</option>");
				}
				builder.append("</select>"); 
				writeJSON(response, builder.toString());
			}
			
		
		/**
		 * 首页轮播图获取大分类（营地，社会等）下拉菜单
		 * @param request
		 * @param response
		 * @throws Exception
		 */
			@RequestMapping(value = "/getvipSelectforIndexList", method = { RequestMethod.POST, RequestMethod.GET })
			public void getvipSelectforIndexList(HttpServletRequest request, HttpServletResponse response) throws Exception {
				List<TzzDictionary> tzzdicList = tzzDictionaryService.queryByProerties("type", "3");
				StringBuilder builder = new StringBuilder();
				builder.append("<select>");
				builder.append("<option value='0'>散客与游客</option>");
				for (int i = 0; i < tzzdicList.size(); i++) {
					builder.append("<option value='" + tzzdicList.get(i).getId() + "'>" +tzzdicList.get(i).getName()+  "</option>");
				}
				builder.append("</select>"); 
				writeJSON(response, builder.toString());
			}
			
		
			/**
			 * 首页轮播图获取大分类（营地，社会等）下拉菜单编辑
			 * @param request
			 * @param response
			 * @throws Exception
			 */
				@RequestMapping(value = "/getvipSelectforIndexListedit", method = { RequestMethod.POST, RequestMethod.GET })
				public void getvipSelectforIndexListedit(HttpServletRequest request, HttpServletResponse response) throws Exception {
					List<TzzDictionary> tzzdicList = tzzDictionaryService.queryByProerties("type", "3");
					StringBuilder builder = new StringBuilder();
					builder.append("<select name='carouselType'>");
					builder.append("<option value='0'>散客与游客</option>");
					for (int i = 0; i < tzzdicList.size(); i++) {
						builder.append("<option value='" + tzzdicList.get(i).getId() + "'>" +tzzdicList.get(i).getName()+  "</option>");
					}
					builder.append("</select>"); 
					JSONObject jsonObject=new JSONObject();
					jsonObject.put("d", builder.toString());
					writeJSON(response, jsonObject);
				}
				/**
				 * 产品分类下拉菜单同课程分类
				 * @param request
				 * @param response  /sys/Tzzdic/getproSelectList
				 * @throws Exception
				 */
				@RequestMapping(value = "/getproSelectList", method = { RequestMethod.POST, RequestMethod.GET })
				public void getproSelectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
					String[] name=new String[2];
					String[] v=new String[2];
					name[0]="type";
					name[1]="level";
					v[0]="1";
					v[1]="3";
					List<TzzDictionary> tzzdicList = tzzDictionaryService.queryByProerties(name,v);
					StringBuilder builder = new StringBuilder();
					builder.append("<select name='category' id='acategory'>");
					for (int i = 0; i < tzzdicList.size(); i++) {
						builder.append("<option value='" + tzzdicList.get(i).getId() + "'>" +tzzdicList.get(i).getName()+  "</option>");
					}
					builder.append("</select>"); 
					JSONObject jsonObject=new JSONObject();
					jsonObject.put("d", builder.toString());
					writeJSON(response, jsonObject);
				}
				
				
				
			
			@RequestMapping(value = "/getDifficultSelectList", method = { RequestMethod.POST, RequestMethod.GET })
			public void getDifficultSelectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
				List<TzzDictionary> tzzdicList = tzzDictionaryService.queryByProerties("type", "2");
				StringBuilder builder = new StringBuilder();
				builder.append("<select  name='difficultyId'>");
				for (int i = 0; i < tzzdicList.size(); i++) {
					builder.append("<option value='" + tzzdicList.get(i).getId() + "'>" +tzzdicList.get(i).getName()+  "</option>");
				}
				builder.append("</select>");
				System.out.println( builder.toString()); 
				writeJSON(response, builder.toString());
			}

	@RequestMapping(value = "/getCategory", method = { RequestMethod.POST, RequestMethod.GET })
	public void getCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer firstResult = Integer.valueOf(request.getParameter("page"));
		Integer maxResults = Integer.valueOf(request.getParameter("rows"));
		String sortedObject = request.getParameter("sidx");
		String type=request.getParameter("type");
		String sortedValue = request.getParameter("sord");
		String filters = request.getParameter("filters");
		TzzDictionary dictionary = new TzzDictionary();
		dictionary.set$eq_type(type);
		if (StringUtils.isNotBlank(filters)) {
			JSONObject jsonObject = JSONObject.fromObject(filters);
			JSONArray jsonArray = (JSONArray) jsonObject.get("rules");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject result = (JSONObject) jsonArray.get(i);
				if (result.getString("field").equals("name") && result.getString("op").equals("cn")) {
					dictionary.set$like_name(result.getString("data"));
				}
				if (result.getString("field").equals("level") && result.getString("op").equals("eq")) {
					dictionary.set$eq_level(result.getString("data"));
				}
				if(result.getString("field").equals("parentId") && result.getString("op").equals("eq")){
					dictionary.set$eq_parentId(Integer.valueOf(result.getString("data")));
				}
			}
			if (((String) jsonObject.get("groupOp")).equalsIgnoreCase("OR")) {
				dictionary.setFlag("OR");
			} else {
				dictionary.setFlag("AND");
			}
			
		}
		dictionary.setFirstResult((firstResult - 1) * maxResults);
		dictionary.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		dictionary.setSortedConditions(sortedCondition);
		QueryResult<TzzDictionary> queryResult = dictionaryService.doPaginationQuery(dictionary);
		List <TzzDictionary> queryList = queryResult.getResultList();
		if(!type.equals("9")){
			queryList = dictionaryService.queryTzzDictCnList(queryList);
		}
		JqGridPageView<TzzDictionary> authorityListView = new JqGridPageView<TzzDictionary>();
		authorityListView.setMaxResults(maxResults);
		authorityListView.setRows(queryList);
		authorityListView.setRecords(queryResult.getTotalCount());
		writeJSON(response, authorityListView);
	}
	
	// 删除菜单
		@RequestMapping("/deleteDict")
		public void deleteDict(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Integer[] ids) throws IOException {
			boolean flag = dictionaryService.deleteByPK(ids);
			if (flag) {
				writeJSON(response, "{success:true}");
			} else {
				writeJSON(response, "{success:false}");
			}
		}
		
		@RequestMapping(value = "/saveAuthority", method = { RequestMethod.POST, RequestMethod.GET })
		public void doSave(TzzDictionary tzzDictionary, HttpServletRequest request, HttpServletResponse response) throws IOException {
			ExtJSBaseParameter parameter = ((ExtJSBaseParameter) tzzDictionary);
			if (CMD_EDIT.equals(parameter.getCmd())) {
				dictionaryService.merge(tzzDictionary);
			} else if (CMD_NEW.equals(parameter.getCmd())) {
				dictionaryService.persist(tzzDictionary);
			}
			parameter.setSuccess(true);
			writeJSON(response, parameter);
		}
	
	@RequestMapping(value = "/operateDict", method = { RequestMethod.POST, RequestMethod.GET })
	public void operateDict(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> result = new HashMap<String, Object>();
		String oper = request.getParameter("oper");
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		if (oper.equals("del")) {
			boolean f=false;
			String[] ids = id.split(",");
			for (int i = 0; i < ids.length; i++) {
				if(ids[i].equals("1")){
					f=true; 
				}
			}
			Integer[] idsint = (Integer[]) ConvertUtils.convert(ids, Integer.class);
			 
			if(f){
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.put("message", "该分类不能删除！");
				writeJSON(response, result);
			}else {
				deleteDict(request, response, (Integer[]) ConvertUtils.convert(ids, Integer.class));
			} 
		} else if (oper.equals("excel")) {
			response.setContentType("application/msexcel;charset=UTF-8");
			try {
				response.addHeader("Content-Disposition", "attachment;filename=file.xls");
				OutputStream out = response.getOutputStream();
				out.write(URLDecoder.decode(request.getParameter("csvBuffer"), "UTF-8").getBytes());
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			
			String categoryName = request.getParameter("name");
			String categoryTitle = request.getParameter("title"); 
			String categoryParentId = request.getParameter("parentId");
			String categoryLevel = request.getParameter("level"); 
			int categoryParentIdNum = 0 ;
			if (!StringUtils.isBlank(categoryParentId)) {
				categoryParentIdNum = Integer.valueOf(categoryParentId);
			}
			TzzDictionary tzzDictionary = null;
			if (oper.equals("edit")) {
				tzzDictionary = dictionaryService.get(Integer.valueOf(id));
			}
			TzzDictionary sameNameDict = dictionaryService.getByProerties("name", categoryName);
			TzzDictionary parentMenuCodeAuthority = dictionaryService.getByProerties("id", categoryParentIdNum);
			
			//System.out.println(tzzDictionary.getId()+"=="+Integer.valueOf(id)+"++++"+(tzzDictionary.getId() != Integer.valueOf(id))+"++++"+(null != tzzDictionary)+"=="+oper.equals("edit")); 
			if (StringUtils.isBlank(categoryName) || StringUtils.isBlank(categoryTitle) || StringUtils.isBlank(categoryParentId) || StringUtils.isBlank(categoryLevel)) {
				response.setStatus(HttpServletResponse.SC_LENGTH_REQUIRED);
				result.put("message", "请填写分类名字、分类的标题、父节点ID， 类型、等级信息");
				writeJSON(response, result);
			} else if (null != sameNameDict && oper.equals("add")) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.put("message", "类已经存在，请重新输入");
				writeJSON(response, result);
			} else if (null != tzzDictionary && (tzzDictionary.getId() - Integer.valueOf(id) != 0 ) && oper.equals("edit")) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.put("message", "类的名字重复，请重新输入");
				writeJSON(response, result);
			} else if (categoryParentIdNum != 0  && null == parentMenuCodeAuthority) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.put("message", "父类输入有误，请重新输入");
				writeJSON(response, result);
			} else {
					
				if (oper.equals("edit")) {
					if (categoryName != null && !"".equals(categoryName)) {
						tzzDictionary.setName(categoryName);
					}
					if(categoryTitle!= null && !"".equals(categoryTitle)){
						tzzDictionary.setTitle(categoryTitle);
					} 
					tzzDictionary.setParentId(categoryParentIdNum);
					tzzDictionary.setType(type);
					tzzDictionary.setLevel(categoryLevel);	
					tzzDictionary.setCmd("edit");
					doSave(tzzDictionary, request, response);
				} else if (oper.equals("add")) {
					TzzDictionary tzzDictionary1 = new TzzDictionary();
					tzzDictionary1.setName(categoryName);
					tzzDictionary1.setTitle(categoryTitle);
					tzzDictionary1.setParentId(categoryParentIdNum);
					tzzDictionary1.setType(type);
					tzzDictionary1.setLevel(categoryLevel);	
					tzzDictionary1.setCreateTime(new Date());
					tzzDictionary1.setCmd("new");
					doSave(tzzDictionary1, request, response);
				}
			}
		}
	}
}
