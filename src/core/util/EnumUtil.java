package core.util;


import core.enums.CodeEnum;

/**
 * 通用枚举工具类
 *
 * @author Hystar
 * @date 2018/1/3
 */
public class EnumUtil {

    /**
     * 通过传入的code值，获取该code对应的value
     *
     * @param code
     * @param enumClass
     * @param <T>
     * @return
     */
    public static <T extends CodeEnum> T getByCode(String code, Class<T> enumClass) {
        for (T each : enumClass.getEnumConstants()) {
            if (code.equals(each.getKey())) {
                return each;
            }
        }
        return null;
    }
}
