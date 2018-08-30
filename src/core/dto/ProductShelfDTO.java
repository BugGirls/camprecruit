package core.dto;

import java.math.BigDecimal;

/**
 * 上架商品信息
 *
 * @author Hystar
 * @date 2018/8/29
 */
public class ProductShelfDTO {

    /**
     * 商品ID
     */
    private String productNo;

    /**
     * 商品图片
     */
    private String productImage;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品副标题
     */
    private String productSubTitle;

    /**
     * 商品价格
     */
    private BigDecimal productPrice;

    /**
     * 商品销量
     */
    private Integer productSalesVolume;

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSubTitle() {
        return productSubTitle;
    }

    public void setProductSubTitle(String productSubTitle) {
        this.productSubTitle = productSubTitle;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductSalesVolume() {
        return productSalesVolume;
    }

    public void setProductSalesVolume(Integer productSalesVolume) {
        this.productSalesVolume = productSalesVolume;
    }
}
