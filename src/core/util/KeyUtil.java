package core.util;

import java.util.Random;
import java.util.UUID;

/**
 *
 * @author Hystar
 * @date 2017/10/18
 */
public class KeyUtil {

    /**
     * 生成唯一编号
     * 格式：时间 + 随机数
     *
     * @return
     */
    public static synchronized String generatorUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }

    /**
     * 生成随机字符串
     *
     * @return
     */
    public static synchronized String generatorNonceStr() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
