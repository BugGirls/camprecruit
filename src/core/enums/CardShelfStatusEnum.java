package core.enums;

/**
 * 卡券的上架状态
 *
 * @author Hystar
 * @date 2018/7/13
 */
public enum CardShelfStatusEnum {
    PUTAWAY(0, "上架"),
    SOLDOUT(1, "下架"),
    ;

    private Integer key;
    private String value;

    CardShelfStatusEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static CardShelfStatusEnum keyOf(Integer key) {
        for (CardShelfStatusEnum cardShelfStatusEnum : values()) {
            if (cardShelfStatusEnum.getKey().equals(key)) {
                return cardShelfStatusEnum;
            }
        }

        return null;
    }
}
