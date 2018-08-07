package com.jeefw.model.sys.param;

import core.support.ExtJSBaseParameter;

/**
 * @author Hystar
 * @date 2018/7/18
 */
public class OrderMasterParameter extends ExtJSBaseParameter {

    private String $eq_orderId;

    public String get$eq_orderId() {
        return $eq_orderId;
    }

    public void set$eq_orderId(String $eq_orderId) {
        this.$eq_orderId = $eq_orderId;
    }
}
