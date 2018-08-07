package core.enums;

/**
 * 会员信息类目名称
 *
 * @author Hystar
 * @date 2018/7/17
 */
public enum NameTypeEnum {

    FIELD_NAME_TYPE_LEVEL("FIELD_NAME_TYPE_LEVEL", "等级"),
    FIELD_NAME_TYPE_COUPON("FIELD_NAME_TYPE_COUPON", "优惠券"),
    FIELD_NAME_TYPE_STAMP("FIELD_NAME_TYPE_STAMP", "印花"),
    FIELD_NAME_TYPE_DISCOUNT("FIELD_NAME_TYPE_DISCOUNT", "折扣"),
    FIELD_NAME_TYPE_ACHIEVEMEN("FIELD_NAME_TYPE_ACHIEVEMEN", "成就"),
    FIELD_NAME_TYPE_MILEAGE("FIELD_NAME_TYPE_MILEAGE", "里程"),
    FIELD_NAME_TYPE_SET_POINTS("FIELD_NAME_TYPE_SET_POINTS", "集点"),
    FIELD_NAME_TYPE_TIMS("FIELD_NAME_TYPE_TIMS", "次数"),;

    private String key;
    private String value;

    NameTypeEnum(String key, String value) {
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
