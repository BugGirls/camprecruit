package core.enums;

/**
 * 卡券类型
 *
 * @author Hystar
 * @date 2018/7/11
 */
public enum CardTypeEnum implements CodeEnum {

    GENERAL_COUPON("GENERAL_COUPON", "优惠券"),
    GIFT("GIFT", "兑换券"),
    DISCOUNT("DISCOUNT", "折扣券"),
    CASH("CASH", "代金券"),
    GROUPON("GROUPON", "团购券"),
    MEMBER_CARD("MEMBER_CARD", "会员卡"),
    ;

    private String key;
    private String value;

    CardTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
