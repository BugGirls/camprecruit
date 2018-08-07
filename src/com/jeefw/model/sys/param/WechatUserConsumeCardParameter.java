package com.jeefw.model.sys.param;

import core.support.ExtJSBaseParameter;

/**
 * @author Hystar
 * @date 2018/7/27
 */
public class WechatUserConsumeCardParameter extends ExtJSBaseParameter {

    private String $eq_userCardCode;

    public String get$eq_userCardCode() {
        return $eq_userCardCode;
    }

    public void set$eq_userCardCode(String $eq_userCardCode) {
        this.$eq_userCardCode = $eq_userCardCode;
    }
}
