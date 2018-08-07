package com.jeefw.model.sys.param;

import core.support.ExtJSBaseParameter;

/**
 * @author Hystar
 * @date 2018/7/17
 */
public class WechatMemberCardParameter extends ExtJSBaseParameter {

    private Integer $eq_allianceId;
    private String $like_title;

    public Integer get$eq_allianceId() {
        return $eq_allianceId;
    }

    public void set$eq_allianceId(Integer $eq_allianceId) {
        this.$eq_allianceId = $eq_allianceId;
    }

    public String get$like_title() {
        return $like_title;
    }

    public void set$like_title(String $like_title) {
        this.$like_title = $like_title;
    }
}
