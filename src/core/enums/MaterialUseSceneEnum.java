package core.enums;

/**
 * 素材的使用场景
 * 区别的是上传的素材是在什么地方使用
 *
 * @author Hystar
 * @date 2018/7/25
 */
public enum MaterialUseSceneEnum {

    /**
     * 通用
     */
    COMMON_USE(0, "common"),

    /**
     * 门店专用
     */
    POI_DEDICATE(1, "poi"),

    /**
     * 卡券专用
     */
    CARD_DEDICATE(2, "card"),
    ;

    private Integer code;
    private String value;

    MaterialUseSceneEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public static MaterialUseSceneEnum codeOf(Integer code) {
        for (MaterialUseSceneEnum materialUseSceneEnum : values()) {
            if (materialUseSceneEnum.getCode().equals(code)) {
                return materialUseSceneEnum;
            }
        }

        return null;
    }

    public String getValue() {
        return value;
    }

    public Integer getCode() {
        return code;
    }
}
