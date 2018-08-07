package core.form;

/**
 * @author Hystar
 * @date 2018/7/18
 */
public class OrderForm {

    /**
     * 买家姓名
     */
    private String name;
    /**
     * 买家手机号
     */
    private String phone;
    /**
     * 买家地址
     */
    private String address;
    /**
     * 微信openid
     */
    private String openid;
    /**
     * 购物车
     */
    private String items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }
}
