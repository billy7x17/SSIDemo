/**
 * Copyright 2013 China Mobile Research Institute. All Right Reserved
 *
 * This file is owned by China Mobile and you may not use, modify, copy,
 * redistribute this file without written permissions.
 *
 * These Terms of Use define legal use of this file, all updates, revisions,
 * substitutions for you. All rights not expressly granted to you are reserved
 * by Chinamobile.
 */
package com.cloudmaster.cmp.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import com.cloudmaster.cmp.core.CommonStatusCode;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 文件操作工具类
 * @author <a href="mailto:hegq@neusoft.com">puras.he </a>
 * @version 1.0.0 18 Mar 2012
 */
/**
 * 修改日志打印问题，去掉无用的引用 modify by zhao.chy 2010-05-31
 */
public final class FileUtil {

    //log
    private static LogService logger = LogService.getLogger(FileUtil.class);

    //缓存大小
    private static final int BUFFER_SIZE = 5120;

    /**
     * 构造函数
     */
    private FileUtil() {
    }

    /**
     * 文件的复制，不可以是文件夹
     * @param src 文件
     * @param dst 目的路径
     * @return 是否成功
     * @throws IOException
     */
    public static boolean copyFile(File src, String dst) throws IOException {
        File dstFile = new File(dst);
        if (!src.exists() || !dstFile.exists()) {
            return false;
        }
        InputStream in = null;
        OutputStream out = null;
        FileOutputStream fout = null;
        FileInputStream fin = null;

        try {
            fin = new FileInputStream(src);
            fout = new FileOutputStream(dst);
            in = new BufferedInputStream(fin, BUFFER_SIZE);
            out = new BufferedOutputStream(fout, BUFFER_SIZE);
            byte[] buffer = new byte[BUFFER_SIZE];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } finally {
            if (null != in) {
                try {
                    in.close();
                    fin.close();
                } catch (Exception e) {
                    if (null != out) {
                        out.flush();
                        fout.flush();
                        out.close();
                        fout.close();
                    }
                    return false;
                }
            }
            if (null != out) {
                out.flush();
                fout.flush();
                out.close();
                fout.close();
            }
        }
        return true;
    }

    /**
     * 文件的复制
     * @param src 文件
     * @param dst 目的路径
     * @return 是否成功
     * @throws IOException
     */
    public static boolean copyFileTemp(File src, String dst) throws IOException {
        if (!src.exists()) {
            return false;
        }
        InputStream in = null;
        OutputStream out = null;
        FileOutputStream fout = null;
        FileInputStream fin = null;

        try {
            fin = new FileInputStream(src);
            fout = new FileOutputStream(dst);
            in = new BufferedInputStream(fin, BUFFER_SIZE);
            out = new BufferedOutputStream(fout, BUFFER_SIZE);
            byte[] buffer = new byte[BUFFER_SIZE];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } finally {
            if (null != in) {
                try {
                    in.close();
                    fin.close();
                } catch (Exception e) {
                    if (null != out) {
                        out.flush();
                        fout.flush();
                        out.close();
                        fout.close();
                    }
                    return false;
                }
            }
            if (null != out) {
                out.flush();
                fout.flush();
                out.close();
                fout.close();
            }
        }
        return true;
    }

    /**
     * 文件夹下的内容全部复制到另一个文件夹下
     * @param formerfile 源文件夹
     * @param destinyfile 目标文件夹
     * @throws IOException
     */
    public static boolean copyFolder(String formerfile, String destinyfile) throws IOException {
        File formerFile = new File(formerfile);
        File filePath = new File(destinyfile);
        if (!formerFile.exists()) {
            return false;
        }
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        File[] file = formerFile.listFiles();
        if (null == file) {
            return true;
        }
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                FileInputStream input = null;
                FileOutputStream output = null;
                try {
                    input = new FileInputStream(file[i]);
                    output = new FileOutputStream(destinyfile + "/" + file[i].getName());
                    byte[] b = new byte[BUFFER_SIZE];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                } finally {
                    try {
                        if (null != output) {
                            output.flush();
                            output.close();
                        }
                    } catch (Exception e) {
                        if (null != input) {
                            input.close();
                        }
                        return false;
                    }
                    if (null != input) {
                        input.close();
                    }
                }
            }
            if (file[i].isDirectory()) {
                copyDirectiory(destinyfile + "/" + file[i].getName(), formerfile + "/"
                        + file[i].getName());
            }
        }
        return true;
    }

    /**
     * copyFolder之后要执行的函数
     * @param formerfile 源文件夹
     * @param destinyfile 目标文件夹
     * @throws IOException
     */
    public static void copyDirectiory(String destinyfile, String formerfile) throws IOException {
        (new File(destinyfile)).mkdirs();
        File[] file = (new File(formerfile)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                FileInputStream input = null;
                FileOutputStream output = null;
                try {
                    input = new FileInputStream(file[i]);
                    output = new FileOutputStream(destinyfile + "/" + file[i].getName());
                    byte[] b = new byte[BUFFER_SIZE];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                } finally {
                    output.flush();
                    output.close();
                    input.close();
                }
            }
            if (file[i].isDirectory()) {
                copyDirectiory(destinyfile + "/" + file[i].getName(), formerfile + "/"
                        + file[i].getName());
            }
        }
    }

    /**
     * 获取扩展名
     * @param fileName 文件名
     * @return String 文件扩展名
     */
    public static String getExtention(String fileName) {
        int pos = fileName.lastIndexOf(".");
        return fileName.substring(pos);
    }

    /**
     * 删除文件
     * @param filePath 文件路径
     */
    public static void removeFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

    public static void main(String[] args) {
        // removeFile(Constant.NEWS_UPLOAD_PATH + "delete.jpg");
    }

    /**
     * 删除文件夹里面的所有文件
     * @param delFile (File) 文件夹路径 如 c:/fqf
     * @return true成功,false失败
     */
    private static boolean delAllFile(File delFile) {
        if (!delFile.exists()) {
            return false;
        }
        if (!delFile.isDirectory()) {
            return false;
        }
        File[] fileList = delFile.listFiles();
        for (File tempFile : fileList) {
            if (tempFile.isDirectory()) {
                delFolder(tempFile);
            }
            if (tempFile.isFile()) {
                tempFile.delete();
            }
        }
        return true;
    }

    /**
     * 删除文件夹及文件夹下所有文件
     * @param folderPath (File)文件夹路径及名称 如c:/fqf
     * @return true 删除成功, false 删除失败
     */
    public static boolean delFolder(File folderPath) {
        /**
         * 删除文件夹下所有内容
         */
        if (!folderPath.exists()) {
            return false;
        }
        if (!folderPath.isDirectory()) {
            return false;
        }
        delAllFile(folderPath);
        File myFilePath = folderPath;
        /**
         * 删除空文件夹
         */
        myFilePath.delete();
        return true;
    }

    /**
     * 读取文件，返回文家内容
     * @param file 文件
     * @return 内容
     * @throws IOException
     */
    public static String readerFile(File file) throws IOException {
        if (null == file || !file.exists()) {
            if (logger.isDebugEnable()) {
                logger.debug("出现异常，需要读取的文件为空");
            }
            return null;
        }

        StringBuffer result = new StringBuffer();
        BufferedReader reader = null;
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(new FileInputStream(file), "GBK");
            reader = new BufferedReader(isr);
            int single;
            while ((single = reader.read()) > 0) {
                result.append((char) single);
            }
        } catch (Exception e) {
            logger.error(CommonStatusCode.COMMON_RUNTIME_EXCEPTION, "读取文件时出现异常", e);
        } finally {
            if (null != isr) {
                isr.close();
            }
        }
        if (logger.isDebugEnable()) {
            logger.debug("文件内容： " + result);
        }
        String s = result.toString();
        return s;
    }

    /**
     * 将制定内容写入文件
     * @param content 文件内容
     * @param filePath 文件路径
     * @return
     */
    public static boolean writer(String content, String filePath) {
        // FileWriter fileWriter = null;
        boolean result = false;
        OutputStreamWriter outputStream = null;
        File aimFile = new File(filePath);
        try {
            if (!aimFile.exists()) {
                if (!aimFile.createNewFile()) {
                    logger.error(CommonStatusCode.COMMON_RUNTIME_EXCEPTION,
                            "写文件时出现异常，创建目标文件  [" + aimFile.getPath()+ " ] 时出错！");
                    return result;
                }
            }
            // fileWriter = new FileWriter(aimFile);
            // fileWriter.write(content);
            // fileWriter.flush();

            outputStream = new OutputStreamWriter(new FileOutputStream(aimFile), "GBK");
            outputStream.write(content);
            outputStream.flush();
            result = true;
        } catch (IOException e) {
            logger.error(CommonStatusCode.COMMON_RUNTIME_EXCEPTION,
                    "写文件时" + aimFile.getPath()+ "出现异常！", e);
        } finally {
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    logger.error(CommonStatusCode.COMMON_RUNTIME_EXCEPTION, "写文件时出现异常，流未关闭！", e);
                }
            }
        }
        return result;
    }
}
