package com.jeefw.model.sys.param;

import core.support.ExtJSBaseParameter;

/**
 * @author Hystar
 * @date 2018/7/23
 */
public class WechatUserGetCardParameter extends ExtJSBaseParameter {

    private String $eq_openid;
    private String $eq_cardId;

    public String get$eq_openid() {
        return $eq_openid;
    }

    public void set$eq_openid(String $eq_openid) {
        this.$eq_openid = $eq_openid;
    }

    public String get$eq_cardId() {
        return $eq_cardId;
    }

    public void set$eq_cardId(String $eq_cardId) {
        this.$eq_cardId = $eq_cardId;
    }
}
