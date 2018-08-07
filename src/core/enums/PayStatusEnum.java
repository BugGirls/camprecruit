package core.enums;


/**
 * @author Hystar
 * @date 2018/1/4
 */
public enum PayStatusEnum {
    NOT_PAY(0, "未支付"),
    SUCCESS(1, "支付成功"),
    ERROR(-1, "支付失败")
    ;

    private Integer code;
    private String value;

    PayStatusEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
