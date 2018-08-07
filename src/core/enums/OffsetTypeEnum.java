package core.enums;

/**
 * @author Hystar
 * @date 2018/7/24
 */
public enum OffsetTypeEnum {

    MARS(1, "火星坐标"),
    SOGOU(2, "sogou经纬度"),
    BAIDU(3, "百度经纬度"),
    MAPBAR(4, "mapbar经纬度"),
    GPS(5, "GPS坐标"),
    SOGOU_MOKA(6, "sogou墨卡托坐标"),;

    private Integer code;
    private String value;

    OffsetTypeEnum(Integer code, String value) {
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
