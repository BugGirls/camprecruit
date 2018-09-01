package core.dto;

import com.jeefw.model.sys.ProductInfo;
import com.jeefw.model.sys.ProductShelf;

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

    private List<ProductShelf> productShelfList;

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

    public List<ProductShelf> getProductShelfList() {
        return productShelfList;
    }

    public void setProductShelfList(List<ProductShelf> productShelfList) {
        this.productShelfList = productShelfList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
