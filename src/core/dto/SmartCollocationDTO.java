package core.dto;

import com.jeefw.model.sys.ProductInfo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Hystar
 * @date 2018/9/1
 */
public class SmartCollocationDTO {

    private String id;

    private BigDecimal originalPrice;

    private BigDecimal discountPrice;

    private List<ProductInfo> productInfoList;

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

    public List<ProductInfo> getProductInfoList() {
        return productInfoList;
    }

    public void setProductInfoList(List<ProductInfo> productInfoList) {
        this.productInfoList = productInfoList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
