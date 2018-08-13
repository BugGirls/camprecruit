package com.jeefw.model.sys.param;

import core.support.ExtJSBaseParameter;

/**
 * @author Hystar
 * @date 2018/8/13
 */
public class WechatMemberStoresParameter extends ExtJSBaseParameter {

    private String $eq_openid;

    public String get$eq_openid() {
        return $eq_openid;
    }

    public void set$eq_openid(String $eq_openid) {
        this.$eq_openid = $eq_openid;
    }
}
