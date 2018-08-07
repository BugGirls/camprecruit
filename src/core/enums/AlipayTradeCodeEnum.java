package core.enums;

/**
 * @author Hystar
 * @date 2018/7/20
 */
public enum AlipayTradeCodeEnum {

    SUCCESS("10000", "支付成功"),
    FAILED("40004", "支付失败"),
    WAIT_BUYER_PAY("10003", "等待用户付款"),
    UNKNOWN("20000", "未知异常"),;

    private String code;
    private String value;

    AlipayTradeCodeEnum(String code, String value) {
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
