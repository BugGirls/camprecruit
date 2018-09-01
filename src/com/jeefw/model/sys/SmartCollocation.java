package com.jeefw.model.sys;

import com.alipay.api.domain.Product;
import com.jeefw.model.sys.param.SmartCollocationParameter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

/**
 * 智能搭配
 *
 * @author Hystar
 * @date 2018/9/1
 */
@Entity
@Table(name = "smart_collocation")
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd","queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class SmartCollocation extends SmartCollocationParameter {

    @Id
    @Column(name = "id", length = 64, nullable = false)
    private String id;

    @Column(name = "alliance_id")
    private Integer allianceId;

    /**
     * 搭配商品id，用逗号分隔
     */
    @Column(name = "shelf_ids")
    private String shelfIds;

    /**
     * 原价
     */
    @Column(name = "original_price")
    private BigDecimal originalPrice;

    /**
     * 折扣价
     */
    @Column(name = "discount_price")
    private BigDecimal discountPrice;

    public SmartCollocation() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAllianceId() {
        return allianceId;
    }

    public void setAllianceId(Integer allianceId) {
        this.allianceId = allianceId;
    }

    public String getShelfIds() {
        return shelfIds;
    }

    public void setShelfIds(String shelfIds) {
        this.shelfIds = shelfIds;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }
}
