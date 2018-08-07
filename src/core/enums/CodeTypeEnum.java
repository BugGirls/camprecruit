package core.enums;

/**
 * 码型
 *
 * @author Hystar
 * @date 2018/7/10
 */
public enum CodeTypeEnum {

    TEXT("CODE_TYPE_TEXT", "文本"),
    BARCODE("CODE_TYPE_BARCODE", "一维码"),
    QRCODE("CODE_TYPE_QRCODE", "二维码"),
    ONLY_QRCODE("CODE_TYPE_ONLY_QRCODE", "二维码无code显示"),
    ONLY_BARCODE("CODE_TYPE_ONLY_BARCODE", "一维码无code显示"),
    NONE("CODE_TYPE_NONE", " 不显示code和条形码类型"),;

    private String key;
    private String value;

    CodeTypeEnum(String key, String value) {
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
