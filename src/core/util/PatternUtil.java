package core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

/**
 * 正则表达式工具类
 *
 * @author Hystar
 * @date 2018/8/29
 */
public class PatternUtil {

    /**
     * 判断字符串是不是以数字开头
     *
     * @param str
     * @return
     */
    public static boolean isStartWithNumber(String str) {
        Pattern pattern = compile("[0-9]*");
        Matcher isNum = pattern.matcher(str.charAt(0)+"");
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 获取字符串中的数字
     *
     * @param str
     * @return
     */
    public static String[] getNumberForStr(String str) {
        Pattern pattern = compile("[^0-9]");
        Matcher matcher = pattern.matcher(str);
        String[] dis = matcher.replaceAll(" ").split("\\s+");
        return dis;
    }
}
