package core.enums;

/**
 * 商家服务类型
 *
 * @author Hystar
 * @date 2018/7/11
 */
public enum BusinessServiceEnum {

    BIZ_SERVICE_DELIVER("BIZ_SERVICE_DELIVER", "外卖服务"),
    BIZ_SERVICE_FREE_PARK("BIZ_SERVICE_FREE_PARK", "停车位"),
    BIZ_SERVICE_WITH_PET("BIZ_SERVICE_WITH_PET", "可带宠物"),
    BIZ_SERVICE_FREE_WIFI("BIZ_SERVICE_FREE_WIFI", "免费wifi"),;

    private String key;
    private String value;

    BusinessServiceEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }
}
