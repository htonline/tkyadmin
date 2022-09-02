package me.zhengjie.modules.system.verify;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
    private final String KEY_GENERATION_ALG = "PBEWITHSHAANDTWOFISH-CBC";
    private final int HASH_ITERATIONS = 10000;
    private final int KEY_LENGTH = 128;
    private char[] humanPassphrase = new char[]{'P', 'e', 'r', ' ', 'v', 'a', 'l', 'l', 'u', 'm', ' ', 'd', 'u', 'c', 'e', 's', ' ', 'L', 'a', 'b', 'a', 'n', 't'};
    private byte[] salt = new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    private PBEKeySpec myKeyspec;
    private final String CIPHERMODEPADDING;
    private SecretKeyFactory keyfactory;
    private SecretKey sk;
    private SecretKeySpec skforAES;
    String sKey;
    private static String ivParameter = "0987654321234567";
    private byte[] iv;
    private IvParameterSpec IV;

    public AES() {
        this.myKeyspec = new PBEKeySpec(this.humanPassphrase, this.salt, 10000, 128);
        this.CIPHERMODEPADDING = "AES/CBC/PKCS5Padding";
        this.keyfactory = null;
        this.sk = null;
        this.skforAES = null;
        this.sKey = "r93535.com/.!!!!";
        this.iv = ivParameter.getBytes();

        try {
            this.keyfactory = SecretKeyFactory.getInstance("PBEWITHSHAANDTWOFISH-CBC");
            this.sk = this.keyfactory.generateSecret(this.myKeyspec);
        } catch (NoSuchAlgorithmException var2) {
//            Log.e("AESdemo", "no key factory support for PBEWITHSHAANDTWOFISH-CBC");
        } catch (InvalidKeySpecException var3) {
//            Log.e("AESdemo", "invalid key spec for PBEWITHSHAANDTWOFISH-CBC");
        }

        byte[] skAsByteArray = this.sKey.getBytes(StandardCharsets.US_ASCII);
        this.skforAES = new SecretKeySpec(skAsByteArray, "AES");
        this.IV = new IvParameterSpec(this.iv);
    }

    public String encrypt(byte[] plaintext) {
        byte[] ciphertext = this.encrypt("AES/CBC/PKCS5Padding", this.skforAES, this.IV, plaintext);
        String base64_ciphertext = Base64Encoder.encode(ciphertext);
        return base64_ciphertext;
    }

    public String decrypt(String ciphertext_base64) {
        byte[] s = Base64Decoder.decodeToBytes(ciphertext_base64);
        String decrypted = new String(this.decrypt("AES/CBC/PKCS5Padding", this.skforAES, this.IV, s));
        return decrypted;
    }

    private byte[] addPadding(byte[] plain) {
//        byte[] plainpad = null;
        int shortage = 16 - plain.length % 16;
        if (shortage == 0) {
            shortage = 16;
        }

        byte[] plainpad = new byte[plain.length + shortage];

        int i;
        for(i = 0; i < plain.length; ++i) {
            plainpad[i] = plain[i];
        }

        for(i = plain.length; i < plain.length + shortage; ++i) {
            plainpad[i] = (byte)shortage;
        }

        return plainpad;
    }

    private byte[] dropPadding(byte[] plainpad) {
//        byte[] plain = null;
        int drop = plainpad[plainpad.length - 1];
        byte[] plain = new byte[plainpad.length - drop];

        for(int i = 0; i < plain.length; ++i) {
            plain[i] = plainpad[i];
            plainpad[i] = 0;
        }

        return plain;
    }

    private byte[] encrypt(String cmp, SecretKey sk, IvParameterSpec IV, byte[] msg) {
        try {
            Cipher c = Cipher.getInstance(cmp);
            c.init(1, sk, IV);
            return c.doFinal(msg);
        } catch (NoSuchAlgorithmException var6) {
//            Log.e("AESdemo", "no cipher getinstance support for " + cmp);
        } catch (NoSuchPaddingException var7) {
//            Log.e("AESdemo", "no cipher getinstance support for padding " + cmp);
        } catch (InvalidKeyException var8) {
//            Log.e("AESdemo", "invalid key exception");
        } catch (InvalidAlgorithmParameterException var9) {
//            Log.e("AESdemo", "invalid algorithm parameter exception");
        } catch (IllegalBlockSizeException var10) {
//            Log.e("AESdemo", "illegal block size exception");
        } catch (BadPaddingException var11) {
//            Log.e("AESdemo", "bad padding exception");
        }

        return null;
    }

    private byte[] decrypt(String cmp, SecretKey sk, IvParameterSpec IV, byte[] ciphertext) {
        try {
            new IvParameterSpec(ivParameter.getBytes(StandardCharsets.UTF_8));
            new SecretKeySpec(this.sKey.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher c = Cipher.getInstance(cmp);
            c.init(2, sk, IV);
            return c.doFinal(ciphertext);
        } catch (NoSuchAlgorithmException var8) {
//            Log.e("AESdemo", "no cipher getinstance support for " + cmp);
        } catch (NoSuchPaddingException var9) {
//            Log.e("AESdemo", "no cipher getinstance support for padding " + cmp);
        } catch (InvalidKeyException var10) {
//            Log.e("AESdemo", "invalid key exception");
        } catch (InvalidAlgorithmParameterException var11) {
//            Log.e("AESdemo", "invalid algorithm parameter exception");
        } catch (IllegalBlockSizeException var12) {
//            Log.e("AESdemo", "illegal block size exception");
        } catch (BadPaddingException var13) {
//            Log.e("AESdemo", "bad padding exception");
            var13.printStackTrace();
        }

        return null;
    }
}
