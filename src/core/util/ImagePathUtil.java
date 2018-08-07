package core.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 路径处理工具类
 *
 * @author Hystar
 * @date 2017/11/13
 */
public class ImagePathUtil {

    /**
     * 获取当前系统文件的分隔符，因为Windows的分隔符为'\'，而linux和mac系统的分隔符为'/'
     */
    private static String separator = System.getProperty("file.separator");
    /**
     * 获取当前电脑的操作系统
     */
    private static String os = System.getProperty("os.name");

    private static final String WIN = "win";

    /**
     * 返回项目图片的根路径
     *
     * @return
     */
    public static String getImgBasePath() {
        // 获取项目根路径
        HttpServletRequest request=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String basePath = request.getSession().getServletContext().getRealPath("");

        // 根据不同的操作系统替换不同的分隔符
        basePath = basePath.replace("/", separator);

        return basePath;
    }

    /**
     * 返回项目图片的子路径
     * 多参数的传入用于路径区分
     *
     * @param strings
     * @return
     */
    public static String getShopImagePath(String...strings) {
        StringBuffer imagePath = new StringBuffer("/upload/");

        if (strings.length > 0) {
            for (int i = 0; i < strings.length; i++) {
                imagePath.append(strings[i] + "/");
            }
        }

        return imagePath.toString().replace("/", separator);
    }
}
