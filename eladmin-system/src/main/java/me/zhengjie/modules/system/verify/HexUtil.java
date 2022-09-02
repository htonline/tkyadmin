package me.zhengjie.modules.system.verify;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HexUtil {
    private static final char[] DIGITS_LOWER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final char[] DIGITS_UPPER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public HexUtil() {
    }

    public static boolean isHexNumber(String value) {
        int index = value.startsWith("-") ? 1 : 0;
        if (!value.startsWith("0x", index) && !value.startsWith("0X", index) && !value.startsWith("#", index)) {
            return false;
        } else {
            try {
                Long.decode(value);
                return true;
            } catch (NumberFormatException var3) {
                return false;
            }
        }
    }

    public static char[] encodeHex(byte[] data) {
        return encodeHex(data, true);
    }

    public static char[] encodeHex(String str, Charset charset) {
        return encodeHex(str.getBytes(charset), true);
    }

    public static char[] encodeHex(byte[] data, boolean toLowerCase) {
        return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    public static String encodeHexStr(byte[] data) {
        return encodeHexStr(data, true);
    }

    public static String encodeHexStr(String data, Charset charset) {
        return encodeHexStr(data.getBytes(charset), true);
    }

    public static String encodeHexStr(String data) {
        return encodeHexStr(data, StandardCharsets.UTF_8);
    }

    public static String encodeHexStr(byte[] data, boolean toLowerCase) {
        return encodeHexStr(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    public static String decodeHexStr(String hexStr) {
        return decodeHexStr(hexStr, StandardCharsets.UTF_8);
    }

    public static String decodeHexStr(String hexStr, Charset charset) {
        return hexStr.equals("") ? hexStr : decodeHexStr(hexStr.toCharArray(), charset);
    }

    public static String decodeHexStr(char[] hexData, Charset charset) {
        return new String(decodeHex(hexData), charset);
    }

    public static byte[] decodeHex(char[] hexData) {
        int len = hexData.length;
        if ((len & 1) != 0) {
            throw new RuntimeException("Odd number of characters.");
        } else {
            byte[] out = new byte[len >> 1];
            int i = 0;

            for(int j = 0; j < len; ++i) {
                int f = toDigit(hexData[j], j) << 4;
                ++j;
                f |= toDigit(hexData[j], j);
                ++j;
                out[i] = (byte)(f & 255);
            }

            return out;
        }
    }

    public static byte[] decodeHex(String hexStr) {
        return hexStr.equals("") ? null : decodeHex(hexStr.toCharArray());
    }

    public static String toUnicodeHex(int value) {
        StringBuilder builder = new StringBuilder(6);
        builder.append("\\u");
        String hex = toHex(value);
        int len = hex.length();
        if (len < 4) {
            builder.append("0000", 0, 4 - len);
        }

        builder.append(hex);
        return builder.toString();
    }

    public static String toUnicodeHex(char ch) {
        return "\\u" + DIGITS_LOWER[ch >> 12 & 15] + DIGITS_LOWER[ch >> 8 & 15] + DIGITS_LOWER[ch >> 4 & 15] + DIGITS_LOWER[ch & 15];
    }

    public static String toHex(int value) {
        return Integer.toHexString(value);
    }

    public static String toHex(long value) {
        return Long.toHexString(value);
    }

    public static void appendHex(StringBuilder builder, byte b, boolean toLowerCase) {
        char[] toDigits = toLowerCase ? DIGITS_LOWER : DIGITS_UPPER;
        int high = (b & 240) >>> 4;
        int low = b & 15;
        builder.append(toDigits[high]);
        builder.append(toDigits[low]);
    }

    private static String encodeHexStr(byte[] data, char[] toDigits) {
        return new String(encodeHex(data, toDigits));
    }

    private static char[] encodeHex(byte[] data, char[] toDigits) {
        int len = data.length;
        char[] out = new char[len << 1];
        int i = 0;

        for(int var5 = 0; i < len; ++i) {
            out[var5++] = toDigits[(240 & data[i]) >>> 4];
            out[var5++] = toDigits[15 & data[i]];
        }

        return out;
    }

    private static int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new RuntimeException("Illegal hexadecimal character " + ch + " at index " + index);
        } else {
            return digit;
        }
    }
}
