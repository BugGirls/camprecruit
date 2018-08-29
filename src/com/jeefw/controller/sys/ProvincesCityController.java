package com.jeefw.controller.sys;
 
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource; 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController;
import com.jeefw.model.sys.ProductPropertyTemp;
import com.jeefw.model.sys.ProvincesCity;
import com.jeefw.service.sys.ProvincesCityService;

import core.util.NumberHelper;
import core.util.StringHelper;

/**
 * 职位类别的控制层
 * 控制层
 */
@Controller
@RequestMapping("/sys/provincesCity")
public class ProvincesCityController extends JavaEEFrameworkBaseController<ProvincesCity> implements Constant {

	@Resource
	private ProvincesCityService provincesCityService;
	
		
		// 保存职位类别树
		// 保存树
		@RequestMapping(value = "/savetree", method = { RequestMethod.POST, RequestMethod.GET })
		public void savetree(HttpServletRequest request, HttpServletResponse response) throws IOException {
			JSONArray array = JSONArray.fromObject(request.getParameter("tree"));
			if(array.size()>0){
				List<ProvincesCity> list = provincesCityService.doQueryAll();
				for(ProvincesCity careertype : list){
					provincesCityService.delete(careertype);
				}
				
				for(int i=0;i<array.size();i++){
					JSONObject json = array.getJSONObject(i);
					Integer id = json.getInt("id");
					String pid = StringHelper.null2String(json.get("pId"));
					String name = json.getString("name");
					ProvincesCity careerType = new ProvincesCity();
					careerType.setId(id);
					careerType.setName(name);
					careerType.setPid(NumberHelper.string2Int(pid,0));
					careerType.setSequence(i+1);
					provincesCityService.persist(careerType);
					JSONArray childrenArr = json.getJSONArray("children");
					boolean isParent = json.getBoolean("isParent");
					if(isParent&&childrenArr!=null&&childrenArr.size()>0){
						for(int j=0;j<childrenArr.size();j++){
							JSONObject childrenjson = childrenArr.getJSONObject(j);
							Integer childid = childrenjson.getInt("id");
							Integer childpid = childrenjson.getInt("pId");
							String childname = childrenjson.getString("name");
							ProvincesCity careerChild = new ProvincesCity();
							careerChild.setId(childid);
							careerChild.setName(childname);
							careerChild.setPid(childpid);
							careerChild.setSequence(j+1);
							provincesCityService.persist(careerChild);
						}
					}
				}
			}
			String htmlString="<script language=javascript> history.go(-1);</script>";
 			writeJSON(response, htmlString);
		}
		
		// 取得职位类别树
		@RequestMapping(value = "/getcareertypetree", method = { RequestMethod.POST, RequestMethod.GET })
		public void getCareerTypeTree(HttpServletRequest request, HttpServletResponse response) throws IOException {
			List<ProvincesCity> mainMenuList = provincesCityService.queryByProerties("pid", 0);
			JSONObject allJSONObject = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			for (int i = 0; i < mainMenuList.size(); i++) {
				JSONObject mainJsonObject = new JSONObject();
				
				mainJsonObject.element("id", mainMenuList.get(i).getId());
				mainJsonObject.element("pId", 0);
				mainJsonObject.element("name", mainMenuList.get(i).getName());
				mainJsonObject.element("url", mainMenuList.get(i).getData_url());
				mainJsonObject.element("open", true);
				List<ProvincesCity> subList = provincesCityService.queryByProerties("pid", mainMenuList.get(i).getId());
				if(subList!=null &&subList.size()>0){
					for(int j = 0; j < subList.size(); j++){
						JSONObject subJsonObject = new JSONObject();
						subJsonObject.element("id", subList.get(j).getId());
						subJsonObject.element("pId",subList.get(j).getPid());
						subJsonObject.element("name", subList.get(j).getName());
						subJsonObject.element("url", subList.get(j).getData_url());
						subJsonObject.element("open", true);
						jsonArray.add(subJsonObject);
						List<ProvincesCity> secsubList = provincesCityService.queryByProerties("pid", subList.get(j).getId());
						if(secsubList!=null &&secsubList.size()>0){
							for(int k = 0; k < secsubList.size(); k++){
								JSONObject secsubJsonObject = new JSONObject();
								secsubJsonObject.element("id", secsubList.get(k).getId());
								secsubJsonObject.element("pId",secsubList.get(k).getPid());
								secsubJsonObject.element("name", secsubList.get(k).getName());
								secsubJsonObject.element("url", secsubList.get(k).getData_url());
								jsonArray.add(secsubJsonObject);
							}
						}
					}
				}
				jsonArray.add(mainJsonObject);
			}
			allJSONObject.element("status", "OK");
			allJSONObject.element("data", jsonArray);
			writeJSON(response, allJSONObject);
		}
		
		// 取得职位类别树
		@RequestMapping(value = "/getNodeOfTree", method = { RequestMethod.POST, RequestMethod.GET })
		public void getNodeOfTree(HttpServletRequest request, HttpServletResponse response) throws IOException {
			String nodeid = request.getParameter("id");
			List<ProvincesCity> menuList = new ArrayList<ProvincesCity>();
			if(StringHelper.isNotEmpty(nodeid)){
				menuList = provincesCityService.queryByProerties("pid", nodeid);
			}else{
				menuList = provincesCityService.queryByProerties("pid", 0);
			}
			JSONObject allJSONObject = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			for (int i = 0; i < menuList.size(); i++) {
				JSONObject mainJsonObject = new JSONObject();
				mainJsonObject.element("id", menuList.get(i).getId());
				mainJsonObject.element("pId", 0);
				mainJsonObject.element("name", menuList.get(i).getName());
				//mainJsonObject.element("url", menuList.get(i).getData_url());
				mainJsonObject.element("open", true);
				jsonArray.add(mainJsonObject);
			}
			allJSONObject.element("status", "OK");
			allJSONObject.element("data", jsonArray);
			writeJSON(response, allJSONObject);
		}
		
		// 保存职位类别链接地址
		@RequestMapping(value = "/saveCareerTypeUrl", method = { RequestMethod.POST, RequestMethod.GET })
		public void saveCareerTypeUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
			String careertypeid = request.getParameter("careertypeid");
			String dataurl = request.getParameter("dataurl");
			provincesCityService.updateByProperties("id", NumberHelper.string2Int(careertypeid), "data_url", dataurl);
			writeJSON(response, "{success:true}");
		}
		
		// 保存职位类别树
		@RequestMapping(value = "/addNodeOfTree", method = { RequestMethod.POST, RequestMethod.GET })
		public void addNodeOfTree(HttpServletRequest request, HttpServletResponse response) throws IOException {
			JSONObject jsonObj = JSONObject.fromObject(request.getParameter("treeNode"));
			Integer id = jsonObj.getInt("id");
			String pid = StringHelper.null2String(jsonObj.get("pId"));
			String name = jsonObj.getString("name");
			ProvincesCity careerType = new ProvincesCity();
			careerType.setId(id);
			careerType.setName(name);
			careerType.setPid(NumberHelper.string2Int(pid,0));
			//careerType.setSequence(i+1);
			provincesCityService.persist(careerType);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("careerType", careerType)	;
			writeJSON(response, jsonObject);
//			String htmlString="<script language=javascript> history.go(-1);</script>";
// 			writeJSON(response, htmlString);
		}
		
		// 保存职位类别树
		@RequestMapping(value = "/addChildNodeOfTree", method = { RequestMethod.POST, RequestMethod.GET })
		public void addChildNodeOfTree(HttpServletRequest request, HttpServletResponse response) throws IOException {
			String pid = StringHelper.null2String(request.getParameter("pId"));
			String name = StringHelper.null2String(request.getParameter("name"));
			int parentid = NumberHelper.string2Int(pid,0);
			JSONObject jsonObject = new JSONObject();
			if(parentid>0){
				int maxid = NumberHelper.string2Int(parentid + "01");
				Map<String, String> sortedCondition = new HashMap<String, String>();
				sortedCondition.put("id", "desc");
				List<ProvincesCity> childlist = provincesCityService.queryByProerties("pid", parentid, sortedCondition);
				if(childlist!=null&&childlist.size()>0){
					maxid = childlist.get(0).getId()+1;
				}
				ProvincesCity careerType = new ProvincesCity();
				careerType.setId(maxid);
				careerType.setName(name);
				careerType.setPid(parentid);
				//careerType.setSequence(i+1);
				provincesCityService.persist(careerType);
				jsonObject.put("careerType", careerType)	;
			}
			writeJSON(response, jsonObject);
		}
		
		// 重命名职位类别树
		@RequestMapping(value = "/renameNodeOfTree", method = { RequestMethod.POST, RequestMethod.GET })
		public void renameNodeOfTree(HttpServletRequest request, HttpServletResponse response) throws IOException {
			JSONObject jsonObj = JSONObject.fromObject(request.getParameter("treeNode"));
			Integer id = jsonObj.getInt("id");
			String name = jsonObj.getString("name");
			if(id>0){
				ProvincesCity careerType = provincesCityService.get(id);
				if(careerType!=null){
					careerType.setName(name);
					provincesCityService.merge(careerType);
				}
			}
			String htmlString="<script language=javascript> history.go(-1);</script>";
			writeJSON(response, htmlString);
		}
		
		// 删除职位类别树
		@RequestMapping(value = "/deleteNodeOfTree", method = { RequestMethod.POST, RequestMethod.GET })
		public void deleteNodeOfTree(HttpServletRequest request, HttpServletResponse response) throws IOException {
			String id = request.getParameter("nodeid");
			String isparent = StringHelper.null2String(request.getParameter("isparent"));
			if(StringHelper.isNotEmpty(id)){
				int nodeid = NumberHelper.string2Int(id);
				//有子节点的级联删除子节点
				if(isparent.equals("true")){
					provincesCityService.deleteByPK(nodeid);
					provincesCityService.deleteByProperties("pid", nodeid);
				}else{
					provincesCityService.deleteByPK(nodeid);
				}
			}
			
			String htmlString="<script language=javascript> history.go(-1);</script>";
			writeJSON(response, htmlString);
		}
		
		// 查询职位类别一级目录最大值
		@RequestMapping(value = "/getMaxRootId", method = { RequestMethod.POST, RequestMethod.GET })
		public void getMaxRootId(HttpServletRequest request, HttpServletResponse response) throws IOException {
			JSONObject jsonObject = new JSONObject();
			int maxid = 1;
			Map<String, String> sortedCondition = new HashMap<String, String>();
			sortedCondition.put("id", "desc");
			List<ProvincesCity> rootlist = provincesCityService.queryByProerties("pid", 0, sortedCondition);
			if(rootlist!=null&&rootlist.size()>0){
				maxid = rootlist.get(0).getId()+1;
			}
			jsonObject.put("maxid", maxid)	;
			writeJSON(response, jsonObject);
		}
		
		// 查询职位类别下级目录最大值
		@RequestMapping(value = "/getMaxChildNodetId", method = { RequestMethod.POST, RequestMethod.GET })
		public void getMaxChildNodetId(HttpServletRequest request, HttpServletResponse response) throws IOException {
			String id = request.getParameter("nodeid");
			int maxid = 101;
			Map<String, String> sortedCondition = new HashMap<String, String>();
			sortedCondition.put("id", "desc");
			List<ProvincesCity> childlist = provincesCityService.queryByProerties("pid", NumberHelper.string2Int(id), sortedCondition);
			if(childlist!=null&&childlist.size()>0){
				maxid = childlist.get(0).getId()+1;
			}
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("maxid", maxid)	;
			writeJSON(response, jsonObject);
		}
		
		/**
		 * 查询省份
		 * @param request
		 * @param response
		 * @throws Exception
		 */
		@RequestMapping(value = "/getProvincesSelectList", method = { RequestMethod.POST, RequestMethod.GET })
		public void getProvincesSelectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
			String pid = request.getParameter("pid");
			String province = request.getParameter("province");
			pid = StringUtils.isNotEmpty(pid) ? pid : "101";
			//ProvincesCity provincesCity = provincesCityService.getByProerties("id", province);
			List<ProvincesCity> provincesCityList = provincesCityService.queryByProerties("pid", Integer.parseInt(pid));
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < provincesCityList.size(); i++) {
				if(null != province && province.equals(provincesCityList.get(i).getId()+"")){
					builder.append("<option value='" + provincesCityList.get(i).getId() + "' selected='selected'>" +provincesCityList.get(i).getName()+  "</option>");
				} else {
					builder.append("<option value='" + provincesCityList.get(i).getId() + "'>" +provincesCityList.get(i).getName()+  "</option>");
				}
			}
			writeJSON(response, builder.toString());
		}
		
		/**
		 * 查询市
		 * @param request
		 * @param response
		 * @throws Exception
		 */
		@RequestMapping(value = "/getCitySelectList", method = { RequestMethod.POST, RequestMethod.GET })
		public void getCitySelectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
			String pid = request.getParameter("pid");
			String city = request.getParameter("city");
			pid = StringUtils.isNotEmpty(pid) ? pid : "101";
			//ProvincesCity provincesCity = provincesCityService.getByProerties("id", province);
			List<ProvincesCity> provincesCityList = provincesCityService.queryByProerties("pid", Integer.parseInt(pid));
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < provincesCityList.size(); i++) {
				if(null != city && city.equals(provincesCityList.get(i).getId()+"")){
					builder.append("<option value='" + provincesCityList.get(i).getId() + "' selected='selected'>" +provincesCityList.get(i).getName()+  "</option>");
				} else {
					builder.append("<option value='" + provincesCityList.get(i).getId() + "'>" +provincesCityList.get(i).getName()+  "</option>");
				}
			}
			writeJSON(response, builder.toString());
		}
} 


 
