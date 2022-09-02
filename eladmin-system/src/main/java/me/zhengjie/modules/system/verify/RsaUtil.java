package me.zhengjie.modules.system.verify;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

public class RsaUtil {
    public static String jwt;
    public RsaUtil() {
    }

    public static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }

    public static String getPublicKey(KeyPair keyPair) {
        PublicKey publicKey = keyPair.getPublic();
        byte[] bytes = publicKey.getEncoded();
        return byte2Base64(bytes);
    }

    public static String getPrivateKey(KeyPair keyPair) {
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] bytes = privateKey.getEncoded();
        return byte2Base64(bytes);
    }

    public static PublicKey string2PublicKey(String pubStr) throws Exception {
        byte[] keyBytes = base642Byte(pubStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    public static PrivateKey string2PrivateKey(String priStr) throws Exception {
        byte[] keyBytes = base642Byte(priStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    public static byte[] publicEncrypt(byte[] content, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(1, publicKey);
        byte[] bytes = cipher.doFinal(content);
        return bytes;
    }

    public static String encipher(byte[] ciphertext, PublicKey key, int segmentSize) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(1, key);
//            byte[] resultBytes = null;
            byte[] resultBytes;
            if (segmentSize > 0) {
                resultBytes = cipherDoFinal(cipher, ciphertext, segmentSize);
            } else {
                resultBytes = cipher.doFinal(ciphertext);
            }

            String base64Str = HexUtil.encodeHexStr(resultBytes);
            return base64Str;
        } catch (Exception var6) {
            var6.printStackTrace();
            return null;
        }
    }

    public static byte[] cipherDoFinal(Cipher cipher, byte[] srcBytes, int segmentSize) throws IllegalBlockSizeException, BadPaddingException, IOException {
        if (segmentSize <= 0) {
            throw new RuntimeException("分段大小必须大于0");
        } else {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int inputLen = srcBytes.length;
            int offSet = 0;

            for(int i = 0; inputLen - offSet > 0; offSet = i * segmentSize) {
                byte[] cache;
                if (inputLen - offSet > segmentSize) {
                    cache = cipher.doFinal(srcBytes, offSet, segmentSize);
                } else {
                    cache = cipher.doFinal(srcBytes, offSet, inputLen - offSet);
                }

                out.write(cache, 0, cache.length);
                ++i;
            }

            byte[] data = out.toByteArray();
            out.close();
            return data;
        }
    }

    public static String decipher(byte[] srcBytes, PrivateKey key, int segmentSize) {
        try {
            Cipher deCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            deCipher.init(2, key);
//            byte[] decBytes = null;
            byte[] decBytes;
            if (segmentSize > 0) {
                decBytes = cipherDoFinal(deCipher, srcBytes, segmentSize);
            } else {
                decBytes = deCipher.doFinal(srcBytes);
            }

            String decrytStr = new String(decBytes);
            return decrytStr;
        } catch (Exception var6) {
            var6.printStackTrace();
            return null;
        }
    }

    public static byte[] privateDecrypt(byte[] content, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(2, privateKey);
        byte[] bytes = cipher.doFinal(content);
        return bytes;
    }

    public static String byte2Base64(byte[] bytes) {
        return Base64Encoder.encode(bytes);
    }

    public static byte[] base642Byte(String base64Key) throws IOException {
        return Base64Decoder.decodeToBytes(base64Key);
    }

    public static void main(String[] args) throws Exception {
    }
}
