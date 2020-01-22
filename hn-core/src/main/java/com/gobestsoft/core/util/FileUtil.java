package com.gobestsoft.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileUtil {

    private static Logger log = LoggerFactory.getLogger(FileUtil.class);

    /**
     * NIO way
     */
    public static byte[] toByteArray(String filename) throws Exception {

        File f = new File(filename);
        if (!f.exists()) {
            log.error("文件未找到！" + filename);
            throw new Exception("文件未找到！");
        }
        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
                // do nothing
                // System.out.println("reading");
            }
            return byteBuffer.array();
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                throw e;
            }
            try {
                fs.close();
            } catch (IOException e) {
                throw e;
            }
        }
    }

    /**
     * 删除目录
     *
     * @author gobestsoft
     * @Date 2017/10/30 下午4:15
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * (BASE64)图片到服务器
     *
     * @param imgStr       base64字符串
     * @param relativePath 根目录
     * @param folderName   文件目录
     * @return
     */
    public static String createCover(String imgStr, String relativePath, String folderName) throws IOException {
        if (ToolUtil.isEmpty(imgStr)) {
            return "";
        }
        // 根据资讯id创建目录
        String folderSrc = FileUtil.createFolder(relativePath + folderName);
        // 图片文件名 = uuid
        String imgName = UUIDUtil.getUUID() + ".jpg";
        // base64的 imgStr字符串 转为xxx.jpg文件 输出到folderSrc的目录下
        String imgSrc = folderSrc + imgName;
        FileUtil.generateImage(imgStr, imgSrc);

        // 返回文件路径
        return folderName + "/" + imgName;
    }

    /**
     * (BASE64)Base64解码并生成图片
     */
    public static void generateImage(String imgStr, String imgFilePath) throws IOException {
        // 对字节数组字符串进行Base64解码并生成图片
        BASE64Decoder decoder = new BASE64Decoder();
        // Base64解码
        byte[] bytes = decoder.decodeBuffer(imgStr);
        for (int i = 0; i < bytes.length; ++i) {
            if (bytes[i] < 0) {// 调整异常数据
                bytes[i] += 256;
            }
        }
        // 生成jpeg图片
        OutputStream out = new FileOutputStream(imgFilePath);
        out.write(bytes);
        out.flush();
        out.close();
    }

    /**
     * 创建文件目录
     *
     * @param path
     * @return
     */
    public static String createFolder(String path) throws IOException {
        File dir = new File(path);
        String str = "";
        if (!path.endsWith(File.separator)) {
            path = path + File.separator;
        }
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                log.info("创建目录" + path + str + "成功！");
                return path + str;
            } else {
                log.error("创建目录" + path + str + "失败！");
                throw new IOException();
            }
        } else {
            log.info("目录" + path + str + "已存在，无需再次创建！");
            return path + str;
        }
    }

    /**
     * 读取文件
     *
     * @param path
     * @return
     */
    public static String getFileContent(String path) {
        BufferedReader reader = null;
        String laststr = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr += tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return laststr;
    }

    /**
     * 读取文件
     *
     * @param file
     * @return
     */
    public static String getFileContent(File file) {
        BufferedReader reader = null;
        String laststr = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr += tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return laststr;
    }

    /**
     * 获取resource下目录路径
     * @param dirName
     * @return
     */
    public static String getResoucePath(String dirName){
        try{
            return ResourceUtils.getFile("classpath:"+dirName).getPath();
        }catch (FileNotFoundException e){
            log.error(dirName+"在resource下不存在");
        }
        return "";
    }
}