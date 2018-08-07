package com.jeefw.model.sys.vo;

import com.jeefw.model.sys.WechatCard;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * 分页结果
 *
 * @author Empress
 * @data 2018/7/15
 */
public class ResultPageVo {

    /**
     * 当前页
     */
    private Integer pageNum;

    /**
     * 每页显示多少条数据
     */
    private Integer pageSize;

    /**
     * 总页数
     */
    private Long totalPage;

    /**
     * 数据
     */
    private List data;

    /**
     * 总记录数
     */
    private Long totalCount;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }
}
