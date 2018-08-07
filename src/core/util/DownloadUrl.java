package core.util;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 使用浏览器从服务器下载文件
 *
 * @author Empress
 * @data 2018/7/15
 */
public class DownloadUrl {

    /**
     * 使用浏览器从服务器下载文件
     *
     * @param urlStr
     * @param response
     * @throws IOException
     */
    public static void download(String urlStr, HttpServletResponse response) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3 * 1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = readInputStream(inputStream);

        /*  设置文件ContentType类型，这样设置，会自动判断下载文件类型   */
        response.setContentType("application/multipart/form-data");
        /* 设置文件头：最后一个参数是设置下载文件名(假如我们叫a.ini)   */
        response.setHeader("Content-Disposition", "attachment;filename=" + KeyUtil.generatorUniqueKey() + ".jpg");
        try {
            /* 用流将数据写给前端 */
            OutputStream os = response.getOutputStream();
            os.write(getData);
            os.flush();
            os.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }
}
