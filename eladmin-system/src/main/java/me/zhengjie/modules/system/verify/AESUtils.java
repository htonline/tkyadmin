package me.zhengjie.modules.system.verify;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

public class AESUtils {

    public static String encrypt(String key, String initVector, String value) throws Exception {
        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        // cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

        byte[] encrypted = cipher.doFinal(value.getBytes());
        /*
         * System.out.println("encrypted string: " + +
         * Base64.encodeBase64String(encrypted));
         */
        // System.out.println(Base64.encodeBase64String(encrypted));
        return Base64.encodeBase64String(encrypted);

    }

    public static String decrypt(String key, String initVector, String encrypted) throws Exception {
        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        // cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

        return new String(original);

    }

    /*
     * public static void main(String[] args) throws Exception { String key =
     * "r93535.com/.!!!!"; // 128 bit key String initVector = "0987654321234567"; //
     * 16 bytes IV //System.out.println(encrypt(key,initVector,"R93535.com,"));
     *
     * //String pwd=encrypt(key,initVector,"QWER1234"); //System.out.println(pwd);
     *
     * System.out.println(getLoginInfo("jzxiangxiaoliang","xxl111888")); }
     */

    public static void main(String[] args) throws Exception {
        // 密码加密
        String key = "r93535.com/.!!!!"; // 128 bit key
        String initVector = "0987654321234567";
        String pwd = "Wt.5766099";
        pwd = encrypt(key, initVector, pwd);//生成密码
        System.out.println(pwd);
        String key2 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCEJiVVdmEzgRk/Lqg+I8JyavmI6eRZCv9d3rJ/yDGUS0BbxrrUrhdK4t/hLZUjkjtCsX80eLxecn6HkMBHaEM0tQYmZyxpB5NBPHioggQHDrJVskzDdqliHobem+X54INPznlXXwLtRl4vh1/ducI7laD59bVg/I+h/SrepgOvqwIDAQAB";
        String strworld = "{\"appname\":\"r\",\"account\":\"wgwangy\",\"password\":\"jXY3aFLf5TZlGyterIkUzg==\",\"timestamp\":\"1661913589\"}";
//        System.out.println(strworld.getBytes().length);
        System.out.println(encryptByPublicKey(key2,strworld));
    }
    /**
     * 公钥加密
     *
     * @param publicKeyString 公钥
     * @param text 待加密的文本
     * @return 加密后的文本
     */
    public static String encryptByPublicKey(String publicKeyString, String text) throws Exception
    {
        X509EncodedKeySpec x509EncodedKeySpec2 = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyString));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec2);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] result = cipher.doFinal(text.getBytes());
        return Base64.encodeBase64String(result);
    }
    public static byte[] encryptByPublicKey(byte[] data, String key) throws Exception {
        // 对公钥解密
        byte[] keyBytes = decryptBASE64(key);

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }
    public static String encryptByPublicKey2(String data, String key) throws Exception {
        return Base64.encodeBase64String(encryptByPublicKey(data.getBytes(), key));
    }

}