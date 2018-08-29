package com.jeefw.controller.sys;

import core.util.PropertiesUtil;
import core.util.WechatUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 微信网页授权
 *
 * @author Hystar
 * @date 2018/8/16
 */
@Controller
@RequestMapping(value = "/wechat/")
public class WechatController {

    private static final String URL = PropertiesUtil.getProperty("localUrl") + "/wechat/user_info";

    /**
     * 微信网页授权
     * http://hndt.natapp1.cc/wechat/auth?returnUrl=/member/activity/judge_user_member_state
     *
     * @param returnUrl
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "auth", method = RequestMethod.GET)
    public String auth(@RequestParam(value = "returnUrl") String returnUrl) throws UnsupportedEncodingException {
        String redirectUrl = WechatUtil.getCodeUrl(URL, URLEncoder.encode(returnUrl, "UTF-8"));
        return "redirect:" + redirectUrl;
    }

    /**
     * 获取openid
     *
     * @param code
     * @param returnUrl
     * @return
     */
    @RequestMapping(value = "user_info", method = RequestMethod.GET)
    public String getUserInfo(@RequestParam(value = "code") String code,
                              @RequestParam(value = "state") String returnUrl) {
        JSONObject jsonObject = WechatUtil.getAccessTokenForAuth(code);
        String openid = jsonObject.getString("openid");
        if (returnUrl.indexOf("?") >= 0) {
            returnUrl = returnUrl + "&openid=" + openid;
        } else {
            returnUrl = returnUrl + "?openid=" + openid;
        }
        return "redirect:" + PropertiesUtil.getProperty("localUrl") + returnUrl;
    }
}
