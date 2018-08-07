package core.enums;

/**
 * 订单状态
 *
 * @author Hystar
 * @date 2018/1/4
 */
public enum OrderStatusEnum {
    NEW(0, "新订单"),
    CANCEL(1, "已取消"),
    FINISH(2, "已完成"),
    REFUND(3, "已退款")
    ;

    private Integer code;
    private String value;

    OrderStatusEnum(Integer code, String value) {
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
