package core.util;

import com.jeefw.model.sys.WechatMaterialImage;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片工具类
 *
 * @author Hystar
 * @date 2017/11/12
 */
public class ImageUtil {

    private static final Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    /**
     * 根据图片地址转换为base64编码字符串
     *
     * @param imgFile 图片路径-具体到文件
     */
    public static String getImageStr(String imgFile) {
        InputStream inputStream;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    /**
     * 将base64编码字符串转换为图片
     *
     * @param base64Data    base64编码字符串
     * @param targetAddress 处理后图片保存的相对路径
     * @return
     */
    public static String generateImage(WechatMaterialImage wechatMaterialImage, String base64Data, String targetAddress) throws Exception {
        // 1、验证数据的合法性
        String dataPrefix;
        String data;
        if (StringUtils.isBlank(base64Data)) {
            throw new Exception("上传失败，上传图片数据为空");
        } else {
            String[] d = base64Data.split("base64,");
            if (d != null && d.length == 2) {
                dataPrefix = d[0];
                data = d[1];
            } else {
                throw new Exception("上传失败，数据不合法");
            }
        }

        // 2、设置输出文件路径和名称
        // 组装文件名称
        String suffix;
        if ("data:image/jpeg;".equalsIgnoreCase(dataPrefix)) {
            // data:image/jpeg;base64 base64编码的jpeg图片数据
            suffix = ".jpg";
        } else if ("data:image/x-icon;".equalsIgnoreCase(dataPrefix)) {
            // data:image/x-icon;base64 base64编码的icon图片数据
            suffix = ".ico";
        } else if ("data:image/gif;".equalsIgnoreCase(dataPrefix)) {
            // data:image/gif;base64 base64编码的gif图片数据
            suffix = ".gif";
        } else if ("data:image/png;".equalsIgnoreCase(dataPrefix)) {
            // data:image/png;base64 base64编码的png图片数据
            suffix = ".png";
        } else {
            throw new Exception("上传图片格式不合法，支持jpg/ico/gif/png");
        }
        String fileName = KeyUtil.generatorNonceStr() + suffix;
        // 用于处理：如果保存图片的路径不存在，则创建路径
        makeDirPath(targetAddress);

        // 生成新的文件名及其路径（相对路径）
        String relativeAddress = targetAddress + fileName;
        logger.debug("新文件生成的相对路径：" + relativeAddress);
        // 新文件输出路径
        File dest = new File(ImagePathUtil.getImgBasePath() + relativeAddress);
        logger.debug("新文件生成的绝对路径：" + ImagePathUtil.getImgBasePath() + relativeAddress);

        // 3、将base64编码字符串转换成二进制数组
        byte[] array = Base64.decodeBase64(data.getBytes());

        // 4、将base编码字符串写入设置的文件下
        try {
            FileUtils.writeByteArrayToFile(dest, array);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 5、设置属性
        wechatMaterialImage.setTitle(fileName);
        wechatMaterialImage.setLocalImgUrl(relativeAddress);

        return relativeAddress;
    }

    /**
     * 创建目标路径所涉及的目录
     *
     * @param targetAddress
     */
    public static void makeDirPath(String targetAddress) {
        String path = ImagePathUtil.getImgBasePath() + targetAddress;
        File dirPath = new File(path);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    /**
     * 计算图片base64流字节大小
     *
     * @param base64Data
     * @return
     * @throws Exception
     */
    public static Integer imageSize(String base64Data) throws Exception {
        // 1、需要计算文件流大小，首先把头部的data:image/png;base64,（注意有逗号）去掉
        String data;
        if (StringUtils.isBlank(base64Data)) {
            throw new Exception("上传失败，上传图片数据为空");
        } else {
            String[] d = base64Data.split("base64,");
            if (d != null && d.length == 2) {
                data = d[1];
            } else {
                throw new Exception("上传失败，数据不合法");
            }
        }

        //2、找到等号，把等号也去掉
        Integer equalIndex = data.indexOf("=");
        if (data.indexOf("=") > 0) {
            data = data.substring(0, equalIndex);
        }

        //3、原来的字符流大小，单位为字节
        Integer strLength = data.length();

        //4、计算后得到的文件流大小，单位为字节
        Integer size = strLength - (strLength / 8) * 2;

        return size;
    }

    /**
     * 通过上传的文件名生成新的文件名
     *
     * @param oldFileName
     * @return
     * @throws Exception
     */
    public static String generateNewFileName(String oldFileName) throws Exception {
        if (StringUtils.isEmpty(oldFileName)) {
            throw new Exception("文件名为空");
        }

        String suffix = oldFileName.substring(oldFileName.indexOf("."), oldFileName.length());
        String newFileName = KeyUtil.generatorNonceStr() + suffix;

        return newFileName;
    }
}
