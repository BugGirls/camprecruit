package com.jeefw.model.sys.param;

import core.support.ExtJSBaseParameter;

/**
 * @author Empress
 * @data 2018/7/6
 */
public class FeModuleParameter extends ExtJSBaseParameter {

    private Integer $eq_allianceId;

    private String $like_name;

    private String $imageUrl;

    private String $accessUrl;

    private Integer $seq;

    public Integer get$eq_allianceId() {
        return $eq_allianceId;
    }

    public void set$eq_allianceId(Integer $eq_allianceId) {
        this.$eq_allianceId = $eq_allianceId;
    }

    @Override
    public String get$like_name() {
        return $like_name;
    }

    @Override
    public void set$like_name(String $like_name) {
        this.$like_name = $like_name;
    }

    public String get$imageUrl() {
        return $imageUrl;
    }

    public void set$imageUrl(String $imageUrl) {
        this.$imageUrl = $imageUrl;
    }

    public String get$accessUrl() {
        return $accessUrl;
    }

    public void set$accessUrl(String $accessUrl) {
        this.$accessUrl = $accessUrl;
    }

    public Integer get$seq() {
        return $seq;
    }

    public void set$seq(Integer $seq) {
        this.$seq = $seq;
    }

}
