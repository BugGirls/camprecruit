package core.enums;

/**
 * 卡券背景色
 *
 * @author Hystar
 * @date 2018/7/10
 */
public enum CardColorEnum {

    Color010("Color010","#63b359"),

    Color020("Color020","#2c9f67"),

    Color030("Color030","#509fc9"),

    Color040("Color040","#5885cf"),

    Color050("Color050","#9062c0"),

    Color060("Color060","#d09a45"),

    Color070("Color070","#e4b138"),

    Color080("Color080","#ee903c"),

    Color081("Color081","#f08500"),

    Color082("Color082","#a9d92d"),

    Color090("Color090","#dd6549"),

    Color100("Color100","#cc463d"),

    Color101("Color101","#cf3e36"),

    Color102("Color102","#5E6671"),
            ;

    private String key;
    private String value;

    CardColorEnum(String kye, String value) {
        this.key = kye;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
