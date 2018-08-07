package core.enums;

/**
 * 交易类型
 *
 * @author Hystar
 * @date 2018/3/14
 */
public enum TradeTypeEnum {

    WECHAT("WECHAT", "微信"),
    ALIPAY("ALIPAY", "支付宝");

    private String code;
    private String value;

    TradeTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
