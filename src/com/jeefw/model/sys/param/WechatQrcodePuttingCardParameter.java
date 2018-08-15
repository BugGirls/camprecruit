package com.jeefw.model.sys.param;

import core.support.ExtJSBaseParameter;

/**
 * @author Hystar
 * @date 2018/7/13
 */
public class WechatQrcodePuttingCardParameter extends ExtJSBaseParameter {

    private Integer $eq_allianceId;
    private String $eq_wechatCardId;


    public String get$eq_wechatCardId() {
        return $eq_wechatCardId;
    }

    public void set$eq_wechatCardId(String $eq_wechatCardId) {
        this.$eq_wechatCardId = $eq_wechatCardId;
    }

    public Integer get$eq_allianceId() {
        return $eq_allianceId;
    }

    public void set$eq_allianceId(Integer $eq_allianceId) {
        this.$eq_allianceId = $eq_allianceId;
    }
}
