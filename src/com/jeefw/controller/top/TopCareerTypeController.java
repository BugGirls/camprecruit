package com.jeefw.controller.top;
 
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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController;
import com.jeefw.model.sys.CareerType;
import com.jeefw.service.sys.CareerTypeService;

import core.util.NumberHelper;
import core.util.StringHelper;

/**
 * 职位类别的控制层 
 */
@Controller
@RequestMapping("/top/careertype")
public class TopCareerTypeController extends JavaEEFrameworkBaseController<CareerType> implements Constant {

	@Resource
	private CareerTypeService careerTypeService;
	
		
		// 保存职位类别树
		@RequestMapping(value = "/savetree", method = { RequestMethod.POST, RequestMethod.GET })
		public void savetree(HttpServletRequest request, HttpServletResponse response) throws IOException {
			JSONArray array = JSONArray.fromObject(request.getParameter("tree"));
			if(array.size()>0){
				List<CareerType> list = careerTypeService.doQueryAll();
				for(CareerType careertype : list){
					careerTypeService.delete(careertype);
				}
				
				for(int i=0;i<array.size();i++){
					JSONObject json = array.getJSONObject(i);
					Integer id = json.getInt("id");
					String pid = StringHelper.null2String(json.get("pId"));
					String name = json.getString("name");
					CareerType careerType = new CareerType();
					careerType.setId(id);
					careerType.setName(name);
					careerType.setPid(NumberHelper.string2Int(pid,0));
					careerType.setSequence(i+1);
					careerTypeService.persist(careerType);
					JSONArray childrenArr = json.getJSONArray("children");
					boolean isParent = json.getBoolean("isParent");
					if(isParent&&childrenArr!=null&&childrenArr.size()>0){
						for(int j=0;j<childrenArr.size();j++){
							JSONObject childrenjson = childrenArr.getJSONObject(j);
							Integer childid = childrenjson.getInt("id");
							Integer childpid = childrenjson.getInt("pId");
							String childname = childrenjson.getString("name");
							CareerType careerChild = new CareerType();
							careerChild.setId(childid);
							careerChild.setName(childname);
							careerChild.setPid(childpid);
							careerChild.setSequence(j+1);
							careerTypeService.persist(careerChild);
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
			List<CareerType> mainMenuList = careerTypeService.queryByProerties("pid", 0);
			JSONObject allJSONObject = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			for (int i = 0; i < mainMenuList.size(); i++) {
				JSONObject mainJsonObject = new JSONObject();
				
				mainJsonObject.element("id", mainMenuList.get(i).getId());
				mainJsonObject.element("pId", 0);
				mainJsonObject.element("name", mainMenuList.get(i).getName());
				mainJsonObject.element("url", mainMenuList.get(i).getData_url());
				mainJsonObject.element("open", true);
				List<CareerType> subList = careerTypeService.queryByProerties("pid", mainMenuList.get(i).getId());
				if(subList!=null &&subList.size()>0){
					for(int j = 0; j < subList.size(); j++){
						JSONObject subJsonObject = new JSONObject();
						subJsonObject.element("id", subList.get(j).getId());
						subJsonObject.element("pId",subList.get(j).getPid());
						subJsonObject.element("name", subList.get(j).getName());
						subJsonObject.element("url", subList.get(j).getData_url());
						jsonArray.add(subJsonObject);
						List<CareerType> secsubList = careerTypeService.queryByProerties("pid", subList.get(j).getId());
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
		
		
		// 取得职位类别
		@RequestMapping(value = "/getcareertype", method = { RequestMethod.POST, RequestMethod.GET })
		public void getCareerType(HttpServletRequest request, HttpServletResponse response) throws IOException {
			List<CareerType> mainMenuList = careerTypeService.queryByProerties("pid", 0);
			JSONObject allJSONObject = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			if(mainMenuList!=null && mainMenuList.size()>0){
				int rootid = mainMenuList.get(0).getId();
				List<CareerType> subList = careerTypeService.queryByProerties("pid", rootid);
				if(subList!=null &&subList.size()>0){
					for(int j = 0; j < subList.size(); j++){
						JSONObject subJsonObject = new JSONObject();
						subJsonObject.element("id", subList.get(j).getId());
						subJsonObject.element("name", subList.get(j).getName());
						
						
						List<CareerType> secsubList = careerTypeService.queryByProerties("pid", subList.get(j).getId());
						if(secsubList!=null &&secsubList.size()>0){
							subJsonObject.element("childcareers", secsubList);
						}
						jsonArray.add(subJsonObject);
					}
				}
			}
			allJSONObject.element("status", "OK");
			allJSONObject.element("careertypes", jsonArray);
			writeJSON(response, allJSONObject);
		}
		
} 


 
