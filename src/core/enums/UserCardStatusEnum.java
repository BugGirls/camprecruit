package core.enums;

/**
 * 用户领取的卡券状态
 *
 * @author Hystar
 * @date 2018/7/13
 */
public enum UserCardStatusEnum {
    NORMAL("NORMAL", "正常"),
    CONSUMED("CONSUMED", "已核销"),
    EXPIRE("EXPIRE", "已过期"),
    GIFTING("GIFTING", "转赠中"),
    GIFT_TIMEOUT("GIFT_TIMEOUT", "转赠超时"),
    DELETE("DELETE", "已删除"),
    UNAVAILABLE("UNAVAILABLE", "已失效"),
    ;

    private String key;
    private String value;

    UserCardStatusEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static UserCardStatusEnum keyOf(String key) {
        for (UserCardStatusEnum userCardStatusEnum : values()) {
            if (userCardStatusEnum.getKey().equals(key)) {
                return userCardStatusEnum;
            }
        }

        return null;
    }
}
