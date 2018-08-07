package com.jeefw.model.sys.bo;

import javax.smartcardio.*;

/**
 * 通过code查询卡券接口返回的字段
 *
 * @author Hystar
 * @date 2018/7/27
 */
public class CardMessage {

    private String errcode;

    private String errmsg;

    private String openid;

    private boolean can_consume;

    private String user_card_status;

    private Card card;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public boolean getCan_consume() {
        return can_consume;
    }

    public void setCan_consume(boolean can_consume) {
        this.can_consume = can_consume;
    }

    public String getUser_card_status() {
        return user_card_status;
    }

    public void setUser_card_status(String user_card_status) {
        this.user_card_status = user_card_status;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
