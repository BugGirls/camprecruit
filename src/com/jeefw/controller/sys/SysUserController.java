package com.jeefw.controller.sys;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContext;

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController;
import com.jeefw.model.sys.AllianceBusiness;
import com.jeefw.model.sys.Attachment;
import com.jeefw.model.sys.Authority;
import com.jeefw.model.sys.Emailphone;
import com.jeefw.model.sys.IntoWarehouseRecord;
import com.jeefw.model.sys.IntoWarehouseRecordDatail;
import com.jeefw.model.sys.Mail;
import com.jeefw.model.sys.Role;
import com.jeefw.model.sys.SysUser;
import com.jeefw.model.sys.TzzDictionary;
import com.jeefw.model.sys.TzzNews;
import com.jeefw.model.sys.TzzQuestion;
import com.jeefw.model.sys.TzzUser;
import com.jeefw.service.sys.AllianceBusinessService;
import com.jeefw.service.sys.AttachmentService;
import com.jeefw.service.sys.AuthorityService;
import com.jeefw.service.sys.EmailphoneService;
import com.jeefw.service.sys.IntoWarehouseRecordDetailService;
import com.jeefw.service.sys.IntoWarehouseRecordService;
import com.jeefw.service.sys.MailService;
import com.jeefw.service.sys.RoleService;
import com.jeefw.service.sys.SysUserService;
import com.jeefw.service.sys.TzzDictionaryService;
import com.jeefw.service.sys.TzzNewsService;
import com.jeefw.service.sys.TzzQuestionService;
import com.jeefw.service.sys.TzzUserService;

import core.support.ExtJSBaseParameter;
import core.support.JqGridPageView;
import core.support.QueryResult;
import core.util.DateHelper;
import core.util.HtmlUtils;
import core.util.JavaEEFrameworkUtils;
import core.util.NumberHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 用户的控制层 @
 */
@Controller
@RequestMapping("/sys/sysuser")
public class SysUserController extends JavaEEFrameworkBaseController<SysUser> implements Constant {

    private static final Log log = LogFactory.getLog(SysUserController.class);
    @Resource
    private SysUserService sysUserService;
    @Resource
    private AttachmentService attachmentService;
    @Resource
    private AuthorityService authorityService;
    @Resource
    private RoleService roleService;
    @Resource
    private TzzNewsService tzzNewsService;
    @Resource
    private MailService mailService;
    @Resource
    private TzzQuestionService tzzQuestionService;
    @Resource
    private TzzUserService tzzUserService;
    @Resource
    private TzzDictionaryService tzzDictionaryService;
    @Resource
    private EmailphoneService emailphoneService;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Resource
    private IntoWarehouseRecordService intoWarehouseRecordService;
    @Resource
    private IntoWarehouseRecordDetailService intoWarehouseRecordDetailService;
    @Resource
    AllianceBusinessService allianceBusinessService;

    /**
     * /sys/sysuser/newpwd
     *
     * @param sysUserModel 修改密码
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/newpwd")
    public void newpwd(SysUser sysUserModel, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String pwd = request.getParameter("pwd");
        String ids = request.getParameter("id");
        long id;
        JSONObject jsonObject = new JSONObject();
        if (!ids.equals("")) {
            id = Long.valueOf(ids);
            SysUser entity = new SysUser();
            entity = sysUserService.get(id);
            entity.setPassword(new Sha256Hash(pwd).toHex());
            sysUserService.update(entity);
            jsonObject.put("err", 0);
        } else {
            jsonObject.put("err", 1);
        }
        writeJSON(response, jsonObject);
    }


    // 登录
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public void login(SysUser sysUserModel, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Map<String, Object> result = new HashMap<String, Object>();
        SysUser sysUser = sysUserService.getByProerties("email", sysUserModel.getEmail());
        if (sysUser == null || sysUser.getStatus() == true) { // 用户名有误或已被禁用
            result.put("result", -1);
            writeJSON(response, result);
            return;
        }
        if (!sysUser.getPassword().equals(new Sha256Hash(sysUserModel.getPassword()).toHex())) { // 密码错误
            result.put("result", -2);
            writeJSON(response, result);
            return;
        }
        sysUser.setLastLoginTime(new Date());
        sysUserService.merge(sysUser);
        Subject subject = SecurityUtils.getSubject();
        subject.login(new UsernamePasswordToken(sysUserModel.getEmail(), sysUserModel.getPassword(),
                sysUserModel.isRememberMe()));
        Session session = subject.getSession();
        session.setAttribute(SESSION_SYS_USER, sysUser);
        result.put("result", 1);
        writeJSON(response, result);
    }

    // 跳转到主页，获取菜单并授权
    @RequestMapping("/home")
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        if (session.getAttribute(SESSION_SYS_USER) == null) {
            return new ModelAndView();
        } else {
            SysUser sysUser = (SysUser) session.getAttribute(SESSION_SYS_USER);
            String globalRoleKey = sysUser.getRoles().iterator().next().getRoleKey();
            try {
                Map<String, String> sortedCondition = new HashMap<String, String>();
                sortedCondition.put("sequence", "ASC");
                List<TzzQuestion> tzzQuestions = tzzQuestionService.queryByProerties("state", "0");
                request.setAttribute("qsize", tzzQuestions.size());

                // request.setAttribute("aaa", 111);
                List<Authority> mainMenuList = authorityService.queryByProerties("parentMenuCode", "0",
                        sortedCondition);
                List<Authority> allMenuList = authorityService.queryAllMenuList(globalRoleKey, mainMenuList);
                return new ModelAndView("back/index", "authorityList", allMenuList);
            } catch (Exception e) {
                log.error(e.toString());
                return new ModelAndView();
            }
        }
    }

    // 注册
    @RequestMapping("/register")
    public void register(SysUser sysUserModel, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Map<String, Object> result = new HashMap<String, Object>();
        SysUser emailSysUser = sysUserService.getByProerties("email", sysUserModel.getEmail());
        if (emailSysUser != null) {
            result.put("result", -1);
            writeJSON(response, result);
            return;
        }
        SysUser sysUser = new SysUser();
        sysUser.setUserName(sysUserModel.getUserName());
        sysUser.setSex(sysUserModel.getSex());
        sysUser.setEmail(sysUserModel.getEmail());
        sysUser.setPhone(sysUserModel.getPhone());
        sysUser.setBirthday(sysUserModel.getBirthday());
        // sysUser.setPassword(MD5.crypt(sysUserModel.getPassword()));
        sysUser.setPassword(new Sha256Hash(sysUserModel.getPassword()).toHex());
        sysUser.setStatus(false);
        sysUser.setLastLoginTime(new Date());

        Set<Role> roles = new HashSet<Role>();
        Role role = roleService.getByProerties("roleKey", "ROLE_USER");
        roles.add(role);
        sysUser.setRoles(roles);

        sysUserService.persist(sysUser);
        // sysUserService.saveSysuserAndRole(sysUser.getId(), 3);

        Subject subject = SecurityUtils.getSubject();
        subject.login(new UsernamePasswordToken(sysUserModel.getEmail(), sysUserModel.getPassword()));
        Session session = subject.getSession();
        session.setAttribute(SESSION_SYS_USER, sysUser);

        result.put("result", 1);
        writeJSON(response, result);
    }

    // 获取个人资料信息
    @RequestMapping("/sysuserprofile")
    public ModelAndView sysuserprofile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SysUser sysuser = sysUserService.get(((SysUser) request.getSession().getAttribute(SESSION_SYS_USER)).getId());
        SysUser sysUserWithAvatar = sysUserService.getSysUserWithAvatar(sysuser);
        return new ModelAndView("back/sysuserprofile", "sysuser", sysUserWithAvatar);
    }

    // 登出
    @RequestMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SecurityUtils.getSubject().logout();
        response.sendRedirect("/jeefw/login.jsp");
    }

    // 发送邮件找回密码
    @RequestMapping("/retrievePassword")
    public void retrievePassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> result = new HashMap<String, Object>();
        String email = request.getParameter("email");
        SysUser sysUser = sysUserService.getByProerties("email", email);
        if (sysUser == null || sysUser.getStatus() == true) { // 用户名有误或已被禁用
            result.put("result", -1);
            writeJSON(response, result);
            return;
        }

        Emailphone emailphone = new Emailphone();
        emailphone = emailphoneService.getByProerties("estate", "1");
        int random = (int) (Math.random() * 1000000);
        for (int i = 0; i != 1; ) {
            if (random < 100000) {
                random = (int) (Math.random() * 1000000);
            } else {
                i = 1;
            }
        }
        sysUser.setPassword(new Sha256Hash(random + "").toHex());
        sysUserService.merge(sysUser);
        sysUserService.clear();
        SimpleEmail emailUtil = new SimpleEmail();
        emailUtil.setCharset("utf-8");
        emailUtil.setHostName(emailphone.getSmtp());
        try {
            emailUtil.addTo(email, sysUser.getUserName());
            emailUtil.setAuthenticator(new DefaultAuthenticator(emailphone.getEmail(), emailphone.getPwd()));// 参数是您的真实邮箱和密码
            emailUtil.setFrom(emailphone.getEmail(), "亲子淘");
            emailUtil.setSubject("亲子淘后台管理中心密码找回");
            emailUtil.setMsg("【亲子淘后台管理中心】本邮件为找回密码邮件！请妥善保管您的密码，建议登陆后重新设置密码，用户名为" + sysUser.getUserName() + "，您新的登录密码是："
                    + random + "");
            emailUtil.send();
        } catch (Exception e) {
            result.put("result", -2);
            writeJSON(response, result);
            return;
        }
        result.put("result", 1);
        writeJSON(response, result);
    }

    // 更改密码
    @RequestMapping("/resetPassword")
    public void resetPassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String password = request.getParameter("password");
        Long id = ((SysUser) request.getSession().getAttribute(SESSION_SYS_USER)).getId();
        // sysUserService.updateByProperties("id", id, "password",
        // MD5.crypt(password));
        sysUserService.updateByProperties("id", id, "password", new Sha256Hash(password).toHex());
        sysUserService.updateByProperties("id", id, "plaink", password);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        writeJSON(response, result);
    }

    // 查询用户的表格，包括分页、搜索和排序
    @RequestMapping(value = "/getSysUser", method = {RequestMethod.POST, RequestMethod.GET})
    public void getSysUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer firstResult = Integer.valueOf(request.getParameter("page"));
        Integer maxResults = Integer.valueOf(request.getParameter("rows"));
        String sortedObject = request.getParameter("sidx");
        String sortedValue = request.getParameter("sord");
        String filters = request.getParameter("filters");
        SysUser sysUser = new SysUser();
        if (StringUtils.isNotBlank(filters)) {
            JSONObject jsonObject = JSONObject.fromObject(filters);
            JSONArray jsonArray = (JSONArray) jsonObject.get("rules");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject result = (JSONObject) jsonArray.get(i);
                if (result.getString("field").equals("email") && result.getString("op").equals("eq")) {
                    sysUser.set$eq_email(result.getString("data"));
                }
                if (result.getString("field").equals("userName") && result.getString("op").equals("cn")) {
                    sysUser.set$like_userName(result.getString("data"));
                }
            }
            if (((String) jsonObject.get("groupOp")).equalsIgnoreCase("OR")) {
                sysUser.setFlag("OR");
            } else {
                sysUser.setFlag("AND");
            }
        }
        sysUser.setFirstResult((firstResult - 1) * maxResults);
        sysUser.setMaxResults(maxResults);
        Map<String, String> sortedCondition = new HashMap<String, String>();
        sortedCondition.put(sortedObject, sortedValue);
        sysUser.setSortedConditions(sortedCondition);
        QueryResult<SysUser> queryResult = sysUserService.doPaginationQuery(sysUser);
        JqGridPageView<SysUser> sysUserListView = new JqGridPageView<SysUser>();
        sysUserListView.setMaxResults(maxResults);
        List<SysUser> sysUserCnList = sysUserService.querySysUserCnList(queryResult.getResultList());
        sysUserListView.setRows(sysUserCnList);
        sysUserListView.setRecords(queryResult.getTotalCount());
        writeJSON(response, sysUserListView);
    }


    // 查询用户的表格，包括分页、搜索和排序
    @RequestMapping(value = "/getSysUserSub", method = {RequestMethod.POST, RequestMethod.GET})
    public void getSysUserSub(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer allianceId = getCurrentAllianceId();
        Integer firstResult = Integer.valueOf(request.getParameter("page"));
        Integer maxResults = Integer.valueOf(request.getParameter("rows"));
        String sortedObject = request.getParameter("sidx");
        String sortedValue = request.getParameter("sord");
        String filters = request.getParameter("filters");
        SysUser sysUser = new SysUser();
        if (StringUtils.isNotBlank(filters)) {
            JSONObject jsonObject = JSONObject.fromObject(filters);
            JSONArray jsonArray = (JSONArray) jsonObject.get("rules");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject result = (JSONObject) jsonArray.get(i);
                if (result.getString("field").equals("email") && result.getString("op").equals("eq")) {
                    sysUser.set$eq_email(result.getString("data"));
                }
                if (result.getString("field").equals("userName") && result.getString("op").equals("cn")) {
                    sysUser.set$like_userName(result.getString("data"));
                }
            }
            if (((String) jsonObject.get("groupOp")).equalsIgnoreCase("OR")) {
                sysUser.setFlag("OR");
            } else {
                sysUser.setFlag("AND");
            }
        }
        sysUser.set$eq_allianceId(allianceId);
        sysUser.setFirstResult((firstResult - 1) * maxResults);
        sysUser.setMaxResults(maxResults);
        Map<String, String> sortedCondition = new HashMap<String, String>();
        sortedCondition.put(sortedObject, sortedValue);
        sysUser.setSortedConditions(sortedCondition);
        QueryResult<SysUser> queryResult = sysUserService.doPaginationQuery(sysUser);
        JqGridPageView<SysUser> sysUserListView = new JqGridPageView<SysUser>();
        sysUserListView.setMaxResults(maxResults);
        List<SysUser> sysUserCnList = sysUserService.querySysUserCnList(queryResult.getResultList());
        sysUserListView.setRows(sysUserCnList);
        sysUserListView.setRecords(queryResult.getTotalCount());
        writeJSON(response, sysUserListView);
    }

    // 保存用户的实体Bean
    @RequestMapping(value = "/saveSysUser", method = {RequestMethod.POST, RequestMethod.GET})
    public void doSave(SysUser entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
        if (CMD_EDIT.equals(parameter.getCmd())) {
            SysUser sysUser = sysUserService.get(entity.getId());
            entity.setPassword(sysUser.getPassword());
            entity.setLastLoginTime(sysUser.getLastLoginTime());
            sysUserService.merge(entity);
        } else if (CMD_NEW.equals(parameter.getCmd())) {
            // entity.setPassword(MD5.crypt("123456")); // 初始化密码为123456
            entity.setPassword(new Sha256Hash("123456").toHex()); // 初始化密码为123456
            sysUserService.persist(entity);
        }
        parameter.setSuccess(true);
        writeJSON(response, parameter);
    }

    // 操作用户的删除、导出Excel、字段判断和保存
    @RequestMapping(value = "/operateSysUser", method = {RequestMethod.POST, RequestMethod.GET})
    public void operateSysUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String oper = request.getParameter("oper");
        String id = request.getParameter("id");
        if (oper.equals("del")) {
            String[] ids = id.split(",");
            deleteSysUser(request, response, (Long[]) ConvertUtils.convert(ids, Long.class));
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
            String userName = request.getParameter("userName");
            String email = request.getParameter("email");
            SysUser sysUser = null;
            if (oper.equals("edit")) {
                sysUser = sysUserService.get(Long.valueOf(id));
            }
            SysUser emailSysUser = sysUserService.getByProerties("email", email);
            if (StringUtils.isBlank(userName) || StringUtils.isBlank(email)) {
                response.setStatus(HttpServletResponse.SC_LENGTH_REQUIRED);
                result.put("message", "请填写姓名和邮箱");
                writeJSON(response, result);
            } else if (null != emailSysUser && oper.equals("add")) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                result.put("message", "此邮箱已存在，请重新输入");
                writeJSON(response, result);
            } else if (null != emailSysUser && !sysUser.getEmail().equalsIgnoreCase(email) && oper.equals("edit")) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                result.put("message", "此邮箱已存在，请重新输入");
                writeJSON(response, result);
            } else {
                SysUser entity = new SysUser();
                entity.setUserName(userName);
                entity.setSex(Short.valueOf(request.getParameter("sexCn")));
                entity.setEmail(email);
                entity.setPhone(request.getParameter("phone"));
                if (StringUtils.isNotBlank(request.getParameter("birthday"))) {
                    entity.setBirthday(dateFormat.parse(request.getParameter("birthday")));
                }
                entity.setDepartmentKey(request.getParameter("departmentValue"));
                entity.setStatusCn(request.getParameter("statusCn"));
                if (entity.getStatusCn().equals("是")) {
                    entity.setStatus(true);
                } else {
                    entity.setStatus(false);
                }

                Set<Role> roles = new HashSet<Role>();
                Role role = roleService.getByProerties("roleKey", request.getParameter("roleCn"));
                roles.add(role);
                entity.setRoles(roles);
                Integer allianceId = null == request.getParameter("allianceValue") ? getCurrentAllianceId() : Integer.parseInt(request.getParameter("allianceValue"));
                entity.setAllianceId(allianceId);
                if (oper.equals("edit")) {
                    entity.setId(Long.valueOf(id));
                    entity.setCmd("edit");
                    doSave(entity, request, response);
                } else if (oper.equals("add")) {
                    entity.setCmd("new");
                    doSave(entity, request, response);
                }
            }
        }
    }

    // 微信绑定保存
    @RequestMapping(value = "/weixinSysUser", method = {RequestMethod.POST, RequestMethod.GET})
    public void weixinSysUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        SysUser emailSysUser = sysUserService.getByProerties("email", email);
        if (StringUtils.isBlank(userName) || StringUtils.isBlank(email)) {
            response.setStatus(HttpServletResponse.SC_LENGTH_REQUIRED);
            result.put("message", "请填写姓名和邮箱");
            writeJSON(response, result);
        } else if (null != emailSysUser) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            result.put("message", "此邮箱已存在，请重新输入");
            writeJSON(response, result);
        } else {
            SysUser entity = new SysUser();
            entity.setUserName(userName);
            entity.setSex(Short.valueOf(request.getParameter("sex")));
            entity.setEmail(email);
            entity.setPhone(request.getParameter("phone"));
            if (StringUtils.isNotBlank(request.getParameter("birthday"))) {
                entity.setBirthday(dateFormat.parse(request.getParameter("birthday")));
            }
            // 店员所属部门代码MD
            entity.setDepartmentKey("MD");
            entity.setStatus(false);
            entity.setOpenid(request.getParameter("openid"));
            entity.setStoreid(request.getParameter("storeid"));
            entity.setPassword(request.getParameter("pwd"));

            entity.setPlaink(request.getParameter("pwd"));
            Set<Role> roles = new HashSet<Role>();
            // 店员角色代码14
            Role role = roleService.getByProerties("roleKey", "14");
            roles.add(role);
            entity.setRoles(roles);


            ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
            // entity.setPassword(MD5.crypt("123456")); // 初始化密码为123456
            entity.setPassword(new Sha256Hash(entity.getPassword()).toHex()); // 初始化密码为123456
            sysUserService.persist(entity);
            Subject subject = SecurityUtils.getSubject();
            subject.login(new UsernamePasswordToken(entity.getEmail(), request.getParameter("pwd"),
                    entity.isRememberMe()));
            Session session = subject.getSession();
            session.setAttribute(SESSION_SYS_USER, entity);
            parameter.setSuccess(true);
            writeJSON(response, parameter);
        }
    }

    // 保存个人资料
    @RequestMapping(value = "/saveSysUserProfile", method = {RequestMethod.POST, RequestMethod.GET})
    public void saveSysUserProfile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long sysUserId = ((SysUser) request.getSession().getAttribute(SESSION_SYS_USER)).getId();
        SysUser entity = sysUserService.get(sysUserId);
        //SysUser entity = new SysUser();
//		entity.setId(sysUserId);
        entity.setUserName(request.getParameter("userName"));
        entity.setSex(Short.valueOf(request.getParameter("sex")));
        entity.setEmail(request.getParameter("email"));
        entity.setPhone(request.getParameter("phone"));
        if (null != request.getParameter("birthday")) {
            entity.setBirthday(dateFormat.parse(request.getParameter("birthday")));
        }

//		entity.setStatus(sysUser.getStatus());
//		entity.setPassword(sysUser.getPassword());
//		entity.setLastLoginTime(sysUser.getLastLoginTime());
//		entity.setDepartmentKey(sysUser.getDepartmentKey());
//		entity.setRoles(sysUser.getRoles());
        sysUserService.merge(entity);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        writeJSON(response, result);
    }

    // 删除用户
    @RequestMapping("/deleteSysUser")
    public void deleteSysUser(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Long[] ids)
            throws IOException {
        if (Arrays.asList(ids).contains(Long.valueOf("1"))) {
            writeJSON(response, "{success:false,message:'删除项包含超级管理员，超级管理员不能删除！'}");
        } else {
            boolean flag = sysUserService.deleteByPK(ids);
            if (flag) {
                writeJSON(response, "{success:true}");
            } else {
                writeJSON(response, "{success:false}");
            }
        }
    }

    // 即时更新个人资料的字段
    @RequestMapping(value = "/updateSysUserField", method = {RequestMethod.POST, RequestMethod.GET})
    public void updateSysUserField(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = Long.valueOf(request.getParameter("pk"));
        String name = request.getParameter("name");
        String value = request.getParameter("value");
        if (name.equals("userName")) {
            sysUserService.updateByProperties("id", id, "userName", value);
        } else if (name.equals("sex")) {
            sysUserService.updateByProperties("id", id, "sex", Short.valueOf(value));
        } else if (name.equals("email")) {
            sysUserService.updateByProperties("id", id, "email", value);
        } else if (name.equals("phone")) {
            sysUserService.updateByProperties("id", id, "phone", value);
        } else if (name.equals("birthday")) {
            if (null != value) {
                sysUserService.updateByProperties("id", id, "birthday", dateFormat.parse(value));
            }
        }
    }

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    // 上传个人资料的头像
    @RequestMapping(value = "/uploadAttachement", method = RequestMethod.POST)
    public void uploadAttachement(@RequestParam(value = "avatar", required = false) MultipartFile file,
                                  HttpServletRequest request, HttpServletResponse response) throws Exception {
        RequestContext requestContext = new RequestContext(request);
        JSONObject json = new JSONObject();
        if (!file.isEmpty()) {
            if (file.getSize() > 2097152) {
                json.put("message", requestContext.getMessage("g_fileTooLarge"));
            } else {
                try {
                    String originalFilename = file.getOriginalFilename();
                    String fileName = sdf.format(new Date()) + JavaEEFrameworkUtils.getRandomString(3)
                            + originalFilename.substring(originalFilename.lastIndexOf("."));
                    File filePath = new File(
                            getClass().getClassLoader().getResource("/").getPath().replace("/WEB-INF/classes/",
                                    "/static/upload/img/" + DateFormatUtils.format(new Date(), "yyyyMMdd")));
                    if (!filePath.exists()) {
                        filePath.mkdirs();
                    }
                    file.transferTo(new File(filePath.getAbsolutePath() + "\\" + fileName));
                    String destinationFilePath = "/static/upload/img/" + DateFormatUtils.format(new Date(), "yyyyMMdd")
                            + "/" + fileName;
                    Long sysUserId = ((SysUser) request.getSession().getAttribute(SESSION_SYS_USER)).getId();
                    attachmentService.deleteByProperties(new String[]{"type", "typeId"},
                            new Object[]{(short) 1, sysUserId});
                    Attachment attachment = new Attachment();
                    attachment.setFileName(originalFilename);
                    attachment.setFilePath(destinationFilePath);
                    attachment.setType((short) 1);
                    attachment.setTypeId(sysUserId);
                    attachmentService.persist(attachment);
                    json.put("status", "OK");
                    json.put("url", request.getContextPath() + destinationFilePath);
                    json.put("message", requestContext.getMessage("g_uploadSuccess"));
                } catch (Exception e) {
                    e.printStackTrace();
                    json.put("message", requestContext.getMessage("g_uploadFailure"));
                }
            }
        } else {
            json.put("message", requestContext.getMessage("g_uploadNotExists"));
        }
        writeJSON(response, json.toString());
    }

    /**
     * 以下方法是根据路径跳转到页面
     **/

    @RequestMapping("/sysuser")
    public String sysuser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/systemmanage/sysuser";
    }

    @RequestMapping("/weixinbind")
    public String weixinbind(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/weixin_login_bind";
    }

    @RequestMapping("/weixinmenu")
    public String weixinmenu(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/weixin_menu";
    }

    @RequestMapping("/weixinverification")
    public String weixinverification(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/weixin_verification";
    }

    @RequestMapping("/homepage")
    public String homepage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/homepage";
    }

    @RequestMapping("/dict")
    public String dict(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/systemmanage/dict";
    }

    @RequestMapping("/wxmenu")
    public String wxmenu(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/systemmanage/wxmenu";
    }

    @RequestMapping("/role")
    public String role(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/systemmanage/role";
    }

    @RequestMapping("/department")
    public String department(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/systemmanage/department";
    }

    @RequestMapping("/mail")
    public String mail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/systemmanage/mail";
    }

    @RequestMapping("/authority")
    public String authority(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/systemmanage/authority";
    }

    @RequestMapping("/typography")
    public String typography(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/bootstrapexample/typography";
    }

    @RequestMapping("/uielements")
    public String uielements(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/bootstrapexample/uielements";
    }

    @RequestMapping("/buttonsicon")
    public String buttonsicon(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/bootstrapexample/buttonsicon";
    }

    @RequestMapping("/contentslider")
    public String contentslider(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/bootstrapexample/contentslider";
    }

    @RequestMapping("/nestablelist")
    public String nestablelist(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/bootstrapexample/nestablelist";
    }

    @RequestMapping("/jquerydatatables")
    public String jquerydatatables(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/bootstrapexample/jquerydatatables";
    }

    @RequestMapping("/formelements")
    public String formelements(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/bootstrapexample/formelements";
    }

    @RequestMapping("/formelements2")
    public String formelements2(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/bootstrapexample/formelements2";
    }

    @RequestMapping("/wizardvalidation")
    public String wizardvalidation(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/bootstrapexample/wizardvalidation";
    }

    @RequestMapping("/uiwidgets")
    public String uiwidgets(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/bootstrapexample/uiwidgets";
    }

    @RequestMapping("/calendar")
    public String calendar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/bootstrapexample/calendar";
    }

    @RequestMapping("/gallery")
    public String gallery(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/bootstrapexample/gallery";
    }

    @RequestMapping("/pricingtables")
    public String pricingtables(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/bootstrapexample/pricingtables";
    }

    @RequestMapping("/invoice")
    public String invoice(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/bootstrapexample/invoice";
    }

    @RequestMapping("/timeline")
    public String timeline(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/bootstrapexample/timeline";
    }

    @RequestMapping("/faq")
    public String faq(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/bootstrapexample/faq";
    }

    @RequestMapping("/error404")
    public String error404(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/bootstrapexample/error404";
    }

    @RequestMapping("/error500")
    public String error500(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/bootstrapexample/error500";
    }

    @RequestMapping("/grid")
    public String grid(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/bootstrapexample/grid";
    }

    @RequestMapping("/charts")
    public String charts(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/chartandreport/charts";
    }

    // ========================================测试=================================
    @RequestMapping("/test")
    public String test(HttpServletRequest request, HttpServletResponse response) throws IOException {

        return "back/test/test";
    }

    @RequestMapping("/uploadvido")
    public String uploadvido(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/test/uploadvido";
    }

    // ======================================用户管理===============================
    @RequestMapping("user")
    public String user(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/usermanage/user";
    }

    @RequestMapping("vip")
    public String vip(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/usermanage/vip";
    }

    @RequestMapping("userBuyCourse")
    public String userBuyCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/usermanage/userbuycourse";
    }

    // =====================================活动管理================================
    @RequestMapping("indexcarousel")
    public String indexcarousel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/carousel/indexcarousel";
    }

    @RequestMapping("shopcarousel")
    public String shopcarousel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/carousel/shopcarousel";
    }

    @RequestMapping("floatingframe")
    public String floatingframe(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/carousel/floatingframe";
    }

    // ==================================问答管理===============================================
    @RequestMapping("question")
    public String question(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/question/question";
    }

    @RequestMapping("answer")
    public String answer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt((String) request.getParameter("flag"));

        request.setAttribute("question", tzzQuestionService.get(id));
        request.setAttribute("username", tzzUserService.get(tzzQuestionService.get(id).getUserId()).getUsername());
        return "back/question/answer";
    }

    // =================================体系库管理=================================================
    @RequestMapping("tixiku")
    public String tixiku(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/tixikumanage/tixiku";
    }

    @RequestMapping("tixikuxq")
    public String tixikuxq(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/tixikumanage/tixikuxq";
    }

    @RequestMapping("tixikucp")
    public String tixikucp(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int txkid = Integer.parseInt(request.getParameter("txkid"));
        List<TzzDictionary> fllist = new ArrayList<TzzDictionary>();
        fllist = tzzDictionaryService.queryByProerties("type", "1");
        request.setAttribute("fllist", fllist);
        request.setAttribute("txkid", txkid);
        return "back/tixikumanage/tixikucp";
    }

    @RequestMapping("tixikucourse")
    public String tixikucourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int txkdid = Integer.parseInt(request.getParameter("txkdid"));
        List<TzzDictionary> fllist = new ArrayList<TzzDictionary>();
        fllist = tzzDictionaryService.queryByProerties("type", "1");
        request.setAttribute("fllist", fllist);
        request.setAttribute("txkdid", txkdid);
        return "back/tixikumanage/tixikuxq";
    }

    // ==================================成功案例==================================================
    @RequestMapping("/information")
    public String information(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/infomanage/information";
    }


    // ==================================邮箱短息设置==============================================
    @RequestMapping("emailphone")
    public String emailphone(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/emailphonemanage/emailphone";
    }

    // =================================站内消息============================================
    // 短信
    @RequestMapping("sms")
    public String sms(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/news/sms";
    }

    // 站内消息
    @RequestMapping("/news")
    public String news(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int pagenum = 1;
        int maxtiaoshu = 10;
        if (request.getAttribute("pagenum") != null)
            pagenum = Integer.valueOf(request.getParameter("pagenum"));
        ;

        List<TzzNews> list = tzzNewsService.getnewspage(pagenum, maxtiaoshu);
        List<TzzNews> alllist = tzzNewsService.doQueryAll();
        int newsnum = alllist.size();// 总条数
        int totalpage = 0;// 总页数
        if (newsnum % maxtiaoshu == 0)
            totalpage = newsnum / maxtiaoshu;
        else
            totalpage = newsnum / maxtiaoshu + 1;
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setContent(HtmlUtils.htmltoText(list.get(i).getContent()));
            if (tzzUserService.get(list.get(i).getUserId()) != null) {
                list.get(i).setUserame(tzzUserService.get(list.get(i).getUserId()).getUsername());
            }

        }

        // 用户名列表
        List<TzzUser> tzzuserList = tzzUserService.doQueryAll();
        List<String> names = new ArrayList<String>();
        for (int i = 0; i < tzzuserList.size(); i++) {
            if (tzzuserList.get(i).getUsername() != null && !tzzuserList.get(i).getUsername().equals("")) {
                names.add(tzzuserList.get(i).getUsername());

            }
        }

        request.setAttribute("names", names);
        request.setAttribute("totalpage", totalpage);// 总页数
        request.setAttribute("totalpage2", totalpage);// 总页数
        request.setAttribute("newsnum", newsnum);// 总条数
        request.setAttribute("nlist", list);
        return "back/news/stationmsg";
    }

    // 邮件
    @RequestMapping("/email")
    public String email(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int pagenum = 1;
        int maxtiaoshu = 10;
        if (request.getAttribute("pagenum") != null)
            pagenum = Integer.valueOf(request.getParameter("pagenum"));
        ;

        List<Mail> list = mailService.getmailpage(pagenum, maxtiaoshu);

        List<Mail> alllist = mailService.queryByProerties("userId",
                ((SysUser) request.getSession().getAttribute(SESSION_SYS_USER)).getId());
        int mailnum = alllist.size();// 总条数
        int totalpage = 0;// 总页数
        if (mailnum % maxtiaoshu == 0)
            totalpage = mailnum / maxtiaoshu;
        else
            totalpage = mailnum / maxtiaoshu + 1;
        list = mailService.queryEmailHTMLToList(list);

        // 用户邮箱列表
        List<TzzUser> tzzuserList = tzzUserService.doQueryAll();

        request.setAttribute("emails", tzzuserList);
        request.setAttribute("pagenum", pagenum);
        request.setAttribute("totalpage", totalpage);// 总页数
        request.setAttribute("totalpage2", totalpage);// 总页数
        request.setAttribute("newsnum", mailnum);// 总条数
        request.setAttribute("nlist", list);
        return "back/news/email";
    }

    // ========================分类===============================================================

    @RequestMapping("/goodsCategory")
    public String goodsCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/category/category";
    }

    @RequestMapping("/dif")
    public String dif(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/category/dif";
    }

    @RequestMapping("/userstyle")
    public String userstyle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/category/userstyle";
    }

    // ================================================================

    @RequestMapping("/showProduct")
    public String showProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/product/showProduct";
    }

    // ============================课程================================

    @RequestMapping({"/newvideo"})
    public String newvideo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/course/newvideo";
    }

    @RequestMapping({"/coursezhekou"})
    public String coursezhekou(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int cid = Integer.parseInt(request.getParameter("cid"));
        request.setAttribute("cid", cid);
        return "back/course/coursezhekou";
    }

    @RequestMapping("/showCourse")
    public String showCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/course/showCourse";
    }

    @RequestMapping("/showCourseWare")
    public String showCourseWare(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int cid = Integer.parseInt(request.getParameter("cid"));
        request.setAttribute("cid", cid);
        return "back/course/showCourseWare";
    }

    // =================================简介==================================================

    @RequestMapping("/tzzjianjie")
    public String tzzjianjie(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/jianjiemanage/jianjie";
    }

    @RequestMapping("/vipquanyi")
    public String vipquanyi(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/jianjiemanage/vipquanyi";
    }

    @RequestMapping("/zhihuiyingdi")
    public String zhihuiyingdi(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/jianjiemanage/zhihuiyingdi";
    }

    @RequestMapping("/callus")
    public String callus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/jianjiemanage/callus";
    }

    @RequestMapping("/userxieyi")
    public String userxieyi(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/jianjiemanage/userxieyi";
    }

    @RequestMapping("/pdfvip")
    public String pdfvip(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/jianjiemanage/pdfvip";
    }

    // =================================首页管理=====================================================
    @RequestMapping("/indexfl")
    public String indexfl(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/index/indexfl";
    }

    @RequestMapping("/pdfvipdic")
    public String pdfvipdic(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/index/pdfvipdic";
    }

    // =================================页面尾部条款=====================================================
    @RequestMapping("/message")
    public String message(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/message/message";
    }

    //指定用户系统消息推送
    @RequestMapping("/sendmsg")
    public String sendmsg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/message/sendmsg";
    }

    @RequestMapping("/databasebackup")
    public String databasebackup(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/usermanage/databasebackup";
    }

    // 提醒
    @RequestMapping("/tixing")
    public String tixing(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/tixing";
    }

    @RequestMapping("/safetynews")
    public String safetynews(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/safetynews/safetynews";
    }

    @RequestMapping("/activityarea")
    public String activityarea(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/carousel/activityarea";
    }

    //个角色首页功能菜单配置管理
    @RequestMapping("/careertype")
    public String careertype(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/systemmanage/careertype";
    }

    //主题招聘列表功能菜单配置管理
    @RequestMapping("/themerecruitment")
    public String themeRecruitment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/recruitment/themerecruitment";
    }

    //主题招聘类型管理
    @RequestMapping("/themerecruitmentType")
    public String themerecruitmentType(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/recruitment/recruitmenttheme";
    }

    //主题招聘编辑功能菜单配置管理
    @RequestMapping("/themerecruitmentedit")
    public String themeRecruitmentEdit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int spid = NumberHelper.string2Int(request.getParameter("spid"));
        request.setAttribute("spid", spid);
        return "back/recruitment/themerecruitmentedit";
    }

    //主题招聘列表功能菜单配置管理
    @RequestMapping("/recruitmenttheme")
    public String recruitmentTheme(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/recruitment/recruitmenttheme";
    }

    //主题招聘岗位配置管理
    @RequestMapping("/recruitcareer")
    public String recruitCareer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int rid = NumberHelper.string2Int(request.getParameter("rid"));
        request.setAttribute("rid", rid);
        return "back/recruitment/recruitcareer";
    }

    //	协会管理
    @RequestMapping("/association")
    public String association(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/association/association";
    }

    //	合作院校管理
    @RequestMapping("/cooperationcolleges")
    public String cooperationcolleges(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/cooperationcolleges/cooperationcolleges";
    }

    //	企业用户审核管理
    @RequestMapping("/companyshenhe")
    public String companyshenhe(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/company/companyshenhe";
    }

    //	注册用户信息管理
    @RequestMapping("/usermanagement")
    public String usermanagement(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/usermanage/user";
    }

    //	系统用户信息管理
    @RequestMapping("/sysusersub")
    public String sysusersub(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/systemmanage/sysusersub";
    }


    //	职位管理
    @RequestMapping("/careermanagement")
    public String careermanagement(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/systemmanage/careermanagement";
    }

    //	城市管理
    @RequestMapping("/city")
    public String city(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/systemmanage/city";
    }

    //	商品类别管理
    @RequestMapping("/productType")
    public String productType(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/productinfomanage/productType";
    }

    //	商品属性模板管理
    @RequestMapping("/productPropertyTemp")
    public String productPropertyTemp(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/productinfomanage/productPropertyTemp";
    }

    //	商品信息管理
    @RequestMapping("/productmanagement")
    public String productmanagement(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/productinfomanage/showProduct";
    }

    /**
     * 加盟商
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/alliancebusiness")
    public String alliancebusiness(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/alliancebusiness/alliancebusiness";
    }

    /**
     * 入库单
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/intoWarehouseRecord")
    public String intoWarehouseRecord(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/warehouse/intowarehouserecord";
    }

    /**
     * 入库单详情
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/intoWarehouseRecordDatail")
    public String intoWarehouseRecordDatail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String intoWarehouseRecordNo = request.getParameter("intoWarehouseRecordNo");
        request.setAttribute("intoWarehouseRecordNo", intoWarehouseRecordNo);
        return "back/warehouse/intowarehouserecord_detail";
    }

    /**
     * 模块管理
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/modulemanager")
    public String moduleManager(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "back/clienthomepage/modulemanager";
    }

    /**
     * 微信素材图片管理
     *
     * @return
     */
    @RequestMapping(value = "/materialImageManager")
    public String materialImageManager() {
        return "back/wechatmanager/materialImageManager";
    }

    /**
     * 根据No查询
     *
     * @param request
     * @param response
     * @param no
     * @return
     */
    @RequestMapping(value = "/editIntoWarehouse")
    public String editIntoWarehouse(HttpServletRequest request, HttpServletResponse response, String no) {
        IntoWarehouseRecord intoWarehouseRecord = intoWarehouseRecordService.getByProerties("no", no);
        List<IntoWarehouseRecordDatail> datails = intoWarehouseRecordDetailService.queryByProerties("intoWarehouseRecordNo", no);
        request.setAttribute("intoWarehouseRecord", intoWarehouseRecord);
        request.setAttribute("datails", datails);
        return "back/warehouse/intowarehouserecord_edit";
    }

    /**
     * 添加页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/addIntoWarehouseUI")
    public String addIntoWarehouseUI(HttpServletRequest request, HttpServletResponse response) {
        SysUser sysUser = getCurrentSysUser();
        Integer allianceId = sysUser.getAllianceId();
        //编码
        String no = DateHelper.getCurDateTime().replace("-", "").replace(":", "").replace(" ", "") + (int) ((Math.random() * 9 + 1) * 10000);
        //标题
        AllianceBusiness business = allianceBusinessService.get(allianceId);
        String title = business.getName() + "入库单";
        request.setAttribute("sysUser", sysUser);
        //request.setAttribute("allianceId", allianceId);
        request.setAttribute("no", no);
        request.setAttribute("title", title);
        return "back/warehouse/intowarehouserecord_add";
    }
}
