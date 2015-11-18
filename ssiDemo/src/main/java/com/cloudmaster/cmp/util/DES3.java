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

import java.security.Key;

import javax.crypto.Cipher;

import javax.crypto.spec.SecretKeySpec;

import com.neusoft.mid.iamp.logger.LogService;

/**
 * 加密工具类
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public class DES3 {
    // log
    private static LogService log = LogService.getLogger(DES3.class);

    // 定义
    private static final String Algorithm = "DESede";

    // 字符编码
    private static final String encoding = "UTF8";

    // 密钥
    private String keyString = null;

    // 密钥
    private static Key key;

    /**
     * 构造函数
     */
    public DES3() {
    }

    /**
     * 根据参数生成KEY
     * @param strKey 密钥字符串，24字节的串
     */
    private void getKey(String strKey) {
        try {
            /*
             * KeyGenerator _generator = KeyGenerator.getInstance("DESede"); // _generator.init(new
             * SecureRandom(strKey.getBytes())); _generator.init(168); //key =
             * _generator.generateKey(); _generator = null;
             */
            // 生成密钥
            key = new SecretKeySpec(strKey.getBytes(), Algorithm);

        } catch (Exception e) {
            log.error("根据参数生成KEY出现异常", e);
        }
    }

    /**
     * 加密
     * @param keybyte keybyte为加密密钥，长度为24字节
     * @param src src为加密后的缓冲区
     * @return 字节数组
     */
    private byte[] encryptMode(byte[] keybyte, byte[] src) {
        try {
            // 生成密钥
            getKey(keyString);
            Key deskey = key;// new SecretKeySpec(keybyte, Algorithm);

            // 加密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(src);

        } catch (java.lang.Exception e) {
            log.error("加密出现异常", e);
        }
        return null;
    }

    /**
     * 解密
     * @param keybyte keybyte为解密密钥，长度为24字节
     * @param src src为解密后的缓冲区
     * @return 字节数组
     * @throws Exception
     */
    private byte[] decryptMode(byte[] keybyte, byte[] src) throws Exception {

        // 生成密钥
        // SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
        getKey(keyString);
        Key deskey = key;

        // 解密
        Cipher c1 = Cipher.getInstance(Algorithm);
        c1.init(Cipher.DECRYPT_MODE, deskey);
        return c1.doFinal(src);
    }

    /**
     * 转换成十六进制字符串
     * @param bytes 为字节数组
     * @return 字符串
     */
    private String byte2hexString(byte[] bytes) {
        StringBuffer buf = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            if (((int) bytes[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString((int) bytes[i] & 0xff, 16));
        }
        return buf.toString();
    }

    /**
     * 转换成十六进制byte数组
     * @param hexString 字符串
     * @return bytes 为字节数组
     */
    private byte[] hexString2byte(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0) return null;
        byte[] hanzi = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i += 2)
            hanzi[i / 2] = (byte) (Integer.parseInt(hexString.substring(i, i + 2), 16) & 0xff);
        return hanzi;
    }

    /**
     * 加密字符串
     * @param src 源字符串
     * @return 加密后的字符串
     */
    public String encrypt(String src) {
        String result = null;
        if (src == null) {
            return result;
        }
        try {
            byte[] encoded = encryptMode(null, src.getBytes(encoding));
            if (encoded != null) {
                result = byte2hexString(encoded);
            }
        } catch (Exception e) {
            log.error("加密出现异常", e);
            return null;
        }
        return result;
    }

    /**
     * 解密字符串
     * @param src 源字符串
     * @return 解密后的字符串
     */
    public String decrypt(String src) {
        String result = null;
        if (src == null) {
            return result;
        }
        try {
            String temp = new String(src.getBytes(encoding));
            byte[] encoded = hexString2byte(temp);
            byte[] srcBytes = decryptMode(null, encoded);
            if (srcBytes != null) {
                result = new String(srcBytes, encoding);
            }
        } catch (Exception e) {
            log.error("解密出现异常", e);
            return null;
        }
        return result;
    }

    public static void main(String[] args) {
        DES3 des3 = new DES3();
        des3.setKeyString("loginDES3keyStringString");
        System.out.println("加密：" + des3.encrypt("000000"));

        System.out.println("解密：" + des3.decrypt("f7d827bd1a3ef484"));
    }

    public String getKeyString() {
        return keyString;
    }

    public void setKeyString(String keyString) {
        this.keyString = keyString;
    }

}
