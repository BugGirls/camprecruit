package com.jeefw.controller.top;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController;
import com.jeefw.model.sys.CompanyUser;
import com.jeefw.model.sys.PersonalMessage;
import com.jeefw.model.sys.Position;
import com.jeefw.model.sys.TzzUser;
import com.jeefw.service.sys.PositionService;

import core.support.JqGridPageView;
import core.util.NumberHelper;
import core.util.StringHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 职位的控制层
 */
@Controller
@RequestMapping("/top/position")
public class TopPositionController extends JavaEEFrameworkBaseController<Position> implements Constant {

	@Resource
	private PositionService positionService;

	// 保存职位类别树
	@RequestMapping(value = "/savePosition", method = { RequestMethod.POST, RequestMethod.GET })
	public void savetree(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject jsonObj = new JSONObject();
		try {
			CompanyUser tzzUser = (CompanyUser) request.getSession().getAttribute("companyuser");
			if (tzzUser == null) {
				jsonObj.put("success", false);
				jsonObj.put("content", "/jsp/clogin.jsp");
				jsonObj.put("msg", "用户信息获取失败！");
			} else {
				Integer id = NumberHelper.string2Int(request.getParameter("id"));
				Position position = new Position();
				
				Integer type = NumberHelper.string2Int(request.getParameter("type"));
				String name = request.getParameter("name");
//				name = new String(name.getBytes("iso-8859-1"), "utf-8");
				String department = request.getParameter("department");
				Integer nature = NumberHelper.string2Int(request.getParameter("nature"));
				Integer salaryfrom = NumberHelper.string2Int(request.getParameter("salaryfrom"));
				Integer salaryto = NumberHelper.string2Int(request.getParameter("salaryto"));
				String workcity = request.getParameter("workcity");
				Integer experience = NumberHelper.string2Int(request.getParameter("experience"));
				Integer edubackground = NumberHelper.string2Int(request.getParameter("edubackground"));
				String brief = request.getParameter("brief");
				String positionDetail = request.getParameter("positionDetail");
				String workplace = request.getParameter("workplace");
				String positionLng = request.getParameter("positionLng");
				String positionLat = request.getParameter("positionLat");
				String email = request.getParameter("email");
				String forwardEmail = request.getParameter("forwardEmail");

				position.setType(type);
				position.setName(name);
				position.setCompanyid(tzzUser.getId());
				position.setDepartment(department);
				position.setNature(nature);
				position.setSalaryfrom(salaryfrom);
				position.setSalaryto(salaryto);
				position.setWorkcity(workcity);
				position.setExperience(experience);
				position.setEdubackground(edubackground);
				position.setBrief(brief);
				position.setPositionDetail(positionDetail);
				position.setWorkplace(workplace);
				
				
				position.setEmail(email);
				position.setForwardEmail(forwardEmail);
				position.setCreator(tzzUser.getId());
				position.setCreatetime(new Date());
				
				if(null!=id&&-1!=id){
					Position positionT = positionService.get(id);
					if(null==positionT)
						positionService.persist(position);
					else{
						position.setId(id);
						positionService.merge(position);
					}
				}else
					positionService.persist(position);
				
				jsonObj.put("success", true);
				jsonObj.put("content", "/jsp/position_success.jsp?id="+position.getId());
				jsonObj.put("msg", "保存成功！");

				writeJSON(response, jsonObj);
			}
			// showUserDetail(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			jsonObj.put("success", false);
			jsonObj.put("msg", "操作失败！");
			writeJSON(response, jsonObj);
		}
	}

	// 取得职位类别树
	@RequestMapping(value = "/getPositionList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getPositionTree(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userphone = StringHelper.null2String(request.getParameter("userphone"));
		String openid = StringHelper.null2String(request.getParameter("openid"));
		String userid = StringHelper.null2String(request.getParameter("userid"));
		CompanyUser tzzUser = (CompanyUser) request.getSession().getAttribute("companyuser");
		if (tzzUser == null) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("success", false);
			jsonObj.put("content", "/jsp/clogin.jsp");
			jsonObj.put("msg", "用户信息获取失败！");
			writeJSON(response, jsonObj);
		} else {
			//String userid = StringHelper.null2String(request.getParameter("userid"));
			Map<String, String> sortedCondition = new HashMap<String, String>();
			sortedCondition.put("createtime,id", "desc");
			String[] names = {"companyid","status"};
			Object[] values = {tzzUser.getId(),1};
			List<Position> messagelist = positionService.queryByProerties(names, values,sortedCondition);
			JqGridPageView<Position> authorityListView = new JqGridPageView<Position>();
			authorityListView.setRows(messagelist);
			writeJSON(response, authorityListView);
		}
	}

	// 取得职位类别
	@RequestMapping(value = "/getcareertype", method = { RequestMethod.POST, RequestMethod.GET })
	public void getPosition(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Position> mainMenuList = positionService.queryByProerties("pid", 0);
		JSONObject allJSONObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		if (mainMenuList != null && mainMenuList.size() > 0) {
			int rootid = mainMenuList.get(0).getId();
			List<Position> subList = positionService.queryByProerties("pid", rootid);
			if (subList != null && subList.size() > 0) {
				for (int j = 0; j < subList.size(); j++) {
					JSONObject subJsonObject = new JSONObject();
					subJsonObject.element("id", subList.get(j).getId());
					subJsonObject.element("name", subList.get(j).getName());

					List<Position> secsubList = positionService.queryByProerties("pid", subList.get(j).getId());
					if (secsubList != null && secsubList.size() > 0) {
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
