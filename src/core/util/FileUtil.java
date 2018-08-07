package core.util;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 文件工具类
 *
 * @author Hystar
 * @date 2018/1/23
 */
public class FileUtil {
    /**
     * 删除单个文件
     *
     * @param fileName 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 复制文件
     *
     * @param srcFile
     * @param toFile
     * @throws IOException
     */
    public static void copyFile(File srcFile, File toFile) throws IOException {
        FileInputStream ins = new FileInputStream(srcFile);
        FileOutputStream out = new FileOutputStream(toFile);
        byte[] b = new byte[1024];
        int n = 0;
        while ((n = ins.read(b)) != -1) {
            out.write(b, 0, n);
        }

        ins.close();
        out.close();
    }

    /**
     * 复制文件
     *
     * @param srcFile
     * @param toFile
     * @throws IOException
     */
    public static void copyFile(String srcFile, String toFile) throws IOException {
        FileInputStream ins = new FileInputStream(srcFile);
        FileOutputStream out = new FileOutputStream(toFile);
        byte[] b = new byte[1024];
        int n = 0;
        while ((n = ins.read(b)) != -1) {
            out.write(b, 0, n);
        }

        ins.close();
        out.close();
    }


    /**
     * 下载文件
     *
     * @param response
     * @param file
     * @Description 下载文件
     * @author Hystar
     * @date 2015年12月7日 上午10:34:23
     */
    public static void downloadFile(HttpServletResponse response, File file) {
        if (file == null || !file.exists()) {
            return;
        }
        OutputStream out = null;
        try {
            response.reset();
            response.setContentType("application/octet-stream; charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
            out = response.getOutputStream();
            out.write(FileUtils.readFileToByteArray(file));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param fileName
     * @param file
     * @return
     * @throws IOException
     * @Description 下载文件
     * @author Hystar
     * @date 2015年12月11日 下午6:11:33
     */
    public static ResponseEntity<byte[]> downloadFile2(String fileName, File file) throws IOException {
        String dfileName = new String(fileName.getBytes("gb2312"), "iso8859-1");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", dfileName);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }

}
