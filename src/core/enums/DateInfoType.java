package core.enums;

/**
 * 有效期使用的时间类型
 *
 * @author Hystar
 * @date 2018/7/10
 */
public enum DateInfoType {

    DATE_TYPE_FIX_TIME_RANGE("DATE_TYPE_FIX_TIME_RANGE", "固定日期区间"),
    DATE_TYPE_FIX_TERM("DATE_TYPE_FIX_TERM", "固定时长"),
    DATE_TYPE_PERMANENT("DATE_TYPE_PERMANENT", "永久有效类型");

    private String key;
    private String value;

    DateInfoType(String key, String value) {
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
