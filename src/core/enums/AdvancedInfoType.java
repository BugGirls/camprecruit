package core.enums;

/**
 * 卡券使用时段类型
 *
 * @author Hystar
 * @date 2018/7/10
 */
public enum AdvancedInfoType {

    MONDAY("MONDAY", "周一"),
    TUESDAY("TUESDAY", "周二"),
    WEDNESDAY("WEDNESDAY", "周三"),
    THURSDAY("THURSDAY", "周四"),
    FRIDAY("FRIDAY", "周五"),
    SATURDAY("SATURDAY", "周六"),
    SUNDAY("SUNDAY", "周日"),;

    private String key;
    private String value;

    AdvancedInfoType(String key, String value) {
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
