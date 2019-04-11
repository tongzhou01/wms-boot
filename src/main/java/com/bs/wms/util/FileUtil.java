package com.bs.wms.util;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class FileUtil {

    /**
     * 下载文件
     * @param downloadFile
     * @param response
     * @param fileName
     * @throws IOException
     */
    public static void downloadFile(File downloadFile, HttpServletResponse response, String fileName) throws IOException {
        response.addHeader("Content-Disposition", "attachment;filename=".concat(new String(fileName.getBytes("utf-8"), "iso8859-1")));
        response.setContentType("application/x-msdownload");
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            if (downloadFile.exists()) {
                int len = 0;
                byte[] buffer = new byte[1024];
                outputStream = new BufferedOutputStream(response.getOutputStream());
                inputStream = new BufferedInputStream(new FileInputStream(downloadFile));
                while ((len = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }
            }
        } catch (Exception e) {
        } finally {
            if(null != outputStream){
                outputStream.flush();
                outputStream.close();
            }
            if(inputStream != null){
                inputStream.close();
            }
        }
    }
}
