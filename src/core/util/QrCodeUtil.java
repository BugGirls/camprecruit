package core.util;

<<<<<<< HEAD
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * 二维码生成和读取的工具类
 *
 * @author Hystar
 * @date 2018/8/20
 */
public class QrCodeUtil {

    /**
     * 生成包含字符串信息的二维码图片
     *
     * @param outputStream 要存储的文件
     * @param content      二维码携带的内容
     * @param qrCodeSize   二维码图片大小
     * @param imageFormat  二维码格式
     * @return
     * @throws WriterException
     * @throws IOException
     */
    public static boolean createQrCode(OutputStream outputStream, String content, int qrCodeSize, String imageFormat) throws WriterException, IOException {
        // 设置二维码纠错级别 M A P
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
        // 娇错级别
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        // 创建比特矩阵（位矩阵）的QR码编码的字符串
        BitMatrix byteMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize, hintMap);
        // 使用BufferedImage勾画QrCode(matrixWidth 是行二维码像素点)
        int matrixWidth = byteMatrix.getWidth();
        BufferedImage image = new BufferedImage(matrixWidth - 200, matrixWidth - 200, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth, matrixWidth);
        // 使用比特矩阵画并保存图像
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i - 100, j - 100, 1, 1);
                }
            }
        }
        return ImageIO.write(image, imageFormat, outputStream);
    }

    /**
     * 读取二维码携带的信息
     *
     * @param inputStream
     * @throws IOException
     */
    public static void readQrCode(InputStream inputStream) throws IOException {
        //从输入流中获取字符串信息
        BufferedImage image = ImageIO.read(inputStream);
        // 将图像转换为二进制位图源
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        Result result = null;
        try {
            result = reader.decode(binaryBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result.getText());
    }

//    public static void main(String[] args) throws IOException, WriterException {
////        createQrCode(new FileOutputStream(new File("d:\\qrcode.jpg")), "http://www.baidu.com", 900, "JPG");
//        readQrCode(new FileInputStream(new File("F:\\camprecruit\\out\\artifacts\\camprecruit_war_exploded\\upload\\qrcode\\dc2196929edc490886d1fa63ce15d56f.png")));
=======
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;

/**
 * 二维码工具类
 *
 * @author Hystar
 * @date 2017/11/13
 */
public class QrCodeUtil {

    private static final Logger logger = LoggerFactory.getLogger(QrCodeUtil.class);

    /**
     * 生成二维码，二维码相对路径
     *
     * 在二维码中存放护照编号
     *
     * @param passportNo
     * @param targetAddress 二维码生成的相对路径
     * @return
     */
//    public static String generateQRCode(String passportNo, String targetAddress) {
//        // 设置二维码的宽度和高度
//        int width = 300;
//        int height = 300;
//        // 设置生成的二维码格式
//        String format = "png";
//        // 设置二维码内容
//        String content = passportNo;
//
//        // 定义二维码的参数
//        HashMap hints = new HashMap(16);
//        // 设置二维码的编码格式
//        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//        // 设置二维码的纠正等级
//        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
//        // 设置二维码离边框的距离
//        hints.put(EncodeHintType.MARGIN, 2);
//
//        // 获取随机生成的新图片名
//        String realFileName = ImageUtil.getRandomFileName();
//        // 获取图片的扩展名
//        String extension = ".jpg";
//        // 用于处理：如果保存图片的路径不存在，则创建路径
//        ImageUtil.makeDirPath(targetAddress);
//
//        // 生成新的文件名及其路径（相对路径）
//        String relativeAddress = targetAddress + realFileName + extension;
//        logger.debug("【生成二维码】二维码生成的相对路径：" + relativeAddress);
//        // 新文件输出路径
//        File dest = new File(PathUtil.getImgBasePath() + relativeAddress);
//        logger.debug("【生成二维码】二维码生成的绝对路径：" + PathUtil.getImgBasePath() + relativeAddress);
//
//        // 生成二维码
//        try {
//            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
//            Path file = dest.toPath();
//            MatrixToImageWriter.writeToPath(bitMatrix, format, file);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return relativeAddress;
>>>>>>> merge project
//    }

}
