package com.jeefw.controller.sys;

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController;
import com.jeefw.model.sys.FeModule;
import com.jeefw.service.sys.FeModuleService;
import core.support.ExtJSBaseParameter;
import core.support.JqGridPageView;
import core.support.QueryResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户端首页
 *
 * @author Empress
 * @data 2018/7/6
 */
@Controller
@RequestMapping(value = "/client")
public class ClientHomePageController extends JavaEEFrameworkBaseController<FeModule> implements Constant {

    @Resource
    private FeModuleService feModuleService;

    /**
     * 获取客户端首页模块信息
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/getModuleInfo", method = {RequestMethod.POST, RequestMethod.GET})
    public void getModuleInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer allianceId = getCurrentAllianceId();
        Integer firstResult = Integer.valueOf(request.getParameter("page"));
        Integer maxResults = Integer.valueOf(request.getParameter("rows"));
        String sortedObject = request.getParameter("sidx");
        String sortedValue = request.getParameter("sord");
        String filters = request.getParameter("filters");
        FeModule feModule = new FeModule();
        if (StringUtils.isNotBlank(filters)) {
            JSONObject jsonObject = JSONObject.fromObject(filters);
            JSONArray jsonArray = (JSONArray) jsonObject.get("rules");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject result = (JSONObject) jsonArray.get(i);
                if (result.getString("field").equals("name") && result.getString("op").equals("cn")) {
                    feModule.set$like_name(result.getString("data"));
                }
            }
            if (((String) jsonObject.get("groupOp")).equalsIgnoreCase("OR")) {
                feModule.setFlag("OR");
            } else {
                feModule.setFlag("AND");
            }
        }
        feModule.set$eq_allianceId(allianceId);
        feModule.setFirstResult((firstResult - 1) * maxResults);
        feModule.setMaxResults(maxResults);
        Map<String, String> sortedCondition = new HashMap<>();
        sortedCondition.put(sortedObject, sortedValue);
        feModule.setSortedConditions(sortedCondition);
        QueryResult<FeModule> queryResult = feModuleService.doPaginationQuery(feModule);
        JqGridPageView<FeModule> dictListView = new JqGridPageView<FeModule>();
        dictListView.setMaxResults(maxResults);
//        List<ProductInfo> dictWithSubList = feModuleService.queryProductInfoWithSubList(queryResult.getResultList());
        dictListView.setRows(queryResult.getResultList());
        dictListView.setRecords(queryResult.getTotalCount());
        writeJSON(response, dictListView);
    }

    /**
     * 获取当前登录用户下的所有模块信息-前台
     *
     * @return
     */
    @RequestMapping(value = "/getModuleInfoFe", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public List<FeModule> getModuleInfoFe() {
        Integer allianceId = getCurrentAllianceId();
        List<FeModule> feModuleList = feModuleService.queryByProerties("allianceId", allianceId);
        return feModuleList;
    }

    /**
     * 模块的删除、导出Excel、字段判断和保存
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/operateModule", method = {RequestMethod.POST, RequestMethod.GET})
    public void operateModule(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer allianceId = getCurrentAllianceId();
        String oper = request.getParameter("oper");
        String id = request.getParameter("id");
        if (oper.equals("del")) {
            String[] ids = id.split(",");
            deleteModule(request, response, (Integer[]) ConvertUtils.convert(ids, Integer.class));
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
            String name = request.getParameter("name");
            String imageUrl = request.getParameter("imageUrl");
            String accessUrl = request.getParameter("accessUrl");
            String seq = request.getParameter("seq");
            FeModule feModule = null;
            if (oper.equals("edit")) {
                feModule = feModuleService.get(Integer.valueOf(id));
            }
            FeModule nameDict = feModuleService.getByProerties("name", name);
            if (StringUtils.isBlank(name)) {
                response.setStatus(HttpServletResponse.SC_LENGTH_REQUIRED);
                result.put("message", "请填写模块名称");
                writeJSON(response, result);
            } else if (StringUtils.isBlank(imageUrl)) {
                response.setStatus(HttpServletResponse.SC_LENGTH_REQUIRED);
                result.put("message", "请填写图片地址");
                writeJSON(response, result);
            } else if (StringUtils.isBlank(accessUrl)) {
                response.setStatus(HttpServletResponse.SC_LENGTH_REQUIRED);
                result.put("message", "请填写访问路径");
                writeJSON(response, result);
            } else if (null != nameDict && oper.equals("add")) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                result.put("message", "此模块已存在，请重新输入");
                writeJSON(response, result);
            } else if (null != nameDict && !feModule.getName().equalsIgnoreCase(name) && oper.equals("edit")) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                result.put("message", "此模块已存在，请重新输入");
                writeJSON(response, result);
            } else {
                FeModule entity = new FeModule();
                entity.setName(name);
                entity.setImageUrl(imageUrl);
                entity.setSeq(Integer.valueOf(seq));
                entity.setAccessUrl(accessUrl);
                if (oper.equals("edit")) {
                    entity.setId(Integer.valueOf(id));
                    entity.setCmd("edit");
                    saveModule(entity, request, response);
                } else if (oper.equals("add")) {
                    entity.setAllianceId(allianceId);
                    entity.setCmd("new");
                    saveModule(entity, request, response);
                }
            }
        }
    }

    /**
     * 删除模块
     *
     * @param request
     * @param response
     * @param ids
     * @throws IOException
     */
    @RequestMapping("/deleteModule")
    public void deleteModule(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Integer[] ids) throws IOException {
        boolean flag = feModuleService.deleteByPK(ids);
        if (flag) {
            writeJSON(response, "{success:true}");
        } else {
            writeJSON(response, "{success:false}");
        }
    }

    /**
     * 添加模块信息
     *
     * @param entity
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/saveModule", method = {RequestMethod.POST, RequestMethod.GET})
    public void saveModule(FeModule entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
        if (CMD_EDIT.equals(parameter.getCmd())) {
            feModuleService.merge(entity);
        } else if (CMD_NEW.equals(parameter.getCmd())) {
            feModuleService.persist(entity);
        }
        parameter.setSuccess(true);
        writeJSON(response, parameter);
    }

}
