package com.jeefw.controller.sys;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContext;

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController;
import com.jeefw.model.sys.ReplyMsgSet;
import com.jeefw.service.sys.ReplyMsgSetService;
import com.jeefw.service.sys.SysUserService;

import core.support.ExtJSBaseParameter;
import core.support.JqGridPageView;
import core.support.QueryResult;
import core.util.JavaEEFrameworkUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 回复消息设置的控制层 
 */
@Controller
@RequestMapping("/sys/replymsgset")
public class ReplyMsgSetController extends JavaEEFrameworkBaseController<ReplyMsgSet> implements Constant {

	@Resource
	private ReplyMsgSetService replyMsgSetService;
	@Resource
	private SysUserService sysUserService;

	// 查询回复消息设置的表格，包括分页、搜索和排序
	@RequestMapping(value = "/getreplymsgset", method = { RequestMethod.POST, RequestMethod.GET })
	public void getReplymsgset(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer firstResult = Integer.valueOf(request.getParameter("page"));
		Integer maxResults = Integer.valueOf(request.getParameter("rows"));
		String sortedObject = request.getParameter("sidx");
		String sortedValue = request.getParameter("sord");
		String filters = request.getParameter("filters");
		ReplyMsgSet replymsgset = new ReplyMsgSet();
		if (StringUtils.isNotBlank(filters)) {
			JSONObject jsonObject = JSONObject.fromObject(filters);
			JSONArray jsonArray = (JSONArray) jsonObject.get("rules");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject result = (JSONObject) jsonArray.get(i);
				if (result.getString("field").equals("receiveMsgKey") && result.getString("op").equals("eq")) {
					replymsgset.set$eq_receiveMsgKey(result.getString("data"));
				}
				if (result.getString("field").equals("receiveMsgValue") && result.getString("op").equals("cn")) {
					replymsgset.set$like_receiveMsgValue(result.getString("data"));
				}
			}
			if (((String) jsonObject.get("groupOp")).equalsIgnoreCase("OR")) {
				replymsgset.setFlag("OR");
			} else {
				replymsgset.setFlag("AND");
			}
		}
		replymsgset.setFirstResult((firstResult - 1) * maxResults);
		replymsgset.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		replymsgset.setSortedConditions(sortedCondition);
		QueryResult<ReplyMsgSet> queryResult = replyMsgSetService.doPaginationQuery(replymsgset);
		JqGridPageView<ReplyMsgSet> roleListView = new JqGridPageView<ReplyMsgSet>();
		roleListView.setMaxResults(maxResults);
		roleListView.setRows(queryResult.getResultList());
		roleListView.setRecords(queryResult.getTotalCount());
		writeJSON(response, roleListView);
	}

	// 保存回复消息设置的实体Bean
	@RequestMapping(value = "/savereplymsgset", method = { RequestMethod.POST, RequestMethod.GET })
	public void doSave(ReplyMsgSet entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
		if (CMD_EDIT.equals(parameter.getCmd())) {
			replyMsgSetService.merge(entity);
		} else if (CMD_NEW.equals(parameter.getCmd())) {
			replyMsgSetService.persist(entity);
		}
		parameter.setSuccess(true);
		writeJSON(response, parameter);
	}

	// 操作回复消息设置的删除、导出Excel、字段判断和保存
	@RequestMapping(value = "/operatereplymsgset", method = { RequestMethod.POST, RequestMethod.GET })
	public void operateReplymsgset(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String oper = request.getParameter("oper");
		String id = request.getParameter("id");
		if (oper.equals("del")) {
			String[] ids = id.split(",");
			deleteReplymsgset(request, response, (Long[]) ConvertUtils.convert(ids, Long.class));
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
			Map<String, Object> result = new HashMap<String, Object>();
			String receive_msg = request.getParameter("receive_msg");
			String reply_type = request.getParameter("reply_type");
			String reply_title = request.getParameter("reply_title");
			String reply_description = request.getParameter("reply_description");
			String pic_url = request.getParameter("pic_url");
			String url = request.getParameter("url");
			ReplyMsgSet replymsgset = null;
			if (oper.equals("edit")) {
				replymsgset = replyMsgSetService.get(Long.valueOf(id));
			}
			ReplyMsgSet receivemsgset = replyMsgSetService.getByProerties("receive_msg", receive_msg);
			if (StringUtils.isBlank(receive_msg) || StringUtils.isBlank(receive_msg)) {
				response.setStatus(HttpServletResponse.SC_LENGTH_REQUIRED);
				result.put("message", "请填写微信公众号接收消息");
				writeJSON(response, result);
			} else if (null != receivemsgset && oper.equals("add")) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.put("message", "此回复消息设置已存在，请重新输入");
				writeJSON(response, result);
			} else {
				ReplyMsgSet entity = new ReplyMsgSet();
				entity.setReceive_msg(receive_msg);
				entity.setReply_type(reply_type);
				entity.setReply_title(reply_title);
				entity.setReply_description(reply_description);
				entity.setPic_url(pic_url);
				entity.setUrl(url);
				if (oper.equals("edit")) {
					entity.setId(Integer.valueOf(id));
					entity.setCmd("edit");
					doSave(entity, request, response);
				} else if (oper.equals("add")) {
					entity.setCmd("new");
					doSave(entity, request, response);
				}
			}
		}
	}

	// 删除回复消息设置
	@RequestMapping("/deletereplymsgset")
	public void deleteReplymsgset(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Long[] ids) throws IOException {
		boolean flag = false;
		for (int i = 0; i < ids.length; i++) {
			Long id = ids[i];
			flag = replyMsgSetService.deleteByPK(id);
		}
		if (flag) {
			writeJSON(response, "{success:true}");
		} else {
			writeJSON(response, "{success:false}");
		}
	}

	// 获取回复消息设置的下拉框
	@RequestMapping(value = "/getreplymsgsetList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getRoleSelectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<ReplyMsgSet> roleList = replyMsgSetService.doQueryAll();
		StringBuilder builder = new StringBuilder();
		builder.append("<select>");
//		for (int i = 0; i < roleList.size(); i++) {
//			builder.append("<option value='" + roleList.get(i).getRoleKey() + "'>" + roleList.get(i).getRoleValue() + "</option>");
//		}
		builder.append("</select>");
		writeJSON(response, builder.toString());
	}

	private static SimpleDateFormat sdfa = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	private static SimpleDateFormat sdfb = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 上传图片/sys/TzzSalesPromotion/fileUpload
	 * 
	 * @param file
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	public void fileUpload(@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequestContext requestContext = new RequestContext(request);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String id = request.getParameter("id");
		String fimage = request.getParameter("img");

		List<MultipartFile> list = new ArrayList<MultipartFile>();
		list.add(file);

		JSONObject json = new JSONObject();
		String image = "";
		if (file != null && !file.isEmpty()) {
			if (file.getSize() > 2097152) {// ============最大两兆
				json.put("message", requestContext.getMessage("g_fileTooLarge"));
			} else {
				try {
					String originalFilename = file.getOriginalFilename();
					// System.out.println(originalFilename);
					String fileName = sdfa.format(new Date()) + JavaEEFrameworkUtils.getRandomString(3)
							+ originalFilename.substring(originalFilename.lastIndexOf("."));
					File filePath = new File(
							getClass().getClassLoader().getResource("/").getPath().replaceAll("%20", " ")
									.replace("/WEB-INF/classes/", "/static/upload/img/" + sdf.format(new Date())));
					if (!filePath.exists()) {
						filePath.mkdirs();
					}
					file.transferTo(new File(filePath.getAbsolutePath() + "\\" + fileName));
					String destinationFilePath = "/static/upload/img/" + sdf.format(new Date()) + "/" + fileName;
					image = destinationFilePath;
					json.put("status", "OK");
					json.put("url", destinationFilePath);
					json.put("message", requestContext.getMessage("g_uploadSuccess"));
				} catch (Exception e) {
					e.printStackTrace();
					json.put("message", requestContext.getMessage("g_uploadFailure"));
				}
			}
		} else {
			json.put("message", requestContext.getMessage("g_uploadNotExists"));
			if (null != fimage && !"".equals(fimage))
				image = fimage;
		}

		String receive_msg = request.getParameter("receive_msg");
		String reply_type = request.getParameter("reply_type");
		String reply_title = request.getParameter("reply_title");
		String reply_description = request.getParameter("reply_description");
		String url = request.getParameter("url");

		ReplyMsgSet replyMsgSet = new ReplyMsgSet();

		if (id != null) {
			ReplyMsgSet entity = replyMsgSetService.get(Integer.parseInt(id));
			replyMsgSet.setId(entity.getId());
			replyMsgSet.setReceive_msg(receive_msg);
			replyMsgSet.setReply_type(reply_type);
			replyMsgSet.setReply_title(reply_title);
			replyMsgSet.setReply_description(reply_description);
			replyMsgSet.setPic_url(image);
			replyMsgSet.setUrl(url);
			replyMsgSetService.merge(replyMsgSet);
		} else {
			replyMsgSet.setReceive_msg(receive_msg);
			replyMsgSet.setReply_type(reply_type);
			replyMsgSet.setReply_title(reply_title);
			replyMsgSet.setReply_description(reply_description);
			replyMsgSet.setPic_url(image);
			replyMsgSet.setUrl(url);
			replyMsgSetService.persist(replyMsgSet);
		}
		String htmlString = "<script language=javascript> history.go(-1);</script>";
		writeJSON(response, htmlString);
	}
	
	// 获取单条记录
		@RequestMapping("/getReplyMsgSet")
		public void getReplyMsgSet(HttpServletRequest request, HttpServletResponse response,
				@RequestParam("id") Integer id) throws IOException {
			ReplyMsgSet replyMsgSet = replyMsgSetService.get(id);
			JSONObject json = new JSONObject();
			json.put("obj", replyMsgSet);
			json.put("success", true);
			writeJSON(response, json);
		}
}
