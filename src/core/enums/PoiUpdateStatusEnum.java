package core.enums;

/**
 * 门店修改状态
 *
 * @author Hystar
 * @date 2018/7/24
 */
public enum PoiUpdateStatusEnum {
    NOT_UPDATE(0, "未修改"),
    UPDATING(1, "正在修改"),;

    private Integer code;
    private String value;

    PoiUpdateStatusEnum(Integer code, String value) {
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
