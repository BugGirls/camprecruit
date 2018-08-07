package com.jeefw.model.sys.bo;

/**
 * @author Hystar
 * @date 2018/7/27
 */
public class Card {

    private String card_id;

    private Integer begin_time;

    private Integer end_time;

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public Integer getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(Integer begin_time) {
        this.begin_time = begin_time;
    }

    public Integer getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Integer end_time) {
        this.end_time = end_time;
    }
}
