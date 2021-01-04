package com.mao.ky.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : create by zongx at 2020/11/17 14:55
 */
public class FileUtil {

    public static final String PATH = "C:\\Users\\zongx\\Desktop\\images";

    public static String downLoadFromUrl(String urlStr, String fileName) {
        try {

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            // 防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            // 得到输入流
            InputStream inputStream = conn.getInputStream();
            // 获取自己数组
            byte[] getData = readInputStream(inputStream);

            // 文件保存位置
            File saveDir = new File(PATH);
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }
            File file = new File(saveDir + File.separator + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(getData);
            fos.close();
            inputStream.close();
            // System.out.println("info:"+url+" download success");
            return saveDir + File.separator + fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    /**
     * 从输入流中获取字节数组
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

    public static final int CLASSPATH = 1;
    public static final int LOCAL = 2;

    /**
     * 获取文件字符串数据
     */
    public static List<String> getFileString(String path, int mode) {
        InputStream inputStream = getInputStream(path, mode);
        if (null == inputStream)
            return null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        List<String> list = new ArrayList<>();
        reader.lines().forEach(list::add);
        return list;
    }

    /**
     * 获取inputStream
     * @param path 地址
     * @param mode 可选项目中文件和本地资源文件
     * @return InputStream
     */
    public static InputStream getInputStream(String path, int mode) {
        switch (mode) {
            case CLASSPATH: return getClasspathInputStream(path);
            case LOCAL: return getLocalInputStream(path);
            default: return null;
        }
    }

    /**
     * 获取项目中文件InputStream
     */
    private static InputStream getClasspathInputStream(String path) {
        if (!path.startsWith("/"))
            path = "/" + path;
        try {
            return FileUtil.class.getResourceAsStream(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取本地资源文件InputStream
     */
    private static InputStream getLocalInputStream(String path) {
        File file = new File(path);
        if (file.exists()) {
            try {
                return new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static void main(String[] args) {

    }

}
