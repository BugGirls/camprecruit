package core.enums;

/**
 * 门店审核状态
 *
 * @author Hystar
 * @date 2018/7/24
 */
public enum AvailableStatusEnum {

    BEING_AVAILABLE(0, "正在审核"),
    AVAILABLE_SUCCESS(1, "审核通过"),
    AVAILABLE_FAIL(2, "审核未通过"),;

    private Integer code;
    private String value;

    AvailableStatusEnum(Integer code, String value) {
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
