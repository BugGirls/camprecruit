package com.jeefw.model.sys.param;

import core.support.ExtJSBaseParameter;

/**
 * @author Empress
 * @data 2018/7/8
 */
public class WechatMaterialImageParameter extends ExtJSBaseParameter {

    private String $like_title;
    private Integer $eq_allianceId;
    private Integer $eq_useScene;

    public Integer get$eq_useScene() {
        return $eq_useScene;
    }

    public void set$eq_useScene(Integer $eq_useScene) {
        this.$eq_useScene = $eq_useScene;
    }

    public String get$like_title() {
        return $like_title;
    }

    public void set$like_title(String $like_title) {
        this.$like_title = $like_title;
    }

    public Integer get$eq_allianceId() {
        return $eq_allianceId;
    }

    public void set$eq_allianceId(Integer $eq_allianceId) {
        this.$eq_allianceId = $eq_allianceId;
    }

}
