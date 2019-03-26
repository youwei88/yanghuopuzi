package com.wmeimob.tool.security;


import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AESUtil {


    /**
     * 注意key和加密用到的字符串是不一样的 加密还要指定填充的加密模式和填充模式 AES密钥可以是128或者256，加密模式包括ECB, CBC等
     * ECB模式是分组的模式，CBC是分块加密后，每块与前一块的加密结果异或后再加密 第一块加密的明文是与IV变量进行异或
     */
    private static final String KEY_ALGORITHM = "AES";
    private static final String ECB_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final String CBC_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String PLAIN_TEXT = "MANUTD is the greatest club in the world";

    /**
     * IV(Initialization Value)是一个初始值，对于CBC模式来说，它必须是随机选取并且需要保密的
     * 而且它的长度和密码分组相同(比如：对于AES 128为128位，即长度为16的byte类型数组)
     */
    private static final byte[] IVPARAMETERS = new byte[]{1, 2, 3, 4, 5, 6, 7,
            8, 9, 10, 11, 12, 13, 14, 15, 16};

    public static void main(String[] arg) throws UnsupportedEncodingException {
        byte[] secretBytes = generateAESSecretKey();
        System.out.println(secretBytes.length);
//        byte[] secretBytes = "123456".getBytes();
        SecretKey key = restoreSecretKey(secretBytes);
        byte[] encodedText = AesEcbEncode(PLAIN_TEXT.getBytes(), key);

        System.out.println("AES ECB encoded with Base64: " + Base64.encodeBase64String(encodedText));
        System.out.println("AES ECB decoded: "
                + AesEcbDecode(encodedText, key));


        encodedText = AesCbcEncode(PLAIN_TEXT.getBytes(), key, IVPARAMETERS);


        System.out.println("AES CBC encoded with Base64: " + Base64.encodeBase64String(encodedText));
        System.out.println("AES CBC decoded: "
                + AesCbcDecode(encodedText, key,
                IVPARAMETERS));
    }

    /**
     * 使用ECB模式进行加密。 加密过程三步走：
     * 1. 传入算法，实例化一个加解密器
     * 2. 传入加密模式和密钥，初始化一个加密器
     * 3.调用doFinal方法加密
     *
     * @param plainText
     * @return
     */
    public static byte[] AesEcbEncode(byte[] plainText, SecretKey key) {

        try {

            Cipher cipher = Cipher.getInstance(ECB_CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(plainText);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static String AesEcbEncode(String content) {
        byte[] plainText = content.getBytes();
        byte[] secretBytes = generateAESSecretKey();
        SecretKey key = restoreSecretKey(secretBytes);
        return Base64.encodeBase64String(AesEcbEncode(plainText,key));
    }

    public static String AesEcbEncode(String content, String key) {
        byte[] plainText = content.getBytes();
        SecretKey secretKey = restoreSecretKey(key.getBytes());
        return Base64.encodeBase64String(AesEcbEncode(plainText, secretKey));
    }

    public static String AesEcbDecode(String content, String key) {
        byte[] plainText =Base64.decodeBase64(content);
        SecretKey secretKey = restoreSecretKey(key.getBytes());
        return AesEcbDecode(plainText, secretKey);
    }


    /**
     * 使用ECB解密
     *
     * @param decodedText
     * @param key
     * @return
     */
    public static String AesEcbDecode(byte[] decodedText, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance(ECB_CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(decodedText));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * CBC加密，三步走，只是在初始化时加了一个初始变量
     *
     * @param plainText
     * @param key
     * @param IVParameter
     * @return
     */
    public static byte[] AesCbcEncode(byte[] plainText, SecretKey key,
                                      byte[] IVParameter) {
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IVParameter);

            Cipher cipher = Cipher.getInstance(CBC_CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
            return cipher.doFinal(plainText);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | InvalidAlgorithmParameterException
                | IllegalBlockSizeException | BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * CBC 解密
     *
     * @param decodedText
     * @param key
     * @param IVParameter
     * @return
     */
    public static String AesCbcDecode(byte[] decodedText, SecretKey key,
                                      byte[] IVParameter) {
        IvParameterSpec ivParameterSpec = new IvParameterSpec(IVParameter);

        try {
            Cipher cipher = Cipher.getInstance(CBC_CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
            return new String(cipher.doFinal(decodedText));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | InvalidAlgorithmParameterException
                | IllegalBlockSizeException | BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;

    }

    /**
     * 1.创建一个KeyGenerator 2.调用KeyGenerator.generateKey方法
     * 由于某些原因，这里只能是128，如果设置为256会报异常，原因在下面文字说明
     * 因为某些国家的进口管制限制，Java发布的运行环境包中的加解密有一定的限制。比如默认不允许256位密钥的AES加解密，解决方法就是修改策略文件。
     * <p>
     * <p>
     * 官方网站提供了JCE无限制权限策略文件的下载：
     * <p>
     * JDK6的下载地址：
     * http://www.oracle.com/technetwork/java/javase/downloads/jce-6-download-429243.html
     * <p>
     * JDK7的下载地址：
     * http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html
     * <p>
     * JDK8的下载地址：
     * http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html
     * <p>
     * 下载后解压，可以看到local_policy.jar和US_export_policy.jar以及readme.txt。
     * <p>
     * 如果安装了JRE，将两个jar文件放到%JRE_HOME%\lib\security下覆盖原来文件，记得先备份。
     * <p>
     * 如果安装了JDK，将两个jar文件也放到%JDK_HOME%\jre\lib\security下。
     *
     * @return
     */
    private static byte[] generateAESSecretKey() {
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
            // keyGenerator.init(256);
            return keyGenerator.generateKey().getEncoded();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 还原密钥
     *
     * @param secretBytes
     * @return
     */
    private static SecretKey restoreSecretKey(byte[] secretBytes) {
        SecretKey secretKey = new SecretKeySpec(secretBytes, KEY_ALGORITHM);
        return secretKey;
    }

}



