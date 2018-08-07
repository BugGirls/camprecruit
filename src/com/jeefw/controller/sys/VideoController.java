package com.jeefw.controller.sys;

import java.io.IOException; 
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
import org.springframework.web.bind.annotation.RequestParam;

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController; 
import com.jeefw.model.sys.Video; 
import com.jeefw.service.sys.VideoService;

import core.support.ExtJSBaseParameter;
import core.support.JqGridPageView;
import core.support.QueryResult;

/**
 * 字典的控制层
 * @ 
 */
@Controller
@RequestMapping("/sys/video")
public class VideoController extends JavaEEFrameworkBaseController<Video> implements Constant {

	@Resource
	private VideoService videoService;

	@RequestMapping(value = "/getTest")
	public void test(HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<Video>vlist= videoService.doQueryAll();
		
		 JSONArray ary1=JSONArray.fromObject(vlist);
//		for (int i = 0; i < vlist.size(); i++) {
//			JSONObject obj1 = new JSONObject();
//			obj1.put("video", vlist.get(i));
//			ary1.add(obj1);
//		} 
		writeJSON(response, ary1);
		
		
		//jsonObject.put("msg", "12354的撒");
		//writeJSON(response,  jsonObject);
		
//		Integer firstResult =1;
//		Integer maxResults = 10;
//		String sortedObject = request.getParameter("sidx");
//		String sortedValue = request.getParameter("sord");
//		String filters = request.getParameter("filters");
//		Video video=new Video();
//		video.setFirstResult((firstResult - 1) * maxResults);
//		video.setMaxResults(maxResults);
//		Map<String, String> sortedCondition = new HashMap<String, String>();
//		sortedCondition.put(sortedObject, sortedValue);
//		video.setSortedConditions(sortedCondition);
//		QueryResult<Video> queryResult = videoService.doPaginationQuery(video);
//		JqGridPageView<Video> videoListView = new JqGridPageView<Video>();
//		videoListView.setMaxResults(maxResults);
//		List<Video> videoWithSubList = videoService.queryVideoWithSubList(queryResult.getResultList());
//		videoListView.setRows(videoWithSubList);
//		videoListView.setRecords(queryResult.getTotalCount());
//		writeJSON(response, videoListView);
	
	}
	
	// 查询字典的表格，包括分页、搜索和排序
	@RequestMapping(value = "/getVideo", method = { RequestMethod.POST, RequestMethod.GET })
	public void getVideo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer firstResult = Integer.valueOf(request.getParameter("page"));
		Integer maxResults = Integer.valueOf(request.getParameter("rows"));
		String sortedObject = request.getParameter("sidx");
		String sortedValue = request.getParameter("sord");
		String filters = request.getParameter("filters");
		Video video = new Video();
		if (StringUtils.isNotBlank(filters)) {
			JSONObject jsonObject = JSONObject.fromObject(filters);
			JSONArray jsonArray = (JSONArray) jsonObject.get("rules");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject result = (JSONObject) jsonArray.get(i);
				if (result.getString("field").equals("name") && result.getString("op").equals("eq")) {
					video.set$name(result.getString("data"));
				}
				if (result.getString("field").equals("title") && result.getString("op").equals("cn")) {
					video.set$title(result.getString("data"));
				}
			}
			if (((String) jsonObject.get("groupOp")).equalsIgnoreCase("OR")) {
				video.setFlag("OR");
			} else {
				video.setFlag("AND");
			}
		}
		video.setFirstResult((firstResult - 1) * maxResults);
		video.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		video.setSortedConditions(sortedCondition);
		QueryResult<Video> queryResult = videoService.doPaginationQuery(video);
		JqGridPageView<Video> videoListView = new JqGridPageView<Video>();
		videoListView.setMaxResults(maxResults);
		List<Video> videoWithSubList = videoService.queryVideoWithSubList(queryResult.getResultList());
		videoListView.setRows(videoWithSubList);
		videoListView.setRecords(queryResult.getTotalCount());
		writeJSON(response, videoListView);
	}

	// 保存字典的实体Bean
	@RequestMapping(value = "/saveVideo", method = { RequestMethod.POST, RequestMethod.GET })
	public void doSave(Video entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
		if (CMD_EDIT.equals(parameter.getCmd())) {
			videoService.merge(entity);
		} else if (CMD_NEW.equals(parameter.getCmd())) {
			videoService.persist(entity);
		}
		parameter.setSuccess(true);
		writeJSON(response, parameter);
	}

//	// 操作字典的删除、导出Excel、字段判断和保存
//	@RequestMapping(value = "/operateVideo", method = { RequestMethod.POST, RequestMethod.GET })
//	public void operateVideo(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		String oper = request.getParameter("oper");
//		String id = request.getParameter("id");
//		if (oper.equals("del")) {
//			String[] ids = id.split(",");
//			deleteVideo(request, response, (Long[]) ConvertUtils.convert(ids, Long.class));
//		} else if (oper.equals("excel")) {
//			response.setContentType("application/msexcel;charset=UTF-8");
//			try {
//				response.addHeader("Content-Disposition", "attachment;filename=file.xls");
//				OutputStream out = response.getOutputStream();
//				out.write(URLDecoder.decode(request.getParameter("csvBuffer"), "UTF-8").getBytes());
//				out.flush();
//				out.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		} else {
//			Map<String, Object> result = new HashMap<String, Object>();
//			String videoKey = request.getParameter("videoKey");
//			String videoValue = request.getParameter("videoValue");
//			String sequence = request.getParameter("sequence");
//			String parentVideokey = request.getParameter("parentVideokey");
//			Video video = null;
//			if (oper.equals("edit")) {
//				video = videoService.get(Long.valueOf(id));
//			}
//			Video videoKeyVideo = VideoService.getByProerties("videoKey", videoKey);
//			Video parentVideokeyVideo = videoService.getByProerties("videoKey", parentVideokey);
//			if (StringUtils.isBlank(videoKey) || StringUtils.isBlank(videoValue)) {
//				response.setStatus(HttpServletResponse.SC_LENGTH_REQUIRED);
//				result.put("message", "请填写字典编码和字典名称");
//				writeJSON(response, result);
//			} else if (null != videoKeyVideo && oper.equals("add")) {
//				response.setStatus(HttpServletResponse.SC_CONFLICT);
//				result.put("message", "此字典编码已存在，请重新输入");
//				writeJSON(response, result);
//			} else if (null != dictKeyDict && !dict.getDictKey().equalsIgnoreCase(dictKey) && oper.equals("edit")) {
//				response.setStatus(HttpServletResponse.SC_CONFLICT);
//				result.put("message", "此字典编码已存在，请重新输入");
//				writeJSON(response, result);
//			} else if (StringUtils.isNotBlank(parentDictkey) && null == parentDictkeyDict) {
//				response.setStatus(HttpServletResponse.SC_CONFLICT);
//				result.put("message", "上级字典编码输入有误，请重新输入");
//				writeJSON(response, result);
//			} else {
//				Dict entity = new Dict();
//				entity.setDictKey(dictKey);
//				entity.setDictValue(dictValue);
//				entity.setSequence(sequence == "" ? null : Long.valueOf(sequence));
//				entity.setParentDictkey(parentDictkey);
//				if (oper.equals("edit")) {
//					entity.setId(Long.valueOf(id));
//					entity.setCmd("edit");
//					doSave(entity, request, response);
//				} else if (oper.equals("add")) {
//					entity.setCmd("new");
//					doSave(entity, request, response);
//				}
//			}
//		}
//	}

	// 删除字典
	@RequestMapping("/deleteVideo")
	public void deleteVideo(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Long[] ids) throws IOException {
		boolean flag = videoService.deleteByPK(ids);
		if (flag) {
			writeJSON(response, "{success:true}");
		} else {
			writeJSON(response, "{success:false}");
		}
	}

}
