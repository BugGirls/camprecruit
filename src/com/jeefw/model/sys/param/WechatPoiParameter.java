package com.jeefw.model.sys.param;

import core.support.ExtJSBaseParameter;

/**
 * @author Hystar
 * @date 2018/7/24
 */
public class WechatPoiParameter extends ExtJSBaseParameter {

    private Integer $eq_allianceId;
    private String $eq_poiId;
    private String $eq_sid;
    private String $like_branchName;

    public String get$like_branchName() {
        return $like_branchName;
    }

    public void set$like_branchName(String $like_branchName) {
        this.$like_branchName = $like_branchName;
    }

    public Integer get$eq_allianceId() {
        return $eq_allianceId;
    }

    public void set$eq_allianceId(Integer $eq_allianceId) {
        this.$eq_allianceId = $eq_allianceId;
    }

    public String get$eq_sid() {
        return $eq_sid;
    }

    public void set$eq_sid(String $eq_sid) {
        this.$eq_sid = $eq_sid;
    }

    public String get$eq_poiId() {
        return $eq_poiId;
    }

    public void set$eq_poiId(String $eq_poiId) {
        this.$eq_poiId = $eq_poiId;
    }
}
